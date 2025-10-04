public class EmployeeRecord {
    private int id;
    private String name;
    private int age;
    private double salary;
    private String designation;
    private double overtimeRate;
    private double bonus;
    private double taxAmount;
    private double netSalary;

    public EmployeeRecord(int id, String name, int age, double salary, String designation, double overtimeRate, double bonus, double taxAmount, double netSalary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.designation = designation;
        this.overtimeRate = overtimeRate;
        this.bonus = bonus;
        this.taxAmount = taxAmount;
        this.netSalary = netSalary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }
    public String getDesignation() { return designation; }
    public double getOvertimeRate() { return overtimeRate; }
    public double getBonus() { return bonus; }
    public double getTaxAmount() { return taxAmount; }
    public double getNetSalary() { return netSalary; }
}
