package model;

public class HighIncomeClaim extends BaseClaim {
	public HighIncomeClaim(String claimId, String claimantId, double income) {
		super(claimId, claimantId, income);
	}

	// Business Rule: Claim ประเภทผู้มีรายได้สูง คือผู้ที่มีรายได้≥ 50000 บาทต่อเดือน จะได้เงินเยียวยาตามรายได้ต่อเดือนหารด้วย 5 แต่ไม่เกิน 20000 บาท
	@Override
	public double calculateCompensation() {
		double calc = income / 5.0;
		return Math.min(calc, 20000.0);
	}
}
