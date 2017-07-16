package org.itai.expenses.core;

import org.joda.time.DateTime;

public interface Transaction {

   float getAmount();

   String getDescription();

   Category getCategory();

   DateTime getTime();

   /*
    * Returns the value that should be added to the balance when this transaction
    * is registered. For example, if t.getAmount() == 10, and the t is an expense,
    * -10 will be returned. If t is an income then +10 is returned.
    */
   float getDelta();

   /**
    * Since some of the transaction types are build with a decorator, it is not
    * possible to use instanceof, or other class hierarchy questions about
    * transaction objects. This method is used to check if an object implements a
    * Transaction, by inheritance or composition (decoration).
    *
    * @param transactionType
    * @return true if object is a transaction of type {@code TransactionType}
    */
   boolean isA(Class<Transaction> transactionType);
}
