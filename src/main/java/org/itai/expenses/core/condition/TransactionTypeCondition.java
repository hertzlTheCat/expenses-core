package org.itai.expenses.core.condition;

import org.itai.expenses.core.Transaction;

public class TransactionTypeCondition implements Condition {

   @SuppressWarnings("rawtypes")
   private Class transactionType;

   public <T extends Transaction> TransactionTypeCondition(Class<T> transactionType) {
      this.transactionType = transactionType;
   }

   @SuppressWarnings("unchecked")
   @Override
   public boolean isMatch(Transaction transaction) {
      return transaction.isA(transactionType);
   }
}
