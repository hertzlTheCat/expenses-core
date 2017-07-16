package org.itai.expenses.core.condition;

import org.itai.expenses.core.DividedExpense;

public class DividedExpenseCondition extends TransactionTypeCondition {

   public DividedExpenseCondition() {
      super(DividedExpense.class);
   }

}
