package org.itai.expenses.core;

public class Expense extends Transaction {

	public Expense(int amount, String description, String category) {
		super(amount, description, category);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Expense)) {
			return false;
		}
		
		return super.equals(obj);
	}	
}