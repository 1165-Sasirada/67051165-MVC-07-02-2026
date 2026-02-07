package model;

public class LowIncomeClaim extends BaseClaim {
	public LowIncomeClaim(String claimId, String claimantId, double income) {
		super(claimId, claimantId, income);
	}

	// Business Rule: Claim ประเภทผู้มีรายได้น้อย คือผู้ที่มีรายได้< 6500 บาทต่อเดือน จะได้เงินเยียวยา 6500 บาท
	@Override
	public double calculateCompensation() {
		return 6500.0;
	}
}
