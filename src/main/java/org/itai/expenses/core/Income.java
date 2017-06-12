package org.itai.expenses.core;

public class Income extends Transaction {

	public Income(int amount, String description, String category) {
		super(amount, description, category);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Income)) {
			return false;
		}
		
		return super.equals(obj);
	}
}
