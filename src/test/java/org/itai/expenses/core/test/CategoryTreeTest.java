package org.itai.expenses.core.test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.itai.expenses.core.Category;
import org.itai.expenses.core.Expense;
import org.itai.expenses.core.ExpenseBook;
import org.itai.expenses.core.Transaction;
import org.itai.expenses.core.TransactionGroup;
import org.itai.expenses.core.condition.CategoryCondition;
import org.itai.expenses.core.condition.Condition;
import org.joda.time.DateTime;
import org.junit.Test;

public class CategoryTreeTest {

   @Test
   public void categoryTree1() {
      Category food = Category.get("food");
      Category restaurents = food.addSubCategory("restaurents");
      Category grocery = food.addSubCategory("grocery");
      Category gym = Category.get("sport");
      
      Transaction[] transactions = new Transaction[]
            {new Expense(450, "Lunch", restaurents,        new DateTime(2017, 1, 31, 0, 0))  //0
            ,new Expense(450, "Trader Joe's", grocery,     new DateTime(2017, 2, 1, 0, 0))   //1
            ,new Expense(450, "Lunch", food,               new DateTime(2017, 2, 3, 0, 0))   //2
            ,new Expense(450, "Gym",  gym,                 new DateTime(2017, 2, 4, 0 ,0))   //3
            };
      ExpenseBook book = ExpenseBook.buildBook(Arrays.asList(transactions));
      
      Condition inFoodCategory = new CategoryCondition(food);
      TransactionGroup foodTransactions = book.getTransactions(inFoodCategory);
      assertEquals(3, foodTransactions.size());
      assert (foodTransactions.contains(transactions[0]));
      assert (foodTransactions.contains(transactions[0]));
      assert (foodTransactions.contains(transactions[0]));
   }
}
