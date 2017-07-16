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

}
