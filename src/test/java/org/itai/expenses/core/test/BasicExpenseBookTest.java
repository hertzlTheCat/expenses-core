package org.itai.expenses.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.itai.expenses.core.Category;
import org.itai.expenses.core.Expense;
import org.itai.expenses.core.DefaultExpenseBook;
import org.itai.expenses.core.ExpenseBook;
import org.itai.expenses.core.Income;
import org.itai.expenses.core.PersistentExpenseBookDecorator;
import org.itai.expenses.core.Transaction;
import org.itai.expenses.core.TransactionGroup;
import org.joda.time.DateTime;
import org.junit.Test;

public class BasicExpenseBookTest {

	public static class Instructions {
		public Transaction[] transactions;
		public int balance;
		public Instructions(Transaction[] transactions, int balance) {
			this.transactions = transactions;
			this.balance = balance;
		}
	}

	public static Instructions[] instructions = new Instructions[] {
	    new Instructions(new Transaction[] {
	                     }, 0),
		new Instructions(new Transaction[] {
			                new Expense(100, "Lunch", Category.get("Food outside"), new DateTime(2017, 1, 1, 10, 30))
	                     }, -100),
		new Instructions(new Transaction[] {
			                new Expense(50, "Lunch", Category.get("Food outside"), new DateTime(2017, 1, 1, 10, 30)),
			                new Expense(20, "Book", Category.get("Books"), new DateTime(2017, 1, 2, 11, 45)),
			                new Income(1000, "Salary", Category.get("Salary"), new DateTime(2017, 1, 1, 0, 0))
			             }, 1000 - 50 - 20),
		new Instructions(new Transaction[] {
				            new Income(460, "Found on the street", Category.get("Other"), new DateTime(2017, 1, 5, 0, 0)),
		                    new Expense(50, "Lunch", Category.get("Food outside"), new DateTime(2017, 1, 6, 10, 30)),
		                    new Expense(120, "Massage", Category.get("Health"), new DateTime(2017, 1, 11, 22, 40)),
		                    new Income(4000, "Salary", Category.get("Salary"), new DateTime(2017, 1, 1, 0, 0))
		                 }, 460 + 4000 - 120 -50)
   };

   @Test
   public void transactionEquals() {

      Category food = Category.get("food");
      Category mood = Category.get("mood");

      DateTime dt1 = new DateTime(2017, 06, 05, 10, 30);
      DateTime dt2 = new DateTime(2017, 07, 06, 11, 45);

      Transaction t1 = new Expense(50, "Lunch", food, dt1);
      transactionsAreEqual(t1, new Expense(50, "Lunch", food, new DateTime(dt1)));
      transactionsAreNotEqual(t1, new Income(50, "Lunch", food, dt1));
      transactionsAreNotEqual(t1, new Expense(51, "Lunch", food, dt1));
      transactionsAreNotEqual(t1, new Expense(50, "Punch", food, dt1));
      transactionsAreNotEqual(t1, new Expense(50, "Lunch", mood, dt1));
      transactionsAreNotEqual(t1, new Expense(50, "Lunch", food, dt2));

      Transaction t2 = new Income(50, "Lunch", food, dt2);
      transactionsAreEqual(t2, new Income(50, "Lunch", food, new DateTime(dt2)));
      transactionsAreNotEqual(t2, new Expense(50, "Lunch", food, dt2));
      transactionsAreNotEqual(t2, new Income(51, "Lunch", food, dt2));
      transactionsAreNotEqual(t2, new Income(50, "Punch", food, dt2));
      transactionsAreNotEqual(t2, new Income(50, "Lunch", mood, dt2));
      transactionsAreNotEqual(t2, new Income(50, "Lunch", food, dt1));
   }

   @Test
   public void buildBook() {
	   for (Instructions i : BasicExpenseBookTest.instructions) {
		   buildBookAndTest(i.balance, i.transactions);
	   }
   }

   private void transactionsAreNotEqual(Transaction t1, Transaction test2) {
      assertNotEquals(t1, test2);
      assertNotEquals(t1.hashCode(), test2.hashCode());
   }

   private void transactionsAreEqual(Transaction t1, Transaction test1) {
      assertEquals(t1, test1);
      assertEquals(t1.hashCode(), test1.hashCode());
   }

   private void buildBookAndTest(int balance, Transaction[] transactions) {
      ExpenseBook book = DefaultExpenseBook.buildBook(Arrays.asList(transactions));
      testBook(book, balance, transactions);
   }

   private void testBook(ExpenseBook book, int balance, Transaction[] instructions) {

      Collection<Transaction> transactions = book.getTransactions();
      for (Transaction i : instructions)
         assertTrue(transactions.contains(i));

      assertEquals(balance, book.balance());
   }
}
