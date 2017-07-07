package org.itai.expenses.core.condition;

import org.itai.expenses.core.Expense;

public class ExpenseCondition extends TransactionTypeCondition {

   public ExpenseCondition() {
      super(Expense.class);
   }

}
