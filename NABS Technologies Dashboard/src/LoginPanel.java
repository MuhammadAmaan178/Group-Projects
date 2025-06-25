import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import gui.AdminPanel_MainFrame;
import gui.EmployeeDashboard;

public class LoginPanel extends JFrame {

    public static boolean authenticate(String username, String password, String role) {
        String fileName = "users.csv";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip header if present
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 3) {
                    String csvUser = tokens[0].trim();
                    String csvPass = tokens[1].trim();
                    String csvRole = tokens[2].trim();

                    if (csvUser.equalsIgnoreCase(username) &&
                        csvPass.equals(password) &&
                        csvRole.equalsIgnoreCase(role)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + fileName + ": " + e.getMessage());
        }

        return false;
    }

    public LoginPanel() {
        setTitle("Login Account - Admin Panel");
        setSize(393, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel titleLabel = new JLabel("LOGIN ACCOUNT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(58, 0, 204));
        titleLabel.setBounds(70, 30, 250, 30);
        add(titleLabel);

        JLabel subTitle = new JLabel("Admin Panel", SwingConstants.CENTER);
        subTitle.setFont(new Font("Serif", Font.PLAIN, 16));
        subTitle.setBounds(130, 60, 130, 20);
        add(subTitle);

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setBounds(65, 110, 260, 35);
        usernameField.setBorder(BorderFactory.createTitledBorder("User Name or Email Address"));
        add(usernameField);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBounds(65, 160, 260, 35);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        add(passwordField);

        JLabel roleLabel = new JLabel("Role");
        roleLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        roleLabel.setBounds(65, 210, 40, 30);
        add(roleLabel);

        String[] roles = { "Select Your Role", "Admin", "Employee" };
        JComboBox<String> roleCombo = new JComboBox<>(roles);
        roleCombo.setBounds(110, 215, 215, 25);
        add(roleCombo);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(84, 48, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBounds(120, 270, 140, 40);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(loginButton);

        ImageIcon logoIcon = new ImageIcon(getClass().getResource("nabs_logo.png"));
        Image logo = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        logoLabel.setBounds(90, 300, 200, 200);
        add(logoLabel);

        JLabel issueLabel = new JLabel("Getting Any Issue: ");
        issueLabel.setBounds(80, 490, 130, 30);
        add(issueLabel);

        JLabel contactLabel = new JLabel("Contact Admin");
        contactLabel.setForeground(Color.BLUE.darker());
        contactLabel.setBounds(200, 490, 150, 30);
        add(contactLabel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty() || "Select Your Role".equals(role)) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean valid = authenticate(username, password, role);
            if (valid) {
                JOptionPane.showMessageDialog(this, "Login successful!");

                switch (role.toUpperCase()) {
                    case "ADMIN":
                        new AdminPanel_MainFrame().setVisible(true);
                        break;
                    case "EMPLOYEE":
                        new EmployeeDashboard(username).setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Role not recognized.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }

                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPanel().setVisible(true));
    }
}
