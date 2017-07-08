package org.itai.expenses.core.condition;

import org.itai.expenses.core.Transaction;

public interface Condition {

   /**
    * Takes a transaction and returns true if it matches the condition.
    * 
    * @param transaction
    * @return true iff transaction matches the condition
    */
   boolean isMatch(Transaction transaction);
}
