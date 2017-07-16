package org.itai.expenses.core.condition;

import org.itai.expenses.core.DividedExpenseDecorator;

public class DividedExpenseCondition extends TransactionTypeCondition {

   public DividedExpenseCondition() {
      super(DividedExpenseDecorator.class);
   }

}
