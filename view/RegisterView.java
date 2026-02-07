// File: view/RegisterView.java
package view;

import java.util.Scanner;

public class RegisterView {
	private Scanner scanner = new Scanner(System.in);

	public String[] promptRegistrationData() {
		System.out.println("\n--- [VIEW] Register ---");
		System.out.print("Name: ");
		String name = scanner.nextLine();
		
		System.out.print("Surname: ");
		String surname = scanner.nextLine();
		
		System.out.print("Email: ");
		String email = scanner.nextLine();
		
		System.out.print("Password: ");
		String password = scanner.nextLine();
		
		System.out.print("Income: ");
		String income = scanner.nextLine();

		// ส่งค่ากลับเป็น Array String เพื่อให้ Controller ไปจัดการต่อ
		return new String[]{name, surname, email, password, income};
	}

	public void showSuccess(String newId) {
		System.out.println("\n>>> Success!");
		System.out.println(">>> New ID: " + newId);
		System.out.println("Please Re-Login");
		System.out.println("---------------------------------------------");
	}
	
	public void showError(String message) {
		System.out.println("!!! Error: " + message);
	}
}