package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddEmployeeForm extends JFrame {
    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField wageField;
    private final JComboBox<String> roleComboBox;
    private final List<Employee> employees;

    public AddEmployeeForm() {
        setTitle("Add Employee");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 100, 500, 400);

        // Initialize employees list
        employees = Employee.readEmployees("employee_details.txt");

        // Create main panel with background
        JPanel mainPanel = new JPanel(null);
        mainPanel.setBounds(0, 0, 500, 400);
        ImageUtils.setBackgroundImage(mainPanel, "Visuals/background.jpg");

        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBounds(100, 100, 300, 200);
        formPanel.setOpaque(false);

        // Add components
        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setForeground(Color.WHITE);
        formPanel.add(idLabel);

        // Create non-editable ID field with next available ID
        idField = new JTextField(String.format("%05d", getNextEmployeeId()));
        idField.setEditable(false);
        idField.setBackground(new Color(200, 200, 200));
        formPanel.add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        formPanel.add(nameLabel);

        nameField = new JTextField();
        formPanel.add(nameField);

        JLabel wageLabel = new JLabel("Hourly Wage:");
        wageLabel.setForeground(Color.WHITE);
        formPanel.add(wageLabel);

        wageField = new JTextField();
        formPanel.add(wageField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setForeground(Color.WHITE);
        formPanel.add(roleLabel);

        String[] roles = {"Waiter", "Manager", "Receptionist"};
        roleComboBox = new JComboBox<>(roles);
        formPanel.add(roleComboBox);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addEmployee());
        formPanel.add(addButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
            new Admin();
        });
        formPanel.add(backButton);

        mainPanel.add(formPanel);
        add(mainPanel);
        setVisible(true);
    }

    private int getNextEmployeeId() {
        int maxId = 0;
        for (Employee employee : employees) {
            if (employee.getId() > maxId) {
                maxId = employee.getId();
            }
        }
        return maxId + 1;
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            double wage = Double.parseDouble(wageField.getText());
            String role = (String) roleComboBox.getSelectedItem();

            // Add the employee
            Employee.addEmployee(employees, id, name, wage, role);
            JOptionPane.showMessageDialog(this,
                "Employee added successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid numeric values for wage.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 