// File: view/LoginView.java
package view;

import java.util.Scanner;

public class LoginView {
	private Scanner scanner = new Scanner(System.in);

	public int showLoginMenu() {
		System.out.println("\n=== Government Aid System ===");
		System.out.println("1. Login [Citizen]");
		System.out.println("2. Login [Officer]");
		System.out.println("3. Register");
		System.out.println("0. Exit");
		System.out.print("Choose: ");
		try {
			return Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) { return -1; }
	}

	public String[] promptCredentials() {
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("Password: ");
		String pass = scanner.nextLine();
		return new String[]{email, pass};
	}
	
	public void showLoginError() {
		System.out.println("!!! Login Failed !!!");
	}
}
