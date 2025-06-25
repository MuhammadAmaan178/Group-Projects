package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;

// Manages reading from and writing to CSV files for Employee and Transaction data.
public class CsvDataManager {

    private static final String EMPLOYEES_CSV_FILE = "employees.csv";
    private static final String TRANSACTIONS_CSV_FILE = "transactions.csv";

    // --- Employee CSV Operations ---

    // Reads all employee records from the employees.csv file.
    public static List<Employee> readEmployees() {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(EMPLOYEES_CSV_FILE))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 12) { // Ensure all fields are present
                    try {
                        String employeeID = data[0].trim();
                        String name = data[1].trim();
                        String role = data[2].trim();
                        String department = data[3].trim();
                        String email = data[4].trim();
                        String phone = data[5].trim();
                        double salary = Double.parseDouble(data[6].trim());
                        double bonus = Double.parseDouble(data[7].trim());
                        String project = data[8].trim();
                        String techStack = data[9].trim();
                        String shiftTime = data[10].trim();
                        String joinDate = data[11].trim();

                        employees.add(new Employee(employeeID, name, role, department,
                                email, phone, salary, bonus, project, techStack, shiftTime, joinDate));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed employee data line: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping incomplete employee data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Employee CSV file not found: " + EMPLOYEES_CSV_FILE);
            // Optionally, create an empty file or handle this more gracefully in GUI
        } catch (IOException e) {
            System.err.println("Error reading employee CSV file: " + e.getMessage());
        }
        return employees;
    }

    // Writes a list of employee records to the employees.csv file.
    public static void writeEmployees(List<Employee> employees) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(EMPLOYEES_CSV_FILE))) {
            // Write header
            bw.write("EmployeeID,Name,Role,Department,Email,Phone,Salary,Bonus,Project,TechStack,ShiftTime,JoinDate\n");
            for (Employee emp : employees) {
                bw.write(String.join(",",
                        emp.getEmployeeID(), emp.getName(), emp.getRole(), emp.getDepartment(),
                        emp.getEmail(), emp.getPhone(), String.valueOf(emp.getSalary()),
                        String.valueOf(emp.getBonus()), emp.getProject(), emp.getTechStack(),
                        emp.getShiftTime(), emp.getJoinDate()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing employee CSV file: " + e.getMessage());
        }
    }

    // Adds a new employee to the employees.csv file.
    public static void addEmployee(Employee newEmployee) {
        List<Employee> employees = readEmployees();
        employees.add(newEmployee);
        writeEmployees(employees);
    }

    // Finds the next available employee ID by finding the maximum existing ID and incrementing it.
    public static String getNextEmployeeId() {
        List<Employee> employees = readEmployees();
        Optional<Integer> maxId = employees.stream()
                .map(e -> {
                    try {
                        return Integer.parseInt(e.getEmployeeID());
                    } catch (NumberFormatException ex) {
                        return 0; // Handle non-numeric IDs, treat as 0 for max calculation
                    }
                })
                .max(Comparator.naturalOrder());

        int nextId = maxId.orElse(1000) + 1; // Start from 1001 if no employees or invalid IDs
        return String.valueOf(nextId);
    }

    // --- Transaction CSV Operations ---

    // Reads all transaction records from the transactions.csv file.
    public static List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(TRANSACTIONS_CSV_FILE))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) { // Ensure all fields are present
                    try {
                        String transactionID = data[0].trim();
                        String date = data[1].trim();
                        String description = data[2].trim();
                        double amount = Double.parseDouble(data[3].trim());
                        String type = data[4].trim();

                        transactions.add(new Transaction(transactionID, date, description, amount, type));
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping malformed transaction data line: " + line + " - " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping incomplete transaction data line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Transaction CSV file not found: " + TRANSACTIONS_CSV_FILE);
            // Optionally, create an empty file or handle this more gracefully in GUI
        } catch (IOException e) {
            System.err.println("Error reading transaction CSV file: " + e.getMessage());
        }
        return transactions;
    }

    // Writes a list of transaction records to the transactions.csv file.
    public static void writeTransactions(List<Transaction> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TRANSACTIONS_CSV_FILE))) {
            // Write header
            bw.write("TransactionID,Date,Description,Amount,Type\n");
            for (Transaction txn : transactions) {
                bw.write(String.join(",",
                        txn.getTransactionID(), txn.getDate(), txn.getDescription(),
                        String.valueOf(txn.getAmount()), txn.getType()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing transaction CSV file: " + e.getMessage());
        }
    }

    // Adds a new transaction to the transactions.csv file.
    public static void addTransaction(Transaction newTransaction) {
        List<Transaction> transactions = readTransactions();
        transactions.add(newTransaction);
        writeTransactions(transactions);
    }

    // Deletes a transaction by its ID from the transactions.csv file.
    public static boolean deleteTransaction(String transactionId) {
        List<Transaction> transactions = readTransactions();
        boolean removed = transactions.removeIf(t -> t.getTransactionID().equalsIgnoreCase(transactionId));
        if (removed) {
            writeTransactions(transactions);
        }
        return removed;
    }

    // Searches for a transaction by its ID.
    public static Transaction searchTransactionById(String transactionId) {
        List<Transaction> transactions = readTransactions();
        return transactions.stream()
                .filter(t -> t.getTransactionID().equalsIgnoreCase(transactionId))
                .findFirst()
                .orElse(null);
    }
}
