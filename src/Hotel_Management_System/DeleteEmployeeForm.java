package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteEmployeeForm extends JFrame {
    private final JTextField idField;
    private final List<Employee> employees;

    public DeleteEmployeeForm() {
        setTitle("Delete Employee");
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
        formPanel.setLayout(new GridLayout(3, 2, 10, 10));
        formPanel.setBounds(100, 100, 300, 100);
        formPanel.setOpaque(false);

        // Add components
        JLabel idLabel = new JLabel("Employee ID:");
        idLabel.setForeground(Color.WHITE);
        formPanel.add(idLabel);

        idField = new JTextField();
        formPanel.add(idField);

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(_ -> deleteEmployee());
        formPanel.add(deleteButton);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(_ -> {
            dispose();
            new Admin();
        });
        formPanel.add(backButton);

        mainPanel.add(formPanel);
        add(mainPanel);
        setVisible(true);
    }

    private void deleteEmployee() {
        try {
            int id = Integer.parseInt(idField.getText());
            boolean found = false;

            for (Employee employee : employees) {
                if (employee.getId() == id) {
                    found = true;
                    break;
                }
            }

            if (found) {
                int option = JOptionPane.showConfirmDialog(this,
                        STR."Are you sure you want to remove employee with ID \{id}?",
                    "Confirmation", 
                    JOptionPane.YES_NO_OPTION);
                
                if (option == JOptionPane.YES_OPTION) {
                    Employee.removeEmployee(employees, id);
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        STR."Employee with ID \{id} does not exist.",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a valid employee ID.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 