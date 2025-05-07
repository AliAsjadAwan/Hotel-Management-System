package Hotel_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewEmployeeForm extends JFrame {
    private final List<Employee> employees;

    public ViewEmployeeForm() {
        setTitle("View Employees");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 100, 800, 600);

        // Initialize employees list
        employees = Employee.readEmployees("employee_details.txt");

        // Create main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBounds(0, 0, 800, 600);
        ImageUtils.setBackgroundImage(mainPanel, "Visuals/background.jpg");

        // Create table model
        String[] columnNames = {"ID", "Name", "Role"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate table with employee data
        for (Employee employee : employees) {
            Object[] row = {
                employee.getId(),
                employee.getName(),
                employee.getClass().getSimpleName()
            };
            tableModel.addRow(row);
        }

        // Create table
        JTable employeeTable = new JTable(tableModel);
        employeeTable.setOpaque(false);
        employeeTable.setForeground(Color.WHITE);
        employeeTable.setBackground(new Color(0, 0, 0, 150));
        employeeTable.setFillsViewportHeight(true);

        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create back button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
        });
        buttonPanel.add(backButton);

        // Add components to main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
} 