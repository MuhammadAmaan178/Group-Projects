package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel_MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public AdminPanel_MainFrame() {
        setTitle("NABS Technologies - Admin Panel");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Header Panel ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 70, 90));
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setLayout(new BorderLayout());

        JLabel logoLabel = new JLabel(" NABS Technologies", SwingConstants.LEFT);
        logoLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));
        logoLabel.setForeground(Color.WHITE);
        ImageIcon nabsLogoIcon = new ImageIcon(new ImageIcon("nabs_logo.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        logoLabel.setIcon(nabsLogoIcon);
        headerPanel.add(logoLabel, BorderLayout.WEST);

        JLabel dashboardLabel = new JLabel("DashBoard", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        dashboardLabel.setForeground(Color.WHITE);
        headerPanel.add(dashboardLabel, BorderLayout.CENTER);

        add(headerPanel, BorderLayout.NORTH);

        // --- Sidebar Panel ---
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(40, 60, 80));
        sidebarPanel.setPreferredSize(new Dimension(250, 0));
        sidebarPanel.setLayout(new BorderLayout());

        JPanel adminPanelSection = new JPanel();
        adminPanelSection.setLayout(new BoxLayout(adminPanelSection, BoxLayout.Y_AXIS));
        adminPanelSection.setBackground(new Color(40, 60, 80));
        adminPanelSection.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Admin Panel User Icon and Label
        JLabel userIconLabel = new JLabel();
        ImageIcon userIcon = new ImageIcon(new ImageIcon("user_icon.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH));
        userIconLabel.setIcon(userIcon);
        userIconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanelSection.add(userIconLabel);
        adminPanelSection.add(Box.createVerticalStrut(10));

        JLabel adminLabel = new JLabel("Admin Panel", SwingConstants.CENTER);
        adminLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        adminLabel.setForeground(Color.WHITE);
        adminLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminPanelSection.add(adminLabel);
        adminPanelSection.add(Box.createVerticalStrut(30));

        sidebarPanel.add(adminPanelSection, BorderLayout.NORTH);

        // Navigation Buttons Panel
        JPanel navButtonsPanel = new JPanel();
        navButtonsPanel.setLayout(new GridLayout(5, 1, 0, 10));
        navButtonsPanel.setBackground(new Color(40, 60, 80));
        navButtonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JButton btnTransaction = createNavButton("Transaction Management", "transactionPanel");
        JButton btnEmployeeSalary = createNavButton("Employee Salary", "employeeSalaryPanel");
        JButton btnProjectDetails = createNavButton("Projects Details", "projectDetailsPanel");
        JButton btnSearchEmployee = createNavButton("Search Employee", "searchEmployeePanel");
        JButton btnAddEmployee = createNavButton("Add Employee", "addEmployeePanel");

        navButtonsPanel.add(btnTransaction);
        navButtonsPanel.add(btnEmployeeSalary);
        navButtonsPanel.add(btnProjectDetails);
        navButtonsPanel.add(btnSearchEmployee);
        navButtonsPanel.add(btnAddEmployee);

        sidebarPanel.add(navButtonsPanel, BorderLayout.CENTER);

        // Logout Button
        JButton btnLogout = new JButton("LogOut");
        btnLogout.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(60, 80, 100));
        ImageIcon logoutIcon = new ImageIcon(new ImageIcon("logout_icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        btnLogout.setIcon(logoutIcon);
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logging out...");
            System.exit(0);
        });
        sidebarPanel.add(btnLogout, BorderLayout.SOUTH);

        add(sidebarPanel, BorderLayout.WEST);

        // --- Content Panel ---
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(new Color(230, 230, 230));

        // Initialize and add all content panels
        // These classes must exist in the 'gui' package
        TransactionPanel transactionPanel = new TransactionPanel();
        EmployeeSalaryPanel employeeSalaryPanel = new EmployeeSalaryPanel();
        ProjectDetailsPanel projectDetailsPanel = new ProjectDetailsPanel();
        SearchEmployeePanel searchEmployeePanel = new SearchEmployeePanel();
        AddEmployeePanel addEmployeePanel = new AddEmployeePanel();

        contentPanel.add(transactionPanel, "transactionPanel");
        contentPanel.add(employeeSalaryPanel, "employeeSalaryPanel");
        contentPanel.add(projectDetailsPanel, "projectDetailsPanel");
        contentPanel.add(searchEmployeePanel, "searchEmployeePanel");
        contentPanel.add(addEmployeePanel, "addEmployeePanel");

        add(contentPanel, BorderLayout.CENTER);

        // Show the Transaction Management panel by default
        cardLayout.show(contentPanel, "transactionPanel");
    }

    // Helper method to create simple navigation buttons
    private JButton createNavButton(String text, String panelName) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 80, 100));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, panelName);
                // Refresh logic
                if (panelName.equals("transactionPanel")) {
                    ((TransactionPanel) contentPanel.getComponent(0)).refreshTable();
                } else if (panelName.equals("employeeSalaryPanel")) {
                    ((EmployeeSalaryPanel) contentPanel.getComponent(1)).refreshTable();
                } else if (panelName.equals("projectDetailsPanel")) {
                    ((ProjectDetailsPanel) contentPanel.getComponent(2)).refreshTable();
                }
            }
        });
        return button;
    }
}