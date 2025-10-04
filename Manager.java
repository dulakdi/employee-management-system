class Manager extends Employee {
    public Manager(String name, int age, double salary) {
        super(name, age, salary, "Manager");
    }

    @Override
    public double calculateOvertimeBonus() {
        return salary * 0.1; // Overtime for Manager
    }
}
