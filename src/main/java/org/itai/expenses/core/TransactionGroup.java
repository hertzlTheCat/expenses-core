package org.itai.expenses.core;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class TransactionGroup extends AbstractCollection<Transaction> implements Collection<Transaction> {

   private Collection<Transaction> transactions;

   public static TransactionGroup unmodifiableCollection(Collection<Transaction> c) {
      return new TransactionGroup(Collections.unmodifiableCollection(c));
   }

   public static TransactionGroup unmodifiableCollection(TransactionGroup c) {
      return new TransactionGroup(Collections.unmodifiableCollection(c.transactions));
   }

   public TransactionGroup() {
      this.transactions = new LinkedList<>();
   }

   public TransactionGroup(Collection<Transaction> transactions) {
      this.transactions = new LinkedList<>(transactions);
   }

   @Override
   public boolean add(Transaction t) {
      return this.transactions.add(t);
   }

   @Override
   public Iterator<Transaction> iterator() {
      return this.transactions.iterator();
   }

   @Override
   public int size() {
      return this.transactions.size();
   }

   public Collection<Transaction> getAll() {
      return Collections.unmodifiableCollection(this.transactions);
   }
}
