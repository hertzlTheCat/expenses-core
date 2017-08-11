package org.itai.expenses.core;

import java.util.Collection;

import org.itai.expenses.core.condition.Condition;

public interface ExpenseBook {

	/**
	 * Adds a transaction to the expense book
	 *
	 * @param Transaction
	 */
	void addTransaction(Transaction transaction);

	/**
	 * Calculates and returns the balance
	 *
	 * @return balance
	 */
	int balance();

	// TODO balance() limited in time

	/**
	 * @return TransactionGroup containing all transaction in expense book
	 */
	TransactionGroup getTransactions();

	/**
	 * Returns a transaction group containing  all transactions in the expense book that meet
	 * the given {@code condition}.
	 *
	 * @param condition
	 * @return TransactionGroup
	 */
	TransactionGroup getTransactions(Condition condition);

	/**
	 * Returns all transactions in the expense book that meet all the conditions in {@code conditions}.
	 * That is, the conditions are related to each other with logical and.
	 *
	 * @param conditions
	 * @return TransactionGroup
	 */
	TransactionGroup getTransactions(Collection<Condition> conditions);

}