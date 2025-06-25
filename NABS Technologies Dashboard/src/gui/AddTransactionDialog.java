package gui;

import data.CsvDataManager;
import data.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTransactionDialog extends JDialog {

    private JTextField idField, dateField, descriptionField, amountField, typeField;
    private boolean transactionAdded = false;

    public AddTransactionDialog(Window owner) {
        super(owner, "Add New Transaction", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setResizable(false);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        idField = new JTextField();
        dateField = new JTextField();
        descriptionField = new JTextField();
        amountField = new JTextField();
        typeField = new JTextField();

        inputPanel.add(new JLabel("Transaction ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Date (MM/DD/YYYY):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel("Description:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Type (Income/Expense):"));
        inputPanel.add(typeField);

        add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add Transaction");
        JButton cancelButton = new JButton("Cancel");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransaction();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addTransaction() {
        String id = idField.getText().trim();
        String date = dateField.getText().trim();
        String description = descriptionField.getText().trim();
        String amountStr = amountField.getText().trim();
        String type = typeField.getText().trim();

        if (id.isEmpty() || date.isEmpty() || description.isEmpty() || amountStr.isEmpty() || type.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Transaction newTransaction = new Transaction(id, date, description, amount, type);

        try {
            CsvDataManager.addTransaction(newTransaction);
            transactionAdded = true;
            JOptionPane.showMessageDialog(this, "Transaction added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding transaction: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isTransactionAdded() {
        return transactionAdded;
    }
}