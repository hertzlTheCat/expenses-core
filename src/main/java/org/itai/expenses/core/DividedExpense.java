package org.itai.expenses.core;

import org.joda.time.DateTime;

public class DividedExpense extends Expense {

   private int numberOfPeople;

   private String payer;

   public DividedExpense(int amount,
                         String description,
                         Category category,
                         DateTime time,
                         int numberOfPeople,
                         String payer) {
      super(amount, description, category, time);
      this.numberOfPeople = numberOfPeople;
      this.payer = payer;
   }

   @Override
   public float getDelta() {
      return super.getDelta() / this.numberOfPeople;
   }

   public String getPayer() {
      return this.payer;
   }
}
