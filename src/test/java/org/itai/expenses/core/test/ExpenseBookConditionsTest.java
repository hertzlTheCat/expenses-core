package org.itai.expenses.core.test;

import static org.junit.Assert.assertEquals;

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

public class ExpenseBookConditionsTest {

   @Test
   public void getAllTransactionsFromMonth() {
      Transaction[] transactions = new Transaction[]
         {new Expense(450, "Lunch", "Food",        new DateTime(2017, 1, 31, 0, 0))  //0
         ,new Expense(450, "Lunch", "Food",        new DateTime(2017, 2, 1, 0, 0))   //1
         , new Expense(450, "Lunch", "Food",       new DateTime(2017, 2, 3, 0, 0))   //2
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
	   ExpenseBook book = ExpenseBook.buildBook(Arrays.asList(transactions));

	   TransactionCondition conditionInFebruary = new TransactionInMonthCondition(2017, 2);
	   Collection<Transaction> inFebruary = book.getTransactions(conditionInFebruary);
	   assertEquals(3, inFebruary.size());
	   assert (inFebruary.contains(transactions[1]));
	   assert (inFebruary.contains(transactions[2]));
	   assert (inFebruary.contains(transactions[3]));

	   TransactionCondition conditionInMarch = new TransactionInMonthCondition(2017, 3);
	   Collection<Transaction> inMarch = book.getTransactions(conditionInMarch);
	   assertEquals(4, inMarch.size());
	   assert (inMarch.contains(transactions[4]));
	   assert (inMarch.contains(transactions[5]));
	   assert (inMarch.contains(transactions[6]));
	   assert (inMarch.contains(transactions[7]));

      TransactionCondition conditionInDec = new TransactionInMonthCondition(2017, 12);
	   Collection<Transaction> inDec = book.getTransactions(conditionInDec);
	   assertEquals(3, inDec.size());
	   assert (inDec.contains(transactions[9]));
	   assert (inDec.contains(transactions[10]));
	   assert (inDec.contains(transactions[11]));
	}

   @Test
   public void getAllTransactionsInPeriod() {
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
	   ExpenseBook book = ExpenseBook.buildBook(Arrays.asList(transactions));

	   DateTime from = new DateTime(2017, 2, 15, 0, 0);
	   DateTime to = new DateTime(2017, 3, 9, 0, 0);
	   TransactionCondition inPeriodCondition = new TransactionInInclusivePeriodCondition(from, to);
	   Collection<Transaction> inPeriod = book.getTransactions(inPeriodCondition);
	   assert (inPeriod.contains(transactions[3]));
	   assert (inPeriod.contains(transactions[4]));
	   assert (inPeriod.contains(transactions[5]));
	   assert (inPeriod.contains(transactions[6]));
	   assertEquals(4, inPeriod.size());
	}
}
