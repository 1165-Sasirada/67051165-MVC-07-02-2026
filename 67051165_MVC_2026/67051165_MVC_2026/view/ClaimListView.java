// File: view/ClaimListView.java (ปรับให้แสดงชื่อคนล็อกอิน)
package view;
import java.util.List;

public class ClaimListView {
	public void render(List<String[]> claims, List<String[]> compensations, boolean isOfficer) {
		System.out.println("\n--- [VIEW] Claims List ---");
		if (isOfficer) {
			System.out.println("(Officer: All Claims)");
		} else {
			System.out.println("(User: Your Claim History)");
		}
		
		System.out.println("ClaimID\t| UserID\t| Status\t| Amount");
		System.out.println("-----------------------------------------------------");

		if (claims.isEmpty()) {
			System.out.println("--- NO DATA ---");
		}
		
		for (String[] claim : claims) {
			String cId = claim[0];
			String uId = claim[1];
			String status = claim[3];
			String amount = "Waiting";

			for (String[] comp : compensations) {
				if (comp[0].equals(cId)) {
					amount = comp[1];
					break;
				}
			}
			System.out.println(cId + "\t| " + uId + "\t\t| " + status + "\t| " + amount);
		}
		System.out.println("-----------------------------------------------------");
	}
}
