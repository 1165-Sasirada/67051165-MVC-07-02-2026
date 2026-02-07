import javax.swing.SwingUtilities;
import controller.*;

public class Main {
	public static void main(String[] args) {
		// Console
		// SystemController app = new SystemController();
		// app.start();

		// GUI
		SwingUtilities.invokeLater(() -> {
			new GuiController();
		});
	}
}
