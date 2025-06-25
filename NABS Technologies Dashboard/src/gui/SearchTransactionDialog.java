package gui;

import data.CsvDataManager;
import data.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchTransactionDialog extends JDialog {

    private JTextField idField;
    private JTextArea resultArea;

    public SearchTransactionDialog(Window owner) {
        super(owner, "Search Transaction", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(10, 10));
        setSize(450, 250);
        setLocationRelativeTo(owner);
        setResizable(false);

        // Input Panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.add(new JLabel("Enter Transaction ID to Search:"));
        idField = new JTextField(15);
        inputPanel.add(idField);
        JButton searchButton = new JButton("Search");
        inputPanel.add(searchButton);
        add(inputPanel, BorderLayout.NORTH);

        // Result Area
        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listener
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTransaction();
            }
        });
    }

    private void searchTransaction() {
        String id = idField.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Transaction ID cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            resultArea.setText("");
            return;
        }

        Transaction transaction = CsvDataManager.searchTransactionById(id);

        if (transaction != null) {
            resultArea.setText("Transaction Found:\n\n");
            resultArea.append("ID: " + transaction.getTransactionID() + "\n");
            resultArea.append("Date: " + transaction.getDate() + "\n");
            resultArea.append("Description: " + transaction.getDescription() + "\n");
            resultArea.append("Amount: " + transaction.getAmount() + "\n");
            resultArea.append("Type: " + transaction.getType() + "\n");
        } else {
            resultArea.setText("Transaction with ID '" + id + "' not found.");
        }
    }
}