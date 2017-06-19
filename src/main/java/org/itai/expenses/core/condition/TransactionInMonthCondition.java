package org.itai.expenses.core.condition;

import org.itai.expenses.core.Transaction;
import org.joda.time.DateTime;

public class TransactionInMonthCondition implements TransactionCondition {

   private int year;
   private int month;

   public TransactionInMonthCondition(int year, int month) {
      this.year = year;
      this.month = month;
   }

   @Override
   public boolean isMatch(Transaction transaction) {
      DateTime time = transaction.getTime();
      return time.getYear() == this.year
         && time.getMonthOfYear() == this.month;
   }
}
