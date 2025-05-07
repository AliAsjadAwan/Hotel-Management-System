package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Admin extends JFrame implements ActionListener {
    JButton Logout;
    JButton AddRoom;
    JButton ViewButton;
    JButton AddEmployee;
    JButton DeleteEmployee;
    JButton ViewEmployee;
    JButton CustomerDetals;

    public Admin() {
        // Setting up the main frame
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Manager");

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(280, 5, 1238, 820);
        panel.setBackground(new Color(3, 45, 48));
        add(panel);

        // Side panel
        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(5, 5, 270, 820);
        panel1.setBackground(new Color(3, 45, 48));
        add(panel1);

        // Set layout manager to null for absolute positioning
        this.setLayout(null);

        // Add buttons to the side panel
        AddRoom = new JButton("Add A Room");
        AddRoom.setBounds(30, 30, 200, 30);
        AddRoom.setBackground(Color.BLACK);
        AddRoom.setForeground(Color.WHITE);
        panel1.add(AddRoom);

        ViewButton = new JButton("View A Room");
        ViewButton.setBounds(30, 70, 200, 30);
        ViewButton.setBackground(Color.BLACK);
        ViewButton.setForeground(Color.WHITE);
        panel1.add(ViewButton);

        // Employee Management Buttons
        AddEmployee = new JButton("Add Employee");
        AddEmployee.setBounds(30, 110, 200, 30);
        AddEmployee.setBackground(Color.BLACK);
        AddEmployee.setForeground(Color.WHITE);
        panel1.add(AddEmployee);

        DeleteEmployee = new JButton("Delete Employee");
        DeleteEmployee.setBounds(30, 150, 200, 30);
        DeleteEmployee.setBackground(Color.BLACK);
        DeleteEmployee.setForeground(Color.WHITE);
        panel1.add(DeleteEmployee);

        ViewEmployee = new JButton("View Employees");
        ViewEmployee.setBounds(30, 190, 200, 30);
        ViewEmployee.setBackground(Color.BLACK);
        ViewEmployee.setForeground(Color.WHITE);
        panel1.add(ViewEmployee);

        CustomerDetals = new JButton("View Customer Details");
        CustomerDetals.setBounds(30, 230, 200, 30);
        CustomerDetals.setBackground(Color.BLACK);
        CustomerDetals.setForeground(Color.WHITE);
        panel1.add(CustomerDetals);

        Logout = new JButton("Logout");
        Logout.setBounds(30, 270, 200, 30);
        Logout.setBackground(Color.BLACK);
        Logout.setForeground(Color.WHITE);
        panel1.add(Logout);

        // Add logo to the side panel
        ImageIcon logoIcon = ImageUtils.loadImageIcon("Logo.jpg");
        if (logoIcon != null) {
            JLabel logoLabel = new JLabel(logoIcon);
            int logoWidth = logoIcon.getIconWidth();
            int logoHeight = logoIcon.getIconHeight();
            logoLabel.setBounds(6, 455, logoWidth, logoHeight);
            panel1.add(logoLabel);
        }

        // Disable focusable property for buttons
        AddRoom.setFocusable(false);
        ViewButton.setFocusable(false);
        AddEmployee.setFocusable(false);
        DeleteEmployee.setFocusable(false);
        ViewEmployee.setFocusable(false);
        CustomerDetals.setFocusable(false);
        Logout.setFocusable(false);

        // Register action listeners
        AddRoom.addActionListener(this);
        ViewButton.addActionListener(this);
        AddEmployee.addActionListener(this);
        DeleteEmployee.addActionListener(this);
        ViewEmployee.addActionListener(this);
        CustomerDetals.addActionListener(this);
        Logout.addActionListener(this);

        // Make the frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == AddRoom) {
            new Room();
        } else if (source == ViewButton) {
            new ViewRoomForm();
        } else if (source == AddEmployee) {
            new AddEmployeeForm();
        } else if (source == DeleteEmployee) {
            new DeleteEmployeeForm();
        } else if (source == ViewEmployee) {
            new ViewEmployeeForm();
        } else if (source == CustomerDetals) {
            new CustomerTable();
        } else if (source == Logout) {
            dispose();
            new ChooseForm();
        }
    }

    public static void main(String[] args) {
        new Admin();
    }
}

