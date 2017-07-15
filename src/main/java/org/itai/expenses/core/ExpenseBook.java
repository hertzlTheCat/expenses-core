package org.itai.expenses.core;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.itai.expenses.core.condition.Condition;

public class ExpenseBook {

   private TransactionGroup transactions;

   public static ExpenseBook buildBook(Collection<Transaction> transactions) {
      ExpenseBook book = new ExpenseBook();
      transactions.stream().forEach(t -> book.addTransaction(t));
      return book;
   }

   public static ExpenseBook buildBook(TransactionGroup transactions) {
      return buildBook(transactions.getAll());
   }

   public ExpenseBook() {
      this.transactions = new TransactionGroup();
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

   public TransactionGroup getTransactions() {
      return TransactionGroup.unmodifiableCollection(this.transactions);
   }

   public TransactionGroup getTransactions(Condition condition) {
      return getTransactions(getPredicate(condition));
   }

   /*
    * The conditions are related to each other with logical and. Meaning, a transaction is
    * returned if it passed all conditions.
    */
   public TransactionGroup getTransactions(Collection<Condition> conditions) {
      return getTransactions(getPredicate(conditions));
   }

   private TransactionGroup getTransactions(Predicate<Transaction> predicate) {
      List<Transaction> toReturn = this.transactions.stream()
            .filter(predicate)
            .collect(Collectors.toList());
      return TransactionGroup.unmodifiableCollection(toReturn);
   }

   private Predicate<Transaction> getPredicate(Condition condition) {
      return new Predicate<Transaction>() {
         @Override
         public boolean test(Transaction t) {
            return condition.isMatch(t);
         }
      };
   }

   private Predicate<Transaction> getPredicate(Collection<Condition> conditions) {
      return new Predicate<Transaction>() {
         @Override
         public boolean test(Transaction t) {
            return conditions.stream().allMatch(c -> c.isMatch(t));
         }
      };
   }

}
