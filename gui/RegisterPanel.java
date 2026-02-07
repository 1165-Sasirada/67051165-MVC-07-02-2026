package gui;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
	public JTextField nameField = new JTextField(20);
	public JTextField surnameField = new JTextField(20);
	public JTextField emailField = new JTextField(20);
	public JPasswordField passField = new JPasswordField(20);
	public JTextField incomeField = new JTextField(20);
	
	public JButton btnSubmit = new JButton("Register");
	public JButton btnBack = new JButton("Cancel");

	public RegisterPanel() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		addLabelField("Name:", nameField, 0, gbc);
		addLabelField("Surname:", surnameField, 1, gbc);
		addLabelField("Email:", emailField, 2, gbc);
		addLabelField("Password:", passField, 3, gbc);
		addLabelField("Income:", incomeField, 4, gbc);

		JPanel btnPanel = new JPanel();
		btnPanel.add(btnSubmit);
		btnPanel.add(btnBack);

		gbc.gridx = 1; gbc.gridy = 5;
		add(btnPanel, gbc);
	}

	private void addLabelField(String label, Component field, int y, GridBagConstraints gbc) {
		gbc.gridx = 0; gbc.gridy = y;
		add(new JLabel(label), gbc);
		gbc.gridx = 1;
		add(field, gbc);
	}
	
	public void clearFields() {
		nameField.setText(""); surnameField.setText("");
		emailField.setText(""); passField.setText(""); incomeField.setText("");
	}
}
