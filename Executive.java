class Executive extends Employee {
    public Executive(String name, int age, double salary) {
        super(name, age, salary, "Executive");
    }

    @Override
    public double calculateOvertimeBonus() {
        return salary * 0.15; // Bonus for Executive
    }
}
