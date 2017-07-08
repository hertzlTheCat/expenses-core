package org.itai.expenses.core;

import org.joda.time.DateTime;

public abstract class Transaction {

   private int amount;
   private String description;
   private Category category;
   private DateTime time;

   public Transaction(int amount, String description, Category category, DateTime time) {
      this.amount = amount;
      this.description = description;
      this.category = category;
      this.time = time;
   }

   public int getAmount() {
      return this.amount;
   }

   public String getDescription() {
      return this.description;
   }

   public Category getCategory() {
      return this.category;
   }

   public DateTime getTime() {
      return this.time;
   }

   public abstract int getDelta();

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Transaction)) {
         return false;
      }
      return this.hashCode() == obj.hashCode();
   }

   @Override
   public int hashCode() {
      return (this.getDescription() + amount + this.getCategory() + this.getTime().hashCode() + this.getClass())
            .hashCode();
   }
}