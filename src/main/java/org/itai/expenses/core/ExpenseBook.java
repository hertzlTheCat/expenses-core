package org.itai.expenses.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.itai.expenses.core.condition.TransactionCondition;
import org.joda.time.DateTime;

public class ExpenseBook {

   private Collection<Transaction> transactions;

   public static ExpenseBook buildBook(Collection<Transaction> transactions) {
      ExpenseBook book = new ExpenseBook();
      transactions.stream().forEach(t -> book.addTransaction(t));
      return book;
   }

   public ExpenseBook() {
      this.transactions = new LinkedList<>();
   }

   public void addExpense(int amount, String description, String category, DateTime time) {
      this.transactions.add(new Expense(amount, description, category, time));
   }

   public void addIncome(int amount, String description, String category, DateTime time) {
      this.transactions.add(new Income(amount, description, category, time));
   }

   public void addTransaction(Transaction t) {
      this.transactions.add(t);
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

   public Collection<Transaction> getTransactions(TransactionCondition condition) {
      List<Transaction> toReturn = this.transactions.stream()
         .filter(t -> condition.isMatch(t))
         .collect(Collectors.toList());
      return Collections.unmodifiableCollection(toReturn);
   }
}
