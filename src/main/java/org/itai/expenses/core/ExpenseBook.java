package org.itai.expenses.core;

import java.util.Collection;
import java.util.LinkedList;

import org.joda.time.DateTime;

public class ExpenseBook {

	private Collection<Expense> expenses;
	private Collection<Income> incomes;

	public ExpenseBook() {
		this.expenses = new LinkedList<>();
		this.incomes = new LinkedList<>();
	}
	
	public void addExpense(int amount, String description, String category, DateTime time) {
		this.expenses.add(new Expense(amount, description, category, time));
	}

	public void addIncome(int amount, String description, String category, DateTime time) {
		this.incomes.add(new Income(amount, description, category, time));
	}

	public int balance() {
		int balance = 0;
		for (Transaction expense : this.expenses) {
			balance -= expense.getAmount();
		}
		for (Income income : this.incomes) {
			balance += income.getAmount();
		}
		return balance;
	}

	public Collection<Transaction> getTransactions() {
		Collection<Transaction> transactions = new LinkedList<>();
		transactions.addAll(expenses);
		transactions.addAll(incomes);
		return transactions;
	}

}
