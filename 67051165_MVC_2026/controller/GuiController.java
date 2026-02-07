// File: controller/GuiController.java
package controller;

import database.CsvDatabase;
import gui.*;
import model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class GuiController {
	private MainFrame frame;
	private LoginPanel loginPanel;
	private RegisterPanel registerPanel;
	private ListPanel listPanel;

	private Claimant currentCitizen;
	private Officer currentOfficer;

	public GuiController() {
		CsvDatabase.init();

		frame = new MainFrame();
		loginPanel = new LoginPanel();
		registerPanel = new RegisterPanel();
		listPanel = new ListPanel();

		frame.addView(loginPanel, "LOGIN");
		frame.addView(registerPanel, "REGISTER");
		frame.addView(listPanel, "LIST");

		// Login [Citizen]
		loginPanel.btnLoginCitizen.addActionListener(e -> handleLoginCitizen());

		// Login [Officer]
		loginPanel.btnLoginOfficer.addActionListener(e -> handleLoginOfficer());

		// Register
		loginPanel.btnRegister.addActionListener(e -> frame.showView("REGISTER"));

		// Register Submit
		registerPanel.btnSubmit.addActionListener(e -> handleRegisterSubmit());
		registerPanel.btnBack.addActionListener(e -> frame.showView("LOGIN"));

		// Logout
		listPanel.btnLogout.addActionListener(e -> {
			currentCitizen = null;
			currentOfficer = null;
			loginPanel.clearFields();
			frame.showView("LOGIN");
		});
		
		// Exit
		loginPanel.btnExit.addActionListener(e -> System.exit(0));

		// New Claim
		listPanel.btnCreateClaim.addActionListener(e -> handleCreateClaim());

		// Login View
		frame.setVisible(true);
		frame.showView("LOGIN");
	}

	private void handleLoginCitizen() {
		String email = loginPanel.emailField.getText();
		String pass = new String(loginPanel.passField.getPassword());
		
		List<String[]> users = CsvDatabase.readAll(CsvDatabase.CLAIMANTS_FILE);
		boolean found = false;
		for (String[] row : users) {
			if (row[3].equals(email) && row[4].equals(pass)) {
				currentCitizen = new Claimant(row[0], row[1], row[2], row[3], Double.parseDouble(row[5]), row[6]);
				found = true;
				break;
			}
		}

		if (found) {
			currentOfficer = null;
			refreshTableData();
			frame.showView("LIST");
		} else {
			JOptionPane.showMessageDialog(frame, "Incorrect Email or Password", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleLoginOfficer() {
		String email = loginPanel.emailField.getText();
		String pass = new String(loginPanel.passField.getPassword());

		List<String[]> officers = CsvDatabase.readAll(CsvDatabase.OFFICERS_FILE);
		boolean found = false;
		for (String[] row : officers) {
			if (row[3].equals(email) && row[4].equals(pass)) {
				currentOfficer = new Officer(row[0], row[1], row[2], row[3]);
				found = true;
				break;
			}
		}

		if (found) {
			currentCitizen = null;
			refreshTableData();
			frame.showView("LIST");
		} else {
			JOptionPane.showMessageDialog(frame, "Login Failed", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleRegisterSubmit() {
		try {
			String name = registerPanel.nameField.getText();
			String surname = registerPanel.surnameField.getText();
			String email = registerPanel.emailField.getText();
			String pass = new String(registerPanel.passField.getPassword());
			double income = Double.parseDouble(registerPanel.incomeField.getText());

			// Calculate Type
			String type;
			if (income < 6500) type = "LOW";
			else if (income <= 50000) type = "GENERAL";
			else type = "HIGH";

			String newId = CsvDatabase.generateNextId(CsvDatabase.CLAIMANTS_FILE);
			String record = String.join(",", newId, name, surname, email, pass, String.valueOf(income), type);
			
			CsvDatabase.appendRecord(CsvDatabase.CLAIMANTS_FILE, record);
			
			JOptionPane.showMessageDialog(frame, "Success! Your ID is " + newId);
			registerPanel.clearFields();
			frame.showView("LOGIN");

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void handleCreateClaim() {
		if (currentCitizen == null) {
			 JOptionPane.showMessageDialog(frame, "Only Citizen Can Submit New Claims");
			 return;
		}

		// Choose Model Logic
		String claimId = String.valueOf(10000000 + new Random().nextInt(90000000));
		BaseClaim model;
		double income = currentCitizen.income;
		
		if (income < 6500) model = new LowIncomeClaim(claimId, currentCitizen.id, income);
		else if (income <= 50000) model = new Claim(claimId, currentCitizen.id, income);
		else model = new HighIncomeClaim(claimId, currentCitizen.id, income);

		double amount = model.calculateCompensation();
		String date = LocalDate.now().toString();

		CsvDatabase.appendRecord(CsvDatabase.CLAIMS_FILE, claimId + "," + currentCitizen.id + "," + date + ",APPROVED");
		CsvDatabase.appendRecord(CsvDatabase.COMPENSATIONS_FILE, claimId + "," + amount + "," + date);

		JOptionPane.showMessageDialog(frame, "Approved Claim Amount: " + amount + " Baht");
		refreshTableData(); // Refresh Table
	}

	private void refreshTableData() {
		// Clear Table
		listPanel.tableModel.setRowCount(0);

		List<String[]> allUsers = CsvDatabase.readAll(CsvDatabase.CLAIMANTS_FILE);
		List<String[]> allClaims = CsvDatabase.readAll(CsvDatabase.CLAIMS_FILE);
		List<String[]> allComps = CsvDatabase.readAll(CsvDatabase.COMPENSATIONS_FILE);

		// Header Text
		if (currentOfficer != null) {
			listPanel.userLabel.setText("Officer: " + currentOfficer.name + "\n (ID: " + currentOfficer.id + ")");
			listPanel.btnCreateClaim.setEnabled(false); // Officer cannot create claims
		} else {
			listPanel.userLabel.setText("User: " + currentCitizen.name + " " + currentCitizen.surname + "\n (ID: " + currentCitizen.id + ")");
			listPanel.btnCreateClaim.setEnabled(true);
		}

		// Loop Data
		for (String[] claim : allClaims) {
			String cId = claim[0]; // Claim ID
			String uId = claim[1]; // User ID
			String date = claim[2]; // Date
            String status = claim[3]; // Status
			
			// Filtering
			if (currentCitizen != null && !uId.equals(currentCitizen.id)) {
				continue; 
			}

			String amount = "Waiting";
			for (String[] comp : allComps) {
				if (comp[0].equals(cId)) {
					amount = comp[1];
					break;
				}
			}

			String name = "-";
            String surname = "-";
            String income = "-";

			for (String[] u : allUsers) {
                // CSV Claimants: id[0], name[1], surname[2], email, password, income[5], type
                if (u[0].equals(uId)) {
                    name = u[1];
                    surname = u[2];
                    income = u[5];
                    break;
                }
            }
			listPanel.tableModel.addRow(new Object[]{cId, uId, name, surname, income, date, status, amount});
		}
	}
}
