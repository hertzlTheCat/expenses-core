package org.itai.expenses.core;

public abstract class Transaction {

	private int amount;
	private String description;
	private String category;

	public Transaction(int amount, String description, String category) {
		this.amount = amount;
		this.description = description;
		this.category = category;
	}
	
	public int getAmount() {
		return this.amount;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCategory() {
		return category;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Transaction)) {
			return false;
		}
		
		Transaction other = (Transaction) obj;
		return this.getAmount() == other.getAmount() &&
			   this.getDescription().equals(other.getDescription()) &&
			   this.getCategory().equals(other.getCategory());
	}
	
	@Override
	public int hashCode() {
		return (this.getDescription() 
				   + amount 
				   + this.getCategory() 
				   + this.getClass()).hashCode();
	}
}