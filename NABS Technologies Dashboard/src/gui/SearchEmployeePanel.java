package gui;

import data.CsvDataManager;
import data.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SearchEmployeePanel extends JPanel {

    private JTextField idField;
    private JTextArea resultArea;

    public SearchEmployeePanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 230, 230));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(230, 230, 230));
        JLabel titleLabel = new JLabel("Search Employee");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 70, 90));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Search Input Panel
        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        searchInputPanel.setBackground(new Color(230, 230, 230));
        searchInputPanel.add(new JLabel("Enter Employee ID:"));
        idField = new JTextField(15);
        searchInputPanel.add(idField);
        JButton searchButton = createStyledButton("SEARCH");
        searchInputPanel.add(searchButton);
        add(searchInputPanel, BorderLayout.CENTER);

        // Result Area
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.SOUTH);

        // Action Listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchEmployee();
            }
        });
    }

    private void searchEmployee() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Employee ID cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            resultArea.setText("");
            return;
        }

        List<Employee> employees = CsvDataManager.readEmployees();
        Employee foundEmployee = null;
        for (Employee emp : employees) {
            if (emp.getEmployeeID().equalsIgnoreCase(id)) {
                foundEmployee = emp;
                break;
            }
        }

        if (foundEmployee != null) {
            resultArea.setText("");
            resultArea.append("Name: " + foundEmployee.getName() + "\n");
            resultArea.append("ID: " + foundEmployee.getEmployeeID() + "\n");
            resultArea.append("Role: " + foundEmployee.getRole() + "\n");
            resultArea.append("Department: " + foundEmployee.getDepartment() + "\n");
            resultArea.append("Email: " + foundEmployee.getEmail() + "\n");
            resultArea.append("Phone: " + foundEmployee.getPhone() + "\n");
            resultArea.append("Salary: " + foundEmployee.getSalary() + "\n");
            resultArea.append("Bonus: " + foundEmployee.getBonus() + "\n");
            resultArea.append("Project: " + foundEmployee.getProject() + "\n");
            resultArea.append("TechStack: " + foundEmployee.getTechStack() + "\n");
            resultArea.append("ShiftTime: " + foundEmployee.getShiftTime() + "\n");
            resultArea.append("JoinDate: " + foundEmployee.getJoinDate() + "\n");
        } else {
            resultArea.setText("Employee with ID '" + id + "' not found.");
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