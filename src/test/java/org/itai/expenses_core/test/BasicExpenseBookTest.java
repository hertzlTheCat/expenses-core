package org.itai.expenses_core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.itai.expenses.core.Expense;
import org.itai.expenses.core.ExpenseBook;
import org.itai.expenses.core.Income;
import org.itai.expenses.core.Transaction;
import org.itai.expenses.core.condition.TransactionCondition;
import org.itai.expenses.core.condition.TransactionInInclusivePeriodCondition;
import org.itai.expenses.core.condition.TransactionInMonthCondition;
import org.joda.time.DateTime;
import org.junit.Test;

public class BasicExpenseBookTest {

   @Test
   public void transactionEquals() {

      DateTime dt1 = new DateTime(2017, 06, 05, 10, 30);
      DateTime dt2 = new DateTime(2017, 07, 06, 11, 45);

      Transaction t1 = new Expense(50, "Lunch", "Food", dt1);
      transactionsAreEqual(t1, new Expense(50, "Lunch", "Food", new DateTime(dt1)));
      transactionsAreNotEqual(t1, new Income(50, "Lunch", "Food", dt1));
      transactionsAreNotEqual(t1, new Expense(51, "Lunch", "Food", dt1));
      transactionsAreNotEqual(t1, new Expense(50, "Punch", "Food", dt1));
      transactionsAreNotEqual(t1, new Expense(50, "Lunch", "Mood", dt1));
      transactionsAreNotEqual(t1, new Expense(50, "Lunch", "Food", dt2));

      Transaction t2 = new Income(50, "Lunch", "Food", dt2);
      transactionsAreEqual(t2, new Income(50, "Lunch", "Food", new DateTime(dt2)));
      transactionsAreNotEqual(t2, new Expense(50, "Lunch", "Food", dt2));
      transactionsAreNotEqual(t2, new Income(51, "Lunch", "Food", dt2));
      transactionsAreNotEqual(t2, new Income(50, "Punch", "Food", dt2));
      transactionsAreNotEqual(t2, new Income(50, "Lunch", "Mood", dt2));
      transactionsAreNotEqual(t2, new Income(50, "Lunch", "Food", dt1));
   }

   @Test
   public void buildBook0() {
      buildBookAndTest(0, new Transaction[] {});
   }

   @Test
   public void buildBook1() {
      buildBookAndTest(-100, new Transaction[]
         {new Expense(100, "Lunch", "Food outside", new DateTime(2017, 1, 1, 10, 30))
         });
   }

   @Test
   public void buildBooks2() {
      buildBookAndTest(1000 - 50 - 20, new Transaction[]
         {new Expense(50, "Lunch", "Food outside", new DateTime(2017, 1, 1, 10, 30))
         ,new Expense(20, "Book", "Books", new DateTime(2017, 1, 2, 11, 45))
         ,new Income(1000, "Salary", "Salary", new DateTime(2017, 1, 1, 0, 0))
         });
   }

   @Test
   public void buildBook3() {
      buildBookAndTest(460 + 4000 - 50 - 120, new Transaction[]
         {new Income(460, "Found on the street", "Other", new DateTime(2017, 1, 5, 0, 0))
         ,new Expense(50, "Lunch", "Food outside", new DateTime(2017, 1, 6, 10, 30))
         ,new Expense(120, "Massage", "Health", new DateTime(2017, 1, 11, 22, 40))
         ,new Income(4000, "Salary", "Salary", new DateTime(2017, 1, 1, 0, 0))
         });
   }

   @Test
   public void getAllTransactionsFromMonth() {
      Transaction[] transactions = new Transaction[]
         {new Expense(450, "Lunch", "Food",        new DateTime(2017, 1, 31, 0, 0))  //0
         ,new Expense(450, "Lunch", "Food",        new DateTime(2017, 2, 1, 0, 0))   //1
         ,new Expense(450, "Lunch", "Food",        new DateTime(2017, 2, 3, 0, 0))   //2
         ,new Expense(120, "Massage", "Health",    new DateTime(2017, 2, 6, 0, 0))   //3
         ,new Expense(120, "Massage", "Health",    new DateTime(2017, 3, 1, 0, 0))   //4
         ,new Expense(100, "Trader Joe's", "Food", new DateTime(2017, 3, 4, 0, 0))   //5
         ,new Income(4000, "Salary", "Salary",     new DateTime(2017, 3, 15, 0, 0))  //6
         ,new Income(4000, "Salary", "Salary",     new DateTime(2017, 3, 31, 0, 0))  //7
         ,new Income(4000, "Salary", "Salary",     new DateTime(2017, 4, 1, 0, 0))   //8
         ,new Income(4000, "Salary", "Salary",     new DateTime(2017,12, 1, 0, 0))   //9
         ,new Expense(100, "Lunch", "Food",        new DateTime(2017,12, 15, 0, 0))  //10
         ,new Expense(120, "Massage", "Health",    new DateTime(2017,12, 31, 0, 0))  //11
         ,new Expense(100, "Plush Toy", "Other",   new DateTime(2018, 1, 1, 0, 0))   //12
         };
      ExpenseBook book = new ExpenseBook();
      for (Transaction t : transactions) {
         book.addTransaction(t);
      }

      TransactionCondition conditionInFebruary = new TransactionInMonthCondition(2017, 2);
      Collection<Transaction> inFebruary = book.getTransactionsForCondition(conditionInFebruary);
      assertEquals(3, inFebruary.size());
      assert (inFebruary.contains(transactions[1]));
      assert (inFebruary.contains(transactions[2]));
      assert (inFebruary.contains(transactions[3]));

      TransactionCondition conditionInMarch = new TransactionInMonthCondition(2017, 3);
      Collection<Transaction> inMarch = book.getTransactionsForCondition(conditionInMarch);
      assertEquals(4, inMarch.size());
      assert (inMarch.contains(transactions[4]));
      assert (inMarch.contains(transactions[5]));
      assert (inMarch.contains(transactions[6]));
      assert (inMarch.contains(transactions[7]));

      TransactionCondition conditionInDec = new TransactionInMonthCondition(2017, 12);
      Collection<Transaction> inDec = book.getTransactionsForCondition(conditionInDec);
      assertEquals(3, inDec.size());
      assert (inDec.contains(transactions[9]));
      assert (inDec.contains(transactions[10]));
      assert (inDec.contains(transactions[11]));
   }

   @Test
   public void getAllExpensesInPeriod() {
      Transaction[] transactions = new Transaction[]
         {new Income(4000, "Salary", "Salary",             new DateTime(2017, 2, 1, 0, 0))  //0
         ,new Expense(450, "Lunch", "Food",                new DateTime(2017, 2, 10, 0, 0)) //1
         ,new Expense(120, "Massage", "Health",            new DateTime(2017, 2, 14, 0, 0)) //2
         ,new Expense(120, "Massage", "Health",            new DateTime(2017, 2, 15, 0, 0)) //3
         ,new Expense(100, "Trader Joe's", "Food at home", new DateTime(2017, 3, 1, 0, 0))  //4
         ,new Income(4000, "Salary", "Salary",             new DateTime(2017, 3, 1, 0, 0))  //5
         ,new Expense(10, "Amazon", "Nails",               new DateTime(2017, 3, 9, 0, 0))  //6
         ,new Expense(10, "Amazon", "Nails",               new DateTime(2017, 3, 10, 0, 0)) //7
         };
      ExpenseBook book = new ExpenseBook();
      for (Transaction t : transactions) {
         book.addTransaction(t);
      }

      DateTime from = new DateTime(2017, 2, 15, 0, 0);
      DateTime to = new DateTime(2017, 3, 9, 0, 0);
      TransactionCondition inPeriodCondition = new TransactionInInclusivePeriodCondition(from, to);
      Collection<Transaction> inPeriod = book.getTransactionsForCondition(inPeriodCondition);
      assert (inPeriod.contains(transactions[3]));
      assert (inPeriod.contains(transactions[4]));
      assert (inPeriod.contains(transactions[5]));
      assert (inPeriod.contains(transactions[6]));
      assertEquals(4, inPeriod.size());
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
      Arrays.asList(instructions).stream().forEach(t -> addTransaction(book, t));
      return book;
   }

   private static void addTransaction(ExpenseBook book, Transaction t) {
      if (t instanceof Expense)
         book.addExpense(t.getAmount(), t.getDescription(), t.getCategory(), t.getTime());
      if (t instanceof Income)
         book.addIncome(t.getAmount(), t.getDescription(), t.getCategory(), t.getTime());
   }
}
