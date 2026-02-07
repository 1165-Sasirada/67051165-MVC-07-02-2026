package controller;

import database.*;
import model.*;
import view.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class SystemController {
	private LoginView loginView = new LoginView();
	private ClaimListView listView = new ClaimListView();
	private CreateClaimView createView = new CreateClaimView();
	private RegisterView registerView = new RegisterView();

	// Current session
	private Claimant currentCitizen = null;
	private Officer currentOfficer = null;

	public void start() {
		CsvDatabase.init();
		
		while (true) {
			currentCitizen = null;
			currentOfficer = null;
			
			int choice = loginView.showLoginMenu();
			if (choice == 0) break;

			if (choice == 3) {
				handleRegister();
				continue; // Return to Login
			}

			String[] creds = loginView.promptCredentials();
			String email = creds[0];
			String password = creds[1];

			if (choice == 1) { // Citizen Login
				if (loginAsCitizen(email, password)) {
					citizenFlow();
				} else {
					loginView.showLoginError();
				}
			} else if (choice == 2) { // Officer Login
				if (loginAsOfficer(email, password)) {
					officerFlow();
				} else {
					loginView.showLoginError();
				}
			} 
		}
	}

	// Authentication
	private boolean loginAsCitizen(String email, String pass) {
		List<String[]> users = CsvDatabase.readAll(CsvDatabase.CLAIMANTS_FILE);
		for (String[] row : users) {
			// CSV: id, name, surname, email, password, income, type
			if (row[3].equals(email) && row[4].equals(pass)) {
				currentCitizen = new Claimant(row[0], row[1], row[2], row[3], Double.parseDouble(row[5]), row[6]);
				return true;
			}
		}
		return false;
	}

	private boolean loginAsOfficer(String email, String pass) {
		List<String[]> officers = CsvDatabase.readAll(CsvDatabase.OFFICERS_FILE);
		for (String[] row : officers) {
			// CSV: id, name, surname, email, password
			if (row[3].equals(email) && row[4].equals(pass)) {
				currentOfficer = new Officer(row[0], row[1], row[2], row[3]);
				return true;
			}
		}
		return false;
	}

	// --- Flows ---
	
	private void citizenFlow() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			// แสดงรายการคำขอเฉพาะของตัวเอง
			List<String[]> allClaims = CsvDatabase.readAll(CsvDatabase.CLAIMS_FILE);
			List<String[]> myClaims = new ArrayList<>();
			for(String[] c : allClaims) {
				if(c[1].equals(currentCitizen.id)) myClaims.add(c);
			}
			
			listView.render(myClaims, CsvDatabase.readAll(CsvDatabase.COMPENSATIONS_FILE), false);

			System.out.println("Hello, " + currentCitizen.name + " " + currentCitizen.surname);
			System.out.println("[1] Submit New Claim");
			System.out.println("[0] Logout");
			
			String menu = sc.nextLine();
			if (menu.equals("1")) {
				handleCreateClaim();
			} else {
				break;
			}
		}
	}

	private void officerFlow() {
		Scanner sc = new Scanner(System.in);
		while (true) {
			// เจ้าหน้าที่เห็นทั้งหมด
			listView.render(
				CsvDatabase.readAll(CsvDatabase.CLAIMS_FILE), 
				CsvDatabase.readAll(CsvDatabase.COMPENSATIONS_FILE), 
				true
			);

			System.out.println("Hello, Officer " + currentOfficer.name);
			System.out.println("[0] Logout");
			if (sc.nextLine().equals("0")) break;
		}
	}

	private void handleCreateClaim() {
		// Claimant ID = Current ID
		String claimantId = currentCitizen.id;
		
		System.out.println("\nCreating New Claim For " + claimantId + " ...");

		// Generate Claim ID (8 หลัก)
		String claimId = String.valueOf(10000000 + new Random().nextInt(90000000));

		// Model
		BaseClaim claimModel;
		double income = currentCitizen.income;
		if (income < 6500) {
			claimModel = new LowIncomeClaim(claimId, claimantId, income);
		} else if (income >= 6500 && income <= 50000) {
			claimModel = new Claim(claimId, claimantId, income);
		} else {
			claimModel = new HighIncomeClaim(claimId, claimantId, income);
		}

		// Calculate Compensation
		double compensationAmount = claimModel.calculateCompensation();
		String date = LocalDate.now().toString();

		// Save
		CsvDatabase.appendRecord(CsvDatabase.CLAIMS_FILE, 
			claimId + "," + claimantId + "," + date + ",APPROVED");
		CsvDatabase.appendRecord(CsvDatabase.COMPENSATIONS_FILE, 
			claimId + "," + compensationAmount + "," + date);

		// View
		createView.showCalculationResult(compensationAmount);
		createView.showSuccess(claimId);
	}

	private void handleRegister() {
		try {
			String[] data = registerView.promptRegistrationData();
			String name = data[0];
			String surname = data[1];
			String email = data[2];
			String password = data[3];
			double income = Double.parseDouble(data[4]); // แปลงรายได้เป็นตัวเลข

			// Calculate Income Type
			String type;
			if (income < 6500) {
				type = "LOW";
			} else if (income >= 6500 && income <= 50000) {
				type = "GENERAL";
			} else {
				type = "HIGH";
			}

			// New ID
			String newId = CsvDatabase.generateNextId(CsvDatabase.CLAIMANTS_FILE);

			// Csv
			String record = String.join(",", 
				newId, name, surname, email, password, String.valueOf(income), type
			);

			// Save
			CsvDatabase.appendRecord(CsvDatabase.CLAIMANTS_FILE, record);

			// Show Success
			registerView.showSuccess(newId);

		} catch (NumberFormatException e) {
			registerView.showError("PLEASE ONLY INPUT NUMBERS");
		} catch (Exception e) {
			registerView.showError("Register failed: " + e.getMessage());
		}
	}
}
