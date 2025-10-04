import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class EmployeeUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField salaryField;
    private JComboBox<String> designationBox;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JTable table;
    private DefaultTableModel model;
    private DatabaseManager db;
    private int selectedId = -1;

    public EmployeeUI() {
        db = new DatabaseManager();
        initializeUI();
        updateTable();
    }

    private JComboBox<String> filterDesignationBox;
    private JButton filterButton;
    private JButton calculateButton;

    private void initializeUI() {
        frame = new JFrame("Employee Management System");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(8, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Age:"));
        ageField = new JTextField();
        inputPanel.add(ageField);

        inputPanel.add(new JLabel("Salary:"));
        salaryField = new JTextField();
        inputPanel.add(salaryField);

        inputPanel.add(new JLabel("Designation:"));
        designationBox = new JComboBox<>(new String[]{"Manager", "Executive"});
        inputPanel.add(designationBox);

        addButton = new JButton("Add Employee");
        inputPanel.add(addButton);

        updateButton = new JButton("Update Selected");
        inputPanel.add(updateButton);

        deleteButton = new JButton("Delete Selected");
        inputPanel.add(deleteButton);

        refreshButton = new JButton("Refresh");
        inputPanel.add(refreshButton);

        inputPanel.add(new JLabel("Filter by Designation:"));
        filterDesignationBox = new JComboBox<>(new String[]{"All", "Manager", "Executive"});
        inputPanel.add(filterDesignationBox);

        filterButton = new JButton("Filter");
        inputPanel.add(filterButton);

        calculateButton = new JButton("Calculate Tax/Salary");
        inputPanel.add(calculateButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Salary", "Designation", "Tax Amount", "Overtime Rate", "Bonus", "Net Salary"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addEmployee();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateEmployee();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteEmployee();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable();
            }
        });

        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterEmployees();
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateTaxSalary();
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = table.getSelectedRow();
                    if (row >= 0) {
                        selectedId = (int) model.getValueAt(row, 0);
                        nameField.setText((String) model.getValueAt(row, 1));
                        ageField.setText(String.valueOf(model.getValueAt(row, 2)));
                        salaryField.setText(String.valueOf(model.getValueAt(row, 3)));
                        designationBox.setSelectedItem(model.getValueAt(row, 4));
                    }
                }
            }
        });

        frame.add(panel);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void addEmployee() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 100) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age (1-100).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (salary <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid salary (greater than 0).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String designation = (String) designationBox.getSelectedItem();

            if (db.insertEmployee(name, age, salary, designation)) {
                JOptionPane.showMessageDialog(frame, "Employee record added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateTable();
                // Clear fields
                nameField.setText("");
                ageField.setText("");
                salaryField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Error in adding record", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Age and Salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateEmployee() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int age = Integer.parseInt(ageField.getText().trim());
            if (age <= 0 || age > 100) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid age (1-100).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double salary = Double.parseDouble(salaryField.getText().trim());
            if (salary <= 0) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid salary (greater than 0).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String designation = (String) designationBox.getSelectedItem();

            if (db.updateEmployee(selectedId, name, age, salary, designation)) {
                JOptionPane.showMessageDialog(frame, "Employee record updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateTable();
                selectedId = -1;
                nameField.setText("");
                ageField.setText("");
                salaryField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Error in updating record", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numbers for Age and Salary.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this employee?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            if (db.deleteEmployee(selectedId)) {
                JOptionPane.showMessageDialog(frame, "Employee record deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                updateTable();
                selectedId = -1;
                nameField.setText("");
                ageField.setText("");
                salaryField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Error in deleting record", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateTable() {
        model.setRowCount(0);
        List<EmployeeRecord> records = db.getAllEmployees();
        for (EmployeeRecord rec : records) {
            model.addRow(new Object[]{
                rec.getId(),
                rec.getName(),
                rec.getAge(),
                rec.getSalary(),
                rec.getDesignation(),
                rec.getTaxAmount(),
                rec.getOvertimeRate(),
                rec.getBonus(),
                rec.getNetSalary()
            });
        }
    }

    private void filterEmployees() {
        String designation = (String) filterDesignationBox.getSelectedItem();
        if ("All".equals(designation)) {
            updateTable();
        } else {
            List<EmployeeRecord> filtered = db.getEmployeesByDesignation(designation);
            model.setRowCount(0);
            for (EmployeeRecord rec : filtered) {
                model.addRow(new Object[]{
                    rec.getId(),
                    rec.getName(),
                    rec.getAge(),
                    rec.getSalary(),
                    rec.getDesignation(),
                    rec.getTaxAmount(),
                    rec.getOvertimeRate(),
                    rec.getBonus(),
                    rec.getNetSalary()
                });
            }
        }
    }

    private void calculateTaxSalary() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(frame, "Please select an employee to calculate.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        EmployeeRecord updated = db.calculateEmployeeTaxSalary(selectedId);
        if (updated != null) {
            JOptionPane.showMessageDialog(frame, "Tax and salary recalculated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            updateTable();
        } else {
            JOptionPane.showMessageDialog(frame, "Error in recalculating tax and salary.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
