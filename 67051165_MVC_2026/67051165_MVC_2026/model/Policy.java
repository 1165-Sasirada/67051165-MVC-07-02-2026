package model;

public class Policy {
	public String id;
	public String description;
	public double maxAmount;
	public double minIncome;
	public double maxIncome;

	public Policy(String id, String description, double maxAmount, double minIncome, double maxIncome) {
		this.id = id;
		this.description = description;
		this.maxAmount = maxAmount;
		this.minIncome = minIncome;
		this.maxIncome = maxIncome;
	}

	public boolean isMatch(double income) {
		return income >= minIncome && income <= maxIncome;
	}
}
