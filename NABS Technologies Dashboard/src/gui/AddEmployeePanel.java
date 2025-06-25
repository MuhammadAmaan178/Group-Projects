package gui;

import data.CsvDataManager;
import data.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEmployeePanel extends JPanel {

    private JTextField nameField, roleField, departmentField, emailField, phoneField,
            salaryField, bonusField, projectField, techStackField,
            shiftTimeField, joinDateField;
    private JLabel employeeIdLabel;

    public AddEmployeePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 230, 230));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(230, 230, 230));
        JLabel titleLabel = new JLabel("Add Employee");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 70, 90));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Input Form Panel
        JPanel formPanel = new JPanel(new GridLayout(12, 2, 10, 10));
        formPanel.setBackground(new Color(230, 230, 230));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        employeeIdLabel = new JLabel();
        employeeIdLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        employeeIdLabel.setForeground(new Color(0, 100, 0));
        updateEmployeeIdLabel();

        nameField = new JTextField();
        roleField = new JTextField();
        departmentField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();
        salaryField = new JTextField();
        bonusField = new JTextField();
        projectField = new JTextField();
        techStackField = new JTextField();
        shiftTimeField = new JTextField();
        joinDateField = new JTextField();

        formPanel.add(new JLabel("Employee ID:"));
        formPanel.add(employeeIdLabel);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleField);
        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Salary:"));
        formPanel.add(salaryField);
        formPanel.add(new JLabel("Bonus:"));
        formPanel.add(bonusField);
        formPanel.add(new JLabel("Project:"));
        formPanel.add(projectField);
        formPanel.add(new JLabel("Tech Stack:"));
        formPanel.add(techStackField);
        formPanel.add(new JLabel("Shift Time:"));
        formPanel.add(shiftTimeField);
        formPanel.add(new JLabel("Join Date (MM/DD/YYYY):"));
        formPanel.add(joinDateField);

        add(formPanel, BorderLayout.CENTER);

        // Add Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(230, 230, 230));
        JButton addButton = createStyledButton("Add Employee");
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listener
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });
    }

    private void updateEmployeeIdLabel() {
        employeeIdLabel.setText(CsvDataManager.getNextEmployeeId());
    }

    private void clearFields() {
        nameField.setText("");
        roleField.setText("");
        departmentField.setText("");
        emailField.setText("");
        phoneField.setText("");
        salaryField.setText("");
        bonusField.setText("");
        projectField.setText("");
        techStackField.setText("");
        shiftTimeField.setText("");
        joinDateField.setText("");
        updateEmployeeIdLabel();
    }

    private void addEmployee() {
        String employeeID = employeeIdLabel.getText();
        String name = nameField.getText().trim();
        String role = roleField.getText().trim();
        String department = departmentField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String salaryStr = salaryField.getText().trim();
        String bonusStr = bonusField.getText().trim();
        String project = projectField.getText().trim();
        String techStack = techStackField.getText().trim();
        String shiftTime = shiftTimeField.getText().trim();
        String joinDate = joinDateField.getText().trim();

        if (name.isEmpty() || role.isEmpty() || department.isEmpty() || email.isEmpty() ||
                phone.isEmpty() || salaryStr.isEmpty() || bonusStr.isEmpty() || project.isEmpty() ||
                techStack.isEmpty() || shiftTime.isEmpty() || joinDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double salary, bonus;
        try {
            salary = Double.parseDouble(salaryStr);
            bonus = Double.parseDouble(bonusStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salary and Bonus must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employee newEmployee = new Employee(employeeID, name, role, department,
                email, phone, salary, bonus,
                project, techStack, shiftTime, joinDate);

        try {
            CsvDataManager.addEmployee(newEmployee);
            JOptionPane.showMessageDialog(this, "Employee added successfully with ID: " + employeeID, "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helper method to create simple buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(new Color(70, 90, 110));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}