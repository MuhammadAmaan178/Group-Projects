package data;

// Represents a Transaction object with its details.
public class Transaction {
    private String transactionID;
    private String date;
    private String description;
    private double amount;
    private String type;

    // Constructor to initialize a Transaction object.
    public Transaction(String transactionID, String date, String description, double amount, String type) {
        this.transactionID = transactionID;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    // --- Getters ---
    public String getTransactionID() {
        return transactionID;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    // --- Setters ---
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Override toString for easy printing of Transaction details.
    @Override
    public String toString() {
        return "TransactionID: " + transactionID +
                ", Date: " + date +
                ", Description: " + description +
                ", Amount: " + amount +
                ", Type: " + type;
    }
}
