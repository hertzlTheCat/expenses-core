package org.itai.expenses_core.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.itai.expenses.core.Expense;
import org.itai.expenses.core.ExpenseBook;
import org.itai.expenses.core.Income;
import org.itai.expenses.core.Transaction;
import org.junit.Test;

public class BasicExpenseBookTest {

	@Test
	public void transactionEquals() {		
		
		Transaction t1 = new Expense(50, "Lunch", "Food");
		transactionsAreEqual   (t1, new Expense(50, "Lunch", "Food"));
		transactionsAreNotEqual(t1, new Income (50, "Lunch", "Food"));
		transactionsAreNotEqual(t1, new Expense(51, "Lunch", "Food"));
		transactionsAreNotEqual(t1, new Expense(50, "Punch", "Food"));
		transactionsAreNotEqual(t1, new Expense(50, "Lunch", "Mood"));

		Transaction t2 = new Income(50, "Lunch", "Food");
		transactionsAreEqual   (t2, new Income (50, "Lunch", "Food"));
		transactionsAreNotEqual(t2, new Expense(50, "Lunch", "Food"));
		transactionsAreNotEqual(t2, new Income (51, "Lunch", "Food"));
		transactionsAreNotEqual(t2, new Income (50, "Punch", "Food"));
		transactionsAreNotEqual(t2, new Income (50, "Lunch", "Mood"));
    }

	@Test
	public void buildBook1() {
		buildBookAndTest(1000-50-20, 
		    new Transaction[] {new Expense(50, "Lunch",   "Food outside")
                              ,new Expense(20, "Book",    "Books")
			                  ,new Income(1000, "Salary", "Salary")
		                      });
	}

	@Test
	public void buildBook2() {
		buildBookAndTest(460+4000-50-120,
		    new Transaction[] {new Income(460, "Found on the street", "Food outside")
		    		          ,new Expense(50, "Lunch",               "Food outside")
                              ,new Expense(120, "Massage",            "Health")
			                  ,new Income(4000, "Salary",             "Salary")
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
			book.addExpense(t.getAmount(), t.getDescription(), t.getCategory());
		if (t instanceof Income)
			book.addIncome(t.getAmount(), t.getDescription(), t.getCategory());
	}
}
