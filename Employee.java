abstract class Employee {
    protected String name;
    protected int age;
    protected double salary;
    protected String designation;

    public Employee(String name, int age, double salary, String designation) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.designation = designation;
    }

    public abstract double calculateOvertimeBonus();

    public double calculateTax() {
        return salary * 0.125;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public double getSalary() { return salary; }
    public String getDesignation() { return designation; }

    public void displayDetails() {
        double tax = calculateTax();
        double overtimeBonus = calculateOvertimeBonus();
        double netSalary = salary - tax + overtimeBonus;
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
        System.out.println("Tax: " + tax);
        System.out.println("Overtime/Bonus: " + overtimeBonus);
        System.out.println("Net Salary: " + netSalary);
        System.out.println();
    }
}
