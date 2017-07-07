package org.itai.expenses.core.condition;

import org.itai.expenses.core.Income;

/*
 * Since it has no state, it could be a singleton. But for similarity to other
 * conditions, it is left as is.
 */
public class IncomeCondition extends TransactionTypeCondition {

   public IncomeCondition() {
      super(Income.class);
   }

}
