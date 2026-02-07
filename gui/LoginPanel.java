package gui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
	public JTextField emailField = new JTextField(20);
	public JPasswordField passField = new JPasswordField(20);
	public JButton btnLoginCitizen = new JButton("Login [Citizen]");
	public JButton btnLoginOfficer = new JButton("Login [Officer]");
	public JButton btnRegister = new JButton("Register");
	public JButton btnExit = new JButton("Exit");

	public LoginPanel() {
		setLayout(new GridBagLayout()); // Centered
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Header
		JLabel title = new JLabel("Government Aid System");
		title.setFont(new Font("Tahoma", Font.BOLD, 24));
		gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
		add(title, gbc);

		// Inputs
		gbc.gridwidth = 1;
		gbc.gridy = 1; add(new JLabel("Email:"), gbc);
		gbc.gridx = 1; add(emailField, gbc);

		gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("Password:"), gbc);
		gbc.gridx = 1; add(passField, gbc);

		// Buttons
		gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
		add(btnLoginCitizen, gbc);

		gbc.gridy = 4;
		add(btnLoginOfficer, gbc);

		gbc.gridy = 5;
		add(btnRegister, gbc);
		
		gbc.gridy = 6;
		btnExit.setBackground(Color.RED);
		btnExit.setForeground(Color.WHITE);
		add(btnExit, gbc);
	}
	
	public void clearFields() {
		emailField.setText("");
		passField.setText("");
	}
}