package org.itai.expenses_core.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.itai.expenses.core.Expense;
import org.itai.expenses.core.ExpenseBook;
import org.itai.expenses.core.Income;
import org.itai.expenses.core.Transaction;
import org.joda.time.DateTime;
import org.junit.Test;

public class BasicExpenseBookTest {

	@Test
	public void transactionEquals() {

		DateTime dt1 = new DateTime(2017, 06, 05, 10, 30);
		DateTime dt2 = new DateTime(2017, 07, 06, 11, 45);

		Transaction t1 = new Expense(50, "Lunch", "Food", dt1);
		transactionsAreEqual   (t1, new Expense(50, "Lunch", "Food", new DateTime(dt1)));
		transactionsAreNotEqual(t1, new Income (50, "Lunch", "Food", dt1));
		transactionsAreNotEqual(t1, new Expense(51, "Lunch", "Food", dt1));
		transactionsAreNotEqual(t1, new Expense(50, "Punch", "Food", dt1));
		transactionsAreNotEqual(t1, new Expense(50, "Lunch", "Mood", dt1));
		transactionsAreNotEqual(t1, new Expense(50, "Lunch", "Food", dt2));

		Transaction t2 = new Income(50, "Lunch", "Food", dt2);
		transactionsAreEqual   (t2, new Income (50, "Lunch", "Food", new DateTime(dt2)));
		transactionsAreNotEqual(t2, new Expense(50, "Lunch", "Food", dt2));
		transactionsAreNotEqual(t2, new Income (51, "Lunch", "Food", dt2));
		transactionsAreNotEqual(t2, new Income (50, "Punch", "Food", dt2));
		transactionsAreNotEqual(t2, new Income (50, "Lunch", "Mood", dt2));
		transactionsAreNotEqual(t2, new Income (50, "Lunch", "Food", dt1));
    }

	@Test
	public void buildBook1() {
		buildBookAndTest(1000-50-20, new Transaction[]
		  {new Expense(50,   "Lunch",   "Food outside", new DateTime(2017,1,1,10,30))
          ,new Expense(20,   "Book",    "Books",        new DateTime(2017,1,2,11,45))
		  ,new Income (1000, "Salary",  "Salary",       new DateTime(2017,1,1,0,0))
		  });
	}

	@Test
	public void buildBook2() {
		buildBookAndTest(460+4000-50-120, new Transaction[]
		  {new Income (460, "Found on the street", "Other",        new DateTime(2017,1,5,0,0))
		  ,new Expense(50, "Lunch",                "Food outside", new DateTime(2017,1,6,10,30))
          ,new Expense(120, "Massage",             "Health",       new DateTime(2017,1,11,22,40))
	      ,new Income (4000, "Salary",             "Salary",       new DateTime(2017,1,1,0,0))
		  });
	}
	
	private void transactionsAreNotEqual(Transaction t1, Transaction test2) {
		assertNotEquals(t1, test2);
		assertNotEquals(t1.hashCode(), test2.hashCode());
	}

	private void transactionsAreEqual(Transaction t1, Transaction test1) {
		assertEquals(t1, test1);
		assertEquals(t1.hashCode(), test1.hashCode());
	}	

	private void buildBookAndTest(int balance, Transaction[] instructions) {
		ExpenseBook book = buildBook(instructions);

		Collection<Transaction> transactions = book.getTransactions();
		for (Transaction i : instructions)
			assertTrue(transactions.contains(i));

	    assertEquals(balance, book.balance());
	}

	private ExpenseBook buildBook(Transaction[] instructions) {
		ExpenseBook book = new ExpenseBook();
		Arrays.asList(instructions)
			.stream()
			.forEach(t -> addTransaction(book, t));
		return book;
	}
	
	private static void addTransaction(ExpenseBook book, Transaction t) {
		if (t instanceof Expense) 
			book.addExpense(t.getAmount(), t.getDescription(), t.getCategory(), t.getTime());
		if (t instanceof Income)
			book.addIncome(t.getAmount(), t.getDescription(), t.getCategory(), t.getTime());
	}
}
