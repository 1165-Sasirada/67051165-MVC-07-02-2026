package model;

public abstract class BaseClaim {
	protected String claimId;
	protected String claimantId;
	protected double income;

	public BaseClaim(String claimId, String claimantId, double income) {
		this.claimId = claimId;
		this.claimantId = claimantId;
		this.income = income;
	}

	public String getClaimId() {
		return claimId;
	}

	public String getClaimantId() {
		return claimantId;
	}

	public abstract double calculateCompensation();
}
