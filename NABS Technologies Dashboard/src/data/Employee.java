package data;

// Represents an Employee object with various attributes.
public class Employee {
    private String employeeID;
    private String name;
    private String role;
    private String department;
    private String email;
    private String phone;
    private double salary;
    private double bonus;
    private String project;
    private String techStack;
    private String shiftTime;
    private String joinDate;

    // Constructor to initialize an Employee object with all its attributes.
    public Employee(String employeeID, String name, String role, String department,
                    String email, String phone, double salary, double bonus,
                    String project, String techStack, String shiftTime, String joinDate) {
        this.employeeID = employeeID;
        this.name = name;
        this.role = role;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.salary = salary;
        this.bonus = bonus;
        this.project = project;
        this.techStack = techStack;
        this.shiftTime = shiftTime;
        this.joinDate = joinDate;
    }

    // --- Getters ---
    public String getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public double getSalary() {
        return salary;
    }

    public double getBonus() {
        return bonus;
    }

    public String getProject() {
        return project;
    }

    public String getTechStack() {
        return techStack;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public String getJoinDate() {
        return joinDate;
    }

    // --- Setters ---
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setTechStack(String techStack) {
        this.techStack = techStack;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    // Override toString for easy printing of Employee details.
    @Override
    public String toString() {
        return "EmployeeID: " + employeeID + "\n" +
                "Name: " + name + "\n" +
                "Role: " + role + "\n" +
                "Department: " + department + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Salary: " + salary + "\n" +
                "Bonus: " + bonus + "\n" +
                "Project: " + project + "\n" +
                "TechStack: " + techStack + "\n" +
                "ShiftTime: " + shiftTime + "\n" +
                "JoinDate: " + joinDate;
    }
}
