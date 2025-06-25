package gui;

import data.CsvDataManager;
import data.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProjectDetailsPanel extends JPanel {

    private JTable projectDetailsTable;
    private DefaultTableModel tableModel;

    public ProjectDetailsPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 230, 230));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(230, 230, 230));
        JLabel titleLabel = new JLabel("Project Details");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 70, 90));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Table Panel
        String[] columnNames = {"ID", "Projects", "Skills"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        projectDetailsTable = new JTable(tableModel);
        projectDetailsTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        projectDetailsTable.setRowHeight(25);
        projectDetailsTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        projectDetailsTable.getTableHeader().setBackground(new Color(50, 70, 90));
        projectDetailsTable.getTableHeader().setForeground(Color.WHITE);
        projectDetailsTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(projectDetailsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        refreshTable();
    }

    // Refreshes the table with the latest data.
    public void refreshTable() {
        tableModel.setRowCount(0);
        List<Employee> employees = CsvDataManager.readEmployees();
        for (Employee emp : employees) {
            tableModel.addRow(new Object[]{
                    emp.getEmployeeID(),
                    emp.getProject(),
                    emp.getTechStack()
            });
        }
    }
}