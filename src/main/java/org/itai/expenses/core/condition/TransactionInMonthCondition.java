package org.itai.expenses.core.condition;

import org.joda.time.DateTime;

public class TransactionInMonthCondition extends TransactionInInclusivePeriodCondition {

   public TransactionInMonthCondition(int year, int month) {
      super(new DateTime(year, month, 1, 0, 0),
            new DateTime(year, month, 1, 0, 0).plusMonths(1).minusDays(1));
   }
}
