// File: view/CreateClaimView.java (หน้ายื่นคำขอ)
package view;

import java.util.Scanner;

public class CreateClaimView {
	private Scanner scanner = new Scanner(System.in);

	public String promptClaimantId() {
		System.out.println("\n--- [VIEW] หน้ายื่นคำขอเยียวยาใหม่ ---");
		System.out.print("กรุณากรอกรหัสผู้ขอ (Claimant ID): ");
		return scanner.nextLine();
	}
	
	// แสดงผลหลังคำนวณเสร็จทันที ตามโจทย์
	public void showCalculationResult(double amount) {
		System.out.println(">>> ระบบได้คำนวณเงินเยียวยาของคุณ: " + amount + " บาท");
	}

	public void showSuccess(String claimId) {
		System.out.println("บันทึกคำขอรหัส " + claimId + " เรียบร้อยแล้ว");
		System.out.print("กด Enter เพื่อกลับไปหน้ารายการ...");
		scanner.nextLine();
	}
}
