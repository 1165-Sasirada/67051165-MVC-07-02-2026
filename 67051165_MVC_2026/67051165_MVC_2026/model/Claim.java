package model;

public class Claim extends BaseClaim {
	public Claim(String claimId, String claimantId, double income) {
		super(claimId, claimantId, income);
	}

	// Business Rule: Claim ประเภททั่วไป คือผู้ที่มีรายได้≥ 6500 บาทต่อเดือน และ ≤ 50000 บาทต่อเดือน จะได้เงินเยียวยาตามรายได้ต่อเดือน แต่ไม่เกิน 20000 บาท
	@Override
	public double calculateCompensation() {
		return Math.min(income, 20000.0);
	}
}
