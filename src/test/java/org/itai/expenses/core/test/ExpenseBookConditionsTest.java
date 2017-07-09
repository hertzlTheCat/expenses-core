package org.itai.expenses.core.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.itai.expenses.core.Category;
import org.itai.expenses.core.Expense;
import org.itai.expenses.core.ExpenseBook;
import org.itai.expenses.core.Income;
import org.itai.expenses.core.Transaction;
import org.itai.expenses.core.condition.CategoryCondition;
import org.itai.expenses.core.condition.Condition;
import org.itai.expenses.core.condition.ExpenseCondition;
import org.itai.expenses.core.condition.InInclusivePeriodCondition;
import org.itai.expenses.core.condition.InMonthCondition;
import org.itai.expenses.core.condition.IncomeCondition;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class ExpenseBookConditionsTest {

   private ExpenseBook book;

   private Transaction[] transactions;

   @Before
   public void setup() {
      Category food = Category.get("food");
      Category health = Category.get("health");
      Category salary = Category.get("salary");
      Category other = Category.get("other");
      
      transactions = new Transaction[]
         {new Expense(450, "Lunch", food,        new DateTime(2017, 1, 31, 0, 0))  //0
         ,new Expense(450, "Lunch", food,        new DateTime(2017, 2, 1, 0, 0))   //1
         ,new Expense(450, "Lunch", food,        new DateTime(2017, 2, 3, 0, 0))   //2
         ,new Expense(120, "Massage", health,    new DateTime(2017, 2, 6, 0, 0))   //3
         ,new Expense(120, "Massage", health,    new DateTime(2017, 3, 1, 0, 0))   //4
         ,new Expense(100, "Trader Joe's", food, new DateTime(2017, 3, 4, 0, 0))   //5
         ,new Income(4000, "Salary", salary,     new DateTime(2017, 3, 15, 0, 0))  //6
         ,new Income(4000, "Salary", salary,     new DateTime(2017, 3, 31, 0, 0))  //7
         ,new Income(4000, "Salary", salary,     new DateTime(2017, 4, 1, 0, 0))   //8
         ,new Income(4000, "Salary", salary,     new DateTime(2017,12, 1, 0, 0))   //9
         ,new Expense(100, "Lunch", food,        new DateTime(2017,12, 15, 0, 0))  //10
         ,new Expense(120, "Massage", health,    new DateTime(2017,12, 31, 0, 0))  //11
         ,new Expense(100, "Plush Toy", other,   new DateTime(2018, 1, 1, 0, 0))   //12
         };
      book = ExpenseBook.buildBook(Arrays.asList(transactions));
   }

   @Test
   public void getAllTransactionsFromFebruary() {
	   Condition conditionInFebruary = new InMonthCondition(2017, 2);
	   Collection<Transaction> inFebruary = book.getTransactions(conditionInFebruary);
      assertTrasnactionsContain(inFebruary, 1, 2, 3);
   }

   @Test
   public void getAllTransactionsFromMarch() {
	   Condition conditionInMarch = new InMonthCondition(2017, 3);
	   Collection<Transaction> inMarch = book.getTransactions(conditionInMarch);
      assertTrasnactionsContain(inMarch, 4, 5, 6, 7);
   }

   @Test
   public void getAllTransactionsFromDecember() {
      Condition conditionInDec = new InMonthCondition(2017, 12);
	   Collection<Transaction> inDec = book.getTransactions(conditionInDec);
      assertTrasnactionsContain(inDec, 9, 10, 11);
	}

   @Test
   public void getAllTransactionsInPeriod1() {
	   DateTime from = new DateTime(2017, 2, 15, 0, 0);
	   DateTime to = new DateTime(2017, 3, 9, 0, 0);
	   Condition inPeriodCondition = new InInclusivePeriodCondition(from, to);
	   Collection<Transaction> inPeriod = book.getTransactions(inPeriodCondition);
      assertTrasnactionsContain(inPeriod, 4, 5);
	}

   @Test
   public void getAllTransactionsInPeriod2() {
      DateTime from = new DateTime(2017, 3, 4, 0, 0);
      DateTime to = new DateTime(2017, 4, 1, 0, 0);
      Condition inPeriodCondition = new InInclusivePeriodCondition(from, to);
      Collection<Transaction> inPeriod = book.getTransactions(inPeriodCondition);
      assertTrasnactionsContain(inPeriod, 5, 6, 7, 8);
   }

   @Test
   public void getAllIncomes() {
      Collection<Transaction> incomes = book.getTransactions(new IncomeCondition());
      assertTrasnactionsContain(incomes, 6, 7, 8, 9);
   }

   @Test
   public void getAllExpenses() {
      Collection<Transaction> incomes = book.getTransactions(new ExpenseCondition());
      assertTrasnactionsContain(incomes, 0, 1, 2, 3, 4, 5, 10, 11, 12);
   }

   @Test
   public void getAllByCategory() {
      CategoryCondition inFoodCategory = new CategoryCondition(Category.get("food"));
      Collection<Transaction> foods = book.getTransactions(inFoodCategory);
      assertTrasnactionsContain(foods, 0, 1, 2, 5, 10);
   }

   @Test
   public void testMultipleConditions1() {
      Collection<Condition> conditions = Arrays.asList(
         new ExpenseCondition(),
         new InMonthCondition(2017, 3));
      Collection<Transaction> transactions = book.getTransactions(conditions);
      assertTrasnactionsContain(transactions, 4, 5);
   }

   @Test
   public void testMultipleConditions2() {
      Collection<Condition> conditions = Arrays.asList(
         new IncomeCondition(),
         new InMonthCondition(2017, 3));
      Collection<Transaction> transactions = book.getTransactions(conditions);
      assertTrasnactionsContain(transactions, 6, 7);
   }

   @Test
   public void testMultipleConditions3() {
      Collection<Condition> conditions = Arrays.asList(
         new ExpenseCondition(),
         new InMonthCondition(2017, 3),
         new CategoryCondition(Category.get("food")));
      Collection<Transaction> transactions = book.getTransactions(conditions);
      assertTrasnactionsContain(transactions, 5);
   }

   @Test
   public void testMultipleConditions4() {
      Collection<Condition> conditions = Arrays.asList(
         new ExpenseCondition(),
         new InMonthCondition(2017, 3),
         new CategoryCondition(Category.get("sport")));
      Collection<Transaction> transactions = book.getTransactions(conditions);
      assert (transactions.isEmpty());
   }

   public void assertTrasnactionsContain(Collection<Transaction> queriedTransactions, int... indices) {
      assertEquals(indices.length, queriedTransactions.size());
      for (int index : indices) {
         assert (queriedTransactions.contains(transactions[index]));
      }
   }

}
