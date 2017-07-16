package org.itai.expenses.core;

import org.joda.time.DateTime;

public class DividedExpenseDecorator implements Transaction {

   private int numberOfPeople;

   private String payer;

   private Expense expense;

   public DividedExpenseDecorator(Expense expense, int numberOfPeople, String payer) {
      this.expense = expense;
      this.numberOfPeople = numberOfPeople;
      this.payer = payer;
   }

   @Override
   public float getDelta() {
      return expense.getDelta() / this.numberOfPeople;
   }

   public String getPayer() {
      return this.payer;
   }

   public int getNumberOfPeople() {
      return this.numberOfPeople;
   }

   @Override
   public float getAmount() {
      return expense.getAmount();
   }

   @Override
   public String getDescription() {
      return expense.getDescription();
   }

   @Override
   public Category getCategory() {
      return expense.getCategory();
   }

   @Override
   public DateTime getTime() {
      return expense.getTime();
   }

   @Override
   public boolean isA(Class<Transaction> transactionType) {
      return transactionType.equals(DividedExpenseDecorator.class)
         || expense.isA(transactionType);
   }
}
