package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EmployeeDashboard extends JFrame {

    private String[] employeeData;
    private String employeeEmail;
    private JPanel contentPanel;

    // Define colors
    private static final Color HEADER_BG_COLOR = new Color(50, 70, 90);
    private static final Color SIDEBAR_BG_COLOR = new Color(40, 60, 80);
    private static final Color CONTENT_BG_COLOR = Color.WHITE;
    private static final Color TITLE_COLOR = new Color(58, 0, 204);
    private static final Color BUTTON_DEFAULT_BG = new Color(60, 80, 100);
    private static final Color BUTTON_HOVER_BG = new Color(80, 100, 120);

    public EmployeeDashboard(String email) {
        this.employeeEmail = email;
        loadEmployeeData();
        initializeUI();
    }

    private void loadEmployeeData() {
        final String FILENAME = "employees.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 12 && tokens[4].trim().equalsIgnoreCase(employeeEmail)) {
                    employeeData = tokens;
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading " + FILENAME + ": " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + e.getMessage(), "Data Error", JOptionPane.ERROR_MESSAGE);
        }

        if (employeeData == null) {
            JOptionPane.showMessageDialog(this, "Employee data not found for email: " + employeeEmail, "Data Missing", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void initializeUI() {
        setTitle("Employee Dashboard");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(HEADER_BG_COLOR);
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        headerPanel.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel(" NABS Technologies", SwingConstants.LEFT);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        logoLabel.setForeground(Color.WHITE);

        // Image loading
        ImageIcon nabsLogoIcon = new ImageIcon(new ImageIcon("nabs_logo.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        logoLabel.setIcon(nabsLogoIcon);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JLabel dashboardLabel = new JLabel("DashBoard", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        dashboardLabel.setForeground(Color.WHITE); // Keep white for contrast
        headerPanel.add(dashboardLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // Sidebar Panel
        JPanel sidebar = new JPanel();
        sidebar.setBackground(SIDEBAR_BG_COLOR); // Background color kept
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(250, getHeight()));

        JLabel profileIcon = new JLabel();
        ImageIcon userIcon = new ImageIcon(new ImageIcon("user_icon.png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        profileIcon.setIcon(userIcon);
        profileIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(profileIcon);

        JLabel welcomeLabel = new JLabel((employeeData != null && employeeData.length > 1 ? employeeData[1] : "Employee"), SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(welcomeLabel);

        sidebar.add(Box.createVerticalStrut(40));

        // Sidebar Navigation Buttons
        sidebar.add(createSidebarButton("About", e -> showAboutPanel()));
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createSidebarButton("Salary Details", e -> showSalaryPanel()));
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createSidebarButton("Project", e -> showProjectPanel()));

        sidebar.add(Box.createVerticalGlue());

        // Logout Button
        JButton logoutButton = new JButton("LOGOUT"); // Removed unicode symbol
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutButton.setForeground(Color.WHITE); // Keep white for contrast
        logoutButton.setBackground(Color.RED); // Keep red for logout
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(EmployeeDashboard.this,
                        "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(logoutButton);
        sidebar.add(Box.createVerticalStrut(10));

        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setBackground(CONTENT_BG_COLOR);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        showAboutPanel();
    }

    private void showAboutPanel() {
        contentPanel.removeAll();
        contentPanel.add(createTitleLabel("About Employee"));
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel panel = new JPanel(new GridLayout(7, 2, 20, 15));
        panel.setMaximumSize(new Dimension(700, 250));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (employeeData != null && employeeData.length >= 11) {
            addInfoRow(panel, "ID:", employeeData[0]);
            addInfoRow(panel, "Name:", employeeData[1]);
            addInfoRow(panel, "Role:", employeeData[2]);
            addInfoRow(panel, "Department:", employeeData[3]);
            addInfoRow(panel, "Phone:", employeeData[5]);
            addInfoRow(panel, "Email:", employeeData[4]);
            addInfoRow(panel, "Shift Time:", employeeData[10]);
        } else {
            panel.add(new JLabel("No employee data available."));
        }

        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showSalaryPanel() {
        contentPanel.removeAll();
        contentPanel.add(createTitleLabel("Salary Details"));
        contentPanel.add(Box.createVerticalStrut(10));
        JPanel panel = new JPanel(new GridLayout(4, 2, 20, 15));
        panel.setMaximumSize(new Dimension(700, 180));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (employeeData != null && employeeData.length >= 8) {
            addInfoRow(panel, "ID:", employeeData[0]);
            addInfoRow(panel, "Name:", employeeData[1]);
            addInfoRow(panel, "Salary:", employeeData[6]);
            addInfoRow(panel, "Bonus:", employeeData[7]);
        } else {
            panel.add(new JLabel("No salary data available."));
        }

        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showProjectPanel() {
        contentPanel.removeAll();
        contentPanel.add(createTitleLabel("Project Details"));
        contentPanel.add(Box.createVerticalStrut(10));
        JPanel panel = new JPanel(new GridLayout(5, 2, 20, 15));
        panel.setMaximumSize(new Dimension(700, 200));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (employeeData != null && employeeData.length >= 10) {
            addInfoRow(panel, "ID:", employeeData[0]);
            addInfoRow(panel, "Name:", employeeData[1]);
            addInfoRow(panel, "Role:", employeeData[2]);
            addInfoRow(panel, "Project:", employeeData[8]);
            addInfoRow(panel, "Tech Stack:", employeeData[9]);
        } else {
            panel.add(new JLabel("No project data available."));
        }

        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Creates a title label for content panels.
    private JLabel createTitleLabel(String title) {
        JLabel label = new JLabel(title, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 20));
        label.setForeground(TITLE_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        return label;
    }

    // Adds a label-value pair to a JPanel.
    private void addInfoRow(JPanel panel, String label, String value) {
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelComponent.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("SansSerif", Font.PLAIN, 14));
        valueComponent.setHorizontalAlignment(SwingConstants.LEFT);
        panel.add(labelComponent);
        panel.add(valueComponent);
    }

    // Creates a basic sidebar navigation button with background colors.
    private JButton createSidebarButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(180, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(BUTTON_DEFAULT_BG);
        button.setForeground(Color.WHITE);
        button.addActionListener(actionListener);
        return button;
    }
}