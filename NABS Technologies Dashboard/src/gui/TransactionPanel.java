package gui;

import data.CsvDataManager;
import data.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransactionPanel extends JPanel {

    private JTable transactionTable;
    private DefaultTableModel tableModel;

    public TransactionPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(230, 230, 230));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(230, 230, 230));
        JLabel titleLabel = new JLabel("Transaction Management");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(new Color(50, 70, 90));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(230, 230, 230));

        JButton addButton = createStyledButton("ADD");
        JButton deleteButton = createStyledButton("DELETE");
        JButton searchButton = createStyledButton("SEARCH");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Table Panel
        String[] columnNames = {"TransactionID", "Date", "Description", "Amount", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        transactionTable.setRowHeight(25);
        transactionTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        transactionTable.getTableHeader().setBackground(new Color(50, 70, 90));
        transactionTable.getTableHeader().setForeground(Color.WHITE);
        transactionTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load initial data
        refreshTable();

        // Action Listeners for buttons
        addButton.addActionListener(e -> {
            AddTransactionDialog addDialog = new AddTransactionDialog(SwingUtilities.getWindowAncestor(this));
            addDialog.setVisible(true);
            if (addDialog.isTransactionAdded()) {
                refreshTable();
            }
        });

        deleteButton.addActionListener(e -> {
            DeleteTransactionDialog deleteDialog = new DeleteTransactionDialog(SwingUtilities.getWindowAncestor(this));
            deleteDialog.setVisible(true);
            if (deleteDialog.isTransactionDeleted()) {
                refreshTable();
            }
        });

        searchButton.addActionListener(e -> {
            SearchTransactionDialog searchDialog = new SearchTransactionDialog(SwingUtilities.getWindowAncestor(this));
            searchDialog.setVisible(true);
        });
    }

    // Refreshes the table with the latest data.
    public void refreshTable() {
        tableModel.setRowCount(0); // Clear existing data
        List<Transaction> transactions = CsvDataManager.readTransactions();
        for (Transaction txn : transactions) {
            tableModel.addRow(new Object[]{
                    txn.getTransactionID(),
                    txn.getDate(),
                    txn.getDescription(),
                    txn.getAmount(),
                    txn.getType()
            });
        }
    }

    // Helper method to create simple buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(new Color(70, 90, 110));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Simple padding border
        return button;
    }
}