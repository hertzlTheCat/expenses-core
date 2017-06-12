package org.itai.expenses.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import org.joda.time.DateTime;

public class ExpenseBook {

   private Collection<Transaction> transactions;

   public ExpenseBook() {
      this.transactions = new LinkedList<>();
   }

   public void addExpense(int amount, String description, String category, DateTime time) {
      this.transactions.add(new Expense(amount, description, category, time));
   }

   public void addIncome(int amount, String description, String category, DateTime time) {
      this.transactions.add(new Income(amount, description, category, time));
   }

   public int balance() {
      int balance = 0;
      for (Transaction t : transactions) {
         balance += t.getDelta();
      }
      return balance;
   }

   public Collection<Transaction> getTransactions() {
      return Collections.unmodifiableCollection(this.transactions);
   }

}
