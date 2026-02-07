package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListPanel extends JPanel {
	public JTable table;
	public DefaultTableModel tableModel;
	public JButton btnCreateClaim = new JButton("Submit New Claim");
	public JButton btnLogout = new JButton("Logout");
	public JLabel userLabel = new JLabel("User Info");

	public ListPanel() {
		setLayout(new BorderLayout());

		// Top Bar
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		userLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		topPanel.add(userLabel, BorderLayout.WEST);
		topPanel.add(btnLogout, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);

		// Table Area
		String[] columnNames = {"Claim ID", "User ID", "Name", "Surname", "Income", "Date", "Status", "Amount"};
		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		add(new JScrollPane(table), BorderLayout.CENTER);

		// Bottom Bar
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
		btnCreateClaim.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCreateClaim.setPreferredSize(new Dimension(200, 40));
		bottomPanel.add(btnCreateClaim);
		add(bottomPanel, BorderLayout.SOUTH);
	}
}
