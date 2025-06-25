import gui.AdminPanel_MainFrame;

import javax.swing.*;

// Main class to start the NABS Technologies Admin Panel application.
public class App {
    public static void main(String[] args) {
        // Ensure the GUI is created and updated on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and display the main application frame
                AdminPanel_MainFrame frame = new AdminPanel_MainFrame();
                frame.setVisible(true);
            }
        });
    }
}
