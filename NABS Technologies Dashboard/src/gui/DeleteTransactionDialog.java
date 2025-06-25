package gui;

import data.CsvDataManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteTransactionDialog extends JDialog {

    private JTextField idField;
    private boolean transactionDeleted = false;

    public DeleteTransactionDialog(Window owner) {
        super(owner, "Delete Transaction", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10, 10));
        setSize(350, 150);
        setLocationRelativeTo(owner);
        setResizable(false);

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(new JLabel("Enter Transaction ID to Delete:"));
        idField = new JTextField(15);
        inputPanel.add(idField);
        add(inputPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton deleteButton = new JButton("Delete");
        JButton cancelButton = new JButton("Cancel");

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTransaction();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void deleteTransaction() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Transaction ID cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete transaction with ID: " + id + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (CsvDataManager.deleteTransaction(id)) {
                transactionDeleted = true;
                JOptionPane.showMessageDialog(this, "Transaction deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Transaction with ID " + id + " not found.", "Not Found", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public boolean isTransactionDeleted() {
        return transactionDeleted;
    }
}