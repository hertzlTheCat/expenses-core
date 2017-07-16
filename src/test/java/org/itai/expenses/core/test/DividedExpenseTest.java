package org.itai.expenses.core.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.itai.expenses.core.Category;
import org.itai.expenses.core.DividedExpense;
import org.itai.expenses.core.Expense;
import org.itai.expenses.core.ExpenseBook;
import org.itai.expenses.core.Transaction;
import org.itai.expenses.core.TransactionGroup;
import org.itai.expenses.core.condition.DividedExpenseCondition;
import org.itai.expenses.core.condition.ExpenseCondition;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

public class DividedExpenseTest {

   private Transaction[] transactions;

   private ExpenseBook book;

   @Before
   public void setup() {
      Category food = Category.get("food");
      Category health = Category.get("health");

      this.transactions = new Transaction[] {
           new Expense(450, "Lunch", food, new DateTime(2017, 1, 31, 0, 0))                         // 0
         , new DividedExpense(450, "Lunch", food, new DateTime(2017, 2, 1, 0, 0), 2, "Julio")       // 1
         , new Expense(450, "Lunch", food, new DateTime(2017, 2, 3, 0, 0))                          // 2
         , new Expense(120, "Massage", health, new DateTime(2017, 2, 6, 0, 0))                      // 3
         , new DividedExpense(120, "Trader Joe's", food, new DateTime(2017, 3, 4, 0, 0), 3, "Itai") // 4
      };
      this.book = ExpenseBook.buildBook(Arrays.asList(transactions));
   }

   @Test
   public void testBalance() {
      assertEquals(- (450 + 450/2 + 450 + 120 + 120/3), book.balance());
   }

   @Test
   public void testCondition() {
      TransactionGroup dividedExpenses = book.getTransactions(new DividedExpenseCondition());
      assertEquals(2, dividedExpenses.size());
      assertTrue(dividedExpenses.contains(transactions[1]));
      assertTrue(dividedExpenses.contains(transactions[4]));

      TransactionGroup allExpenses = book.getTransactions(new ExpenseCondition());
      assertEquals(5, allExpenses.size());
      assertTrue(allExpenses.contains(transactions[0]));
      assertTrue(allExpenses.contains(transactions[1]));
      assertTrue(allExpenses.contains(transactions[2]));
      assertTrue(allExpenses.contains(transactions[3]));
      assertTrue(allExpenses.contains(transactions[4]));
   }
}
