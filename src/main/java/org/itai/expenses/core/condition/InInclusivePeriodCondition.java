package org.itai.expenses.core.condition;

import org.itai.expenses.core.Transaction;
import org.joda.time.DateTime;

public class InInclusivePeriodCondition implements Condition {

   private DateTime from;
   private DateTime to;

   /**
    * The condition matches all transactions starting from {@param from} to
    * {@to to} including both dates.
    *
    * @param from lower date boundary
    * @param to upper date boundary, transactions that occurred on this date match
    * the condition
    */
   public InInclusivePeriodCondition(DateTime from, DateTime to) {
      this.from = from.minusDays(1);
      this.to = to.plusDays(1);
   }

   @Override
   public boolean isMatch(Transaction transaction) {
      DateTime time = transaction.getTime();
      return from.isBefore(time) && to.isAfter(time);
   }
}
