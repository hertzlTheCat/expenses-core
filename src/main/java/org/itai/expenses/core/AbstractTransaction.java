package org.itai.expenses.core;

import org.joda.time.DateTime;

public abstract class AbstractTransaction implements Transaction {

   private float amount;
   private String description;
   private Category category;
   private DateTime time;

   public AbstractTransaction(float amount, String description, Category category, DateTime time) {
      this.amount = amount;
      this.description = description;
      this.category = category;
      this.time = time;
   }

   @Override
   public float getAmount() {
      return this.amount;
   }

   @Override
   public String getDescription() {
      return this.description;
   }

   @Override
   public Category getCategory() {
      return this.category;
   }

   @Override
   public DateTime getTime() {
      return this.time;
   }

   @Override
   public boolean isA(Class<Transaction> transactionType) {
      return this.getClass().isAssignableFrom(transactionType);
   }

   @Override
   public abstract float getDelta();

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