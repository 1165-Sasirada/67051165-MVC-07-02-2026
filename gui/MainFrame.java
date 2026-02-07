package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
	private CardLayout cardLayout;
	private JPanel mainPanel;

	public MainFrame() {
		setTitle("Government Aid System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1024, 768); // Default
		setMinimumSize(new Dimension(800, 600)); // Min Size
		setLocationRelativeTo(null);

		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		add(mainPanel);
	}

	public void addView(JPanel panel, String name) {
		mainPanel.add(panel, name);
	}

	public void showView(String name) {
		cardLayout.show(mainPanel, name);
	}
}
