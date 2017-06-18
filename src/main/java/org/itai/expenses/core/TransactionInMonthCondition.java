package org.itai.expenses.core;

import org.joda.time.DateTime;

public class TransactionInMonthCondition {

   private int year;
   private int month;

   public TransactionInMonthCondition(int year, int month) {
      this.year = year;
      this.month = month;
   }

   public boolean isMatch(Transaction transaction) {
      DateTime time = transaction.getTime();
      return time.getYear() == this.year
         && time.getMonthOfYear() == this.month;
   }
}
