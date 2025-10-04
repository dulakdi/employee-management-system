import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private Connection conn;

    public DatabaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL + "?serverTimezone=UTC", USER, PASSWORD);
            System.out.println("Database connected successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean insertEmployee(String name, int age, double salary, String designation) {
        try (CallableStatement cs = conn.prepareCall("{call InsertEmployee(?, ?, ?, ?)}")) {
            cs.setString(1, name);
            cs.setInt(2, age);
            cs.setDouble(3, salary);
            cs.setString(4, designation);
            cs.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(int id, String name, int age, double salary, String designation) {
        double tax = salary * 0.125;
        double overtime = designation.equals("Manager") ? salary * 0.1 : 0;
        double bonus = designation.equals("Executive") ? salary * 0.15 : 0;
        double net = salary - tax + overtime + bonus;
        String sql = "UPDATE employees SET name=?, age=?, salary=?, designation=?, overtime_rate=?, bonus=?, tax_amount=?, net_salary=? WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setDouble(3, salary);
            stmt.setString(4, designation);
            stmt.setDouble(5, overtime);
            stmt.setDouble(6, bonus);
            stmt.setDouble(7, tax);
            stmt.setDouble(8, net);
            stmt.setInt(9, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public EmployeeRecord calculateEmployeeTaxSalary(int id) {
        try (CallableStatement cs = conn.prepareCall("{call CalculateEmployeeTaxSalary(?)}")) {
            cs.setInt(1, id);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return new EmployeeRecord(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getDouble("salary"),
                        rs.getString("designation"),
                        rs.getDouble("overtime_rate"),
                        rs.getDouble("bonus"),
                        rs.getDouble("tax_amount"),
                        rs.getDouble("net_salary")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EmployeeRecord> getEmployeesByDesignation(String designation) {
        List<EmployeeRecord> list = new ArrayList<>();
        try (CallableStatement cs = conn.prepareCall("{call GetEmployeesByDesignation(?)}")) {
            cs.setString(1, designation);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) {
                    EmployeeRecord rec = new EmployeeRecord(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getDouble("salary"),
                        rs.getString("designation"),
                        rs.getDouble("overtime_rate"),
                        rs.getDouble("bonus"),
                        rs.getDouble("tax_amount"),
                        rs.getDouble("net_salary")
                    );
                    list.add(rec);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<EmployeeRecord> getAllEmployees() {
        List<EmployeeRecord> list = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                EmployeeRecord rec = new EmployeeRecord(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getDouble("salary"),
                    rs.getString("designation"),
                    rs.getDouble("overtime_rate"),
                    rs.getDouble("bonus"),
                    rs.getDouble("tax_amount"),
                    rs.getDouble("net_salary")
                );
                list.add(rec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void close() {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
