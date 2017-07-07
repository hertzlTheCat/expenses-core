package org.itai.expenses.core.condition;

import org.itai.expenses.core.Transaction;

public class TransactionTypeCondition implements TransactionCondition {

   @SuppressWarnings("rawtypes")
   private Class transactionType;

   public <T extends Transaction> TransactionTypeCondition(Class<T> transactionType) {
      this.transactionType = transactionType;
   }

   @Override
   public boolean isMatch(Transaction transaction) {
      return transaction.getClass().isAssignableFrom(transactionType);
   }
}
