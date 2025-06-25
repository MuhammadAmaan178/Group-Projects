package gui;

import data.CsvDataManager;
import data.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeSalaryPanel extends JPanel {

    private JTable employeeSalaryTable;
    private DefaultTableModel tableModel;

    public EmployeeSalaryPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 230, 230));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(230, 230, 230));
        JLabel titleLabel = new JLabel("Employee Salary");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 70, 90));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {"ID", "Name", "Salary", "Bonus"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeSalaryTable = new JTable(tableModel);
        employeeSalaryTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        employeeSalaryTable.setRowHeight(25);
        employeeSalaryTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        employeeSalaryTable.getTableHeader().setBackground(new Color(50, 70, 90));
        employeeSalaryTable.getTableHeader().setForeground(Color.WHITE);
        employeeSalaryTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(employeeSalaryTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        refreshTable();
    }

    // Refreshes the table with the latest data.
    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Employee> employees = CsvDataManager.readEmployees();
        int count = 0;
        for (Employee emp : employees) {
            if (count >= 50) break;
            tableModel.addRow(new Object[]{
                    emp.getEmployeeID(),
                    emp.getName(),
                    emp.getSalary(),
                    emp.getBonus()
            });
            count++;
        }
    }
}