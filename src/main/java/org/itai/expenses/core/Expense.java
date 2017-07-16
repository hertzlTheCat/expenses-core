package org.itai.expenses.core;

import org.joda.time.DateTime;

public class Expense extends Transaction {

   public Expense(float amount, String description, Category category, DateTime time) {
      super(amount, description, category, time);
   }

   @Override
   public float getDelta() {
      return -1 * this.getAmount();
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Expense)) {
         return false;
      }

      return super.equals(obj);
   }
}