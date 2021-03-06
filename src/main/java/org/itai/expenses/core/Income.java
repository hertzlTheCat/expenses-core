package org.itai.expenses.core;

import org.joda.time.DateTime;

public class Income extends AbstractTransaction implements Transaction {

   public Income(int amount, String description, Category category, DateTime time) {
      super(amount, description, category, time);
   }

   @Override
   public float getDelta() {
      return +1 * getAmount();
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Income)) {
         return false;
      }

      return super.equals(obj);
   }
}
