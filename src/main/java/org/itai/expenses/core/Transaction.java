package org.itai.expenses.core;

import org.joda.time.DateTime;

public abstract class Transaction {

   private int amount;
   private String description;
   private String category;
   private DateTime time;

   public Transaction(int amount, String description, String category, DateTime time) {
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

   public String getCategory() {
      return this.category;
   }

   public DateTime getTime() {
      return this.time;
   }

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