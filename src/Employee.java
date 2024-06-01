import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Employee {
    protected int id;
    protected String name;
    protected double hourlyWage;
    protected List<Feedback> feedbacks;

    public Employee(int id, String name, double hourlyWage) {
        this.id = id;
        this.name = name;
        this.hourlyWage = hourlyWage;
        this.feedbacks = new ArrayList<>();
    }

    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }

    public double calculateSalary(int hoursWorked) {
        double baseSalary = hoursWorked * hourlyWage;
        int positiveFeedbacks = 0;
        int negativeFeedbacks = 0;
        for (Feedback feedback : feedbacks) {
            if (feedback.isPositive()) {
                positiveFeedbacks++;
            } else {
                negativeFeedbacks++;
            }
        }
        double bonus = positiveFeedbacks * 0.02 * baseSalary;
        double penalty = negativeFeedbacks * 0.02 * baseSalary;
        return baseSalary + bonus - penalty;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static void addEmployee(List<Employee> employees, int employeeId, String name, double hourlyWage, String role) {
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                System.out.println("Employee with ID " + employeeId + " already exists.");
                return;
            }
        }

        switch (role) {
            case "Waiter":
                employees.add(new Waiter(employeeId, name, hourlyWage));
                break;
            case "Manager":
                employees.add(new Manager(employeeId, name, hourlyWage));
                break;
            case "Receptionist":
                employees.add(new Receptionist(employeeId, name, hourlyWage));
                break;
            default:
                System.out.println("Invalid role.");
                return;
        }
        System.out.println("Employee added successfully.");
        writeEmployeeDetailsToFile(employees, "employee_details.txt");
    }

    public static void removeEmployee(List<Employee> employees, int employeeId) {
        Employee employeeToRemove = null;
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                employeeToRemove = employee;
                break;
            }
        }
        if (employeeToRemove != null) {
            employees.remove(employeeToRemove);
            System.out.println("Employee removed successfully.");
            writeEmployeeDetailsToFile(employees, "employee_details.txt");
        } else {
            System.out.println("Employee with ID " + employeeId + " does not exist.");
        }
    }

    private static void writeEmployeeDetailsToFile(List<Employee> employees, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-12s | %-20s | %-15s%n", "Employee ID", "Name", "Role"));

            for (Employee employee : employees) {
                sb.append(String.format("%-12d | %-20s | %-15s%n", employee.getId(), employee.getName(), employee.getClass().getSimpleName()));
            }

            writer.print(sb.toString());
            System.out.println("Employee details written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<Employee> readEmployees(String fileName) {
        List<Employee> employees = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }

            while (scanner.hasNextLine()) {
                String[] employeeData = scanner.nextLine().split("\\s*\\|\\s*");
                int employeeId = Integer.parseInt(employeeData[0].trim());
                String name = employeeData[1].trim();
                String role = employeeData[2].trim();
                double hourlyWage = 0; // Since we don't have hourly wage in file, setting default

                switch (role) {
                    case "Waiter":
                        employees.add(new Waiter(employeeId, name, hourlyWage));
                        break;
                    case "Manager":
                        employees.add(new Manager(employeeId, name, hourlyWage));
                        break;
                    case "Receptionist":
                        employees.add(new Receptionist(employeeId, name, hourlyWage));
                        break;
                }
            }
            System.out.println("Employee details read from file successfully.");
        } catch (Exception e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return employees;
    }

    public static class Waiter extends Employee {
        private List<Order> currentOrders;

        public Waiter(int id, String name, double hourlyWage) {
            super(id, name, hourlyWage);
            this.currentOrders = new ArrayList<>();
        }

        public boolean canTakeOrder() {
            return currentOrders.size() < 3;
        }

        public void assignOrder(Order order) {
            currentOrders.add(order);
        }

        public void clearOrders() {
            currentOrders.clear();
        }
    }

    public static class Manager extends Employee {
        public Manager(int id, String name, double hourlyWage) {
            super(id, name, hourlyWage);
        }
    }

    public static class Receptionist extends Employee {
        public Receptionist(int id, String name, double hourlyWage) {
            super(id, name, hourlyWage);
        }
    }

    public static class Feedback {
        private boolean positive;

        public Feedback(boolean positive) {
            this.positive = positive;
        }

        public boolean isPositive() {
            return positive;
        }
    }

    public static class Order {
        // Placeholder class for Order
    }

    public static class EmployeeManagementApp {
        private JFrame frame;
        private JTextField idField;
        private JTextField nameField;
        private JTextField wageField;
        private JTextField removeIdField;
        private JComboBox<String> roleComboBox;
        private JTextArea displayArea;
        private List<Employee> employees;
        private DefaultTableModel tableModel;
        private JTable employeeTable;

        public EmployeeManagementApp() {
            employees = readEmployees("employee_details.txt");

            frame = new JFrame("Employee Management");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setBounds(450,100,500, 400);

            JPanel panel = new JPanel(new GridLayout(6, 2));

            panel.add(new JLabel("Employee ID:"));
            idField = new JTextField();
            panel.add(idField);

            panel.add(new JLabel("Name:"));
            nameField = new JTextField();
            panel.add(nameField);

            panel.add(new JLabel("Hourly Wage:"));
            wageField = new JTextField();
            panel.add(wageField);

            panel.add(new JLabel("Role:"));
            String[] roles = {"Waiter", "Manager", "Receptionist"};
            roleComboBox = new JComboBox<>(roles);
            panel.add(roleComboBox);

            JButton addButton = new JButton("Add Employee");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addEmployee();
                }
            });
            panel.add(addButton);

            JButton removeButton = new JButton("Remove Employee");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    showRemoveEmployeeDialog();
                }
            });
            panel.add(removeButton);

            JButton displayTableButton = new JButton("Display Employees in Table");
            displayTableButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displayEmployeesInTable();
                }
            });
            panel.add(displayTableButton);

            displayArea = new JTextArea();
            displayArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(displayArea);

            frame.add(panel, BorderLayout.NORTH);
            frame.add(scrollPane, BorderLayout.CENTER);
            frame.setVisible(true);
        }

        private void addEmployee() {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double wage = Double.parseDouble(wageField.getText());
                String role = (String) roleComboBox.getSelectedItem();

                Employee.addEmployee(employees, id, name, wage, role);
                updateTableModel();
                clearFields();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void showRemoveEmployeeDialog() {
            JFrame removeEmployeeFrame = new JFrame("Remove Employee");
            removeEmployeeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            removeEmployeeFrame.setSize(300, 150);

            JPanel panel = new JPanel();
            removeEmployeeFrame.add(panel);

            panel.setLayout(new GridLayout(3, 1));

            JLabel idLabel = new JLabel("Employee ID:");
            panel.add(idLabel);

            removeIdField = new JTextField();
            panel.add(removeIdField);

            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeEmployee(removeEmployeeFrame);
                }
            });
            panel.add(removeButton);

            removeEmployeeFrame.setVisible(true);
        }

        private void removeEmployee(JFrame removeEmployeeFrame) {
            try {
                int id = Integer.parseInt(removeIdField.getText());
                boolean found = false;

                for (Employee employee : employees) {
                    if (employee.getId() == id) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to remove employee with ID " + id + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (option == JOptionPane.YES_OPTION) {
                        Employee.removeEmployee(employees, id);
                        updateTableModel();
                        removeEmployeeFrame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(removeEmployeeFrame, "Employee with ID " + id + " does not exist. Please enter a valid ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(removeEmployeeFrame, "Invalid input. Please enter valid data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        public void displayEmployeesInTable() {
            JFrame tableFrame = new JFrame("Employee Table");
            tableFrame.setSize(600, 400);

            String[] columnNames = {"Employee ID", "Name", "Role"};
            tableModel = new DefaultTableModel(columnNames, 0);

            for (Employee employee : employees) {
                Object[] row = {employee.getId(), employee.getName(), employee.getClass().getSimpleName()};
                tableModel.addRow(row);
            }

            employeeTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(employeeTable);

            tableFrame.add(scrollPane);
            tableFrame.setVisible(true);
        }

        private void updateTableModel() {
            if (tableModel != null) {
                tableModel.setRowCount(0);
                for (Employee employee : employees) {
                    Object[] row = {employee.getId(), employee.getName(), employee.getClass().getSimpleName()};
                    tableModel.addRow(row);
                }
            }
        }

        private void clearFields() {
            idField.setText("");
            nameField.setText("");
            wageField.setText("");
            roleComboBox.setSelectedIndex(0);
            if (removeIdField != null) {
                removeIdField.setText("");
            }
        }

    }
}

class Waiter extends Employee {
    private int floor;
    private List<Order> currentOrders;

    public Waiter(int id, String name, double hourlyWage, int floor) {
        super(id, name, hourlyWage);
        this.floor = floor;
        this.currentOrders = new ArrayList<>();
    }

    public boolean canTakeOrder() {
        return currentOrders.size() < 3;
    }

    public void assignOrder(Order order) {
        currentOrders.add(order);
    }

    public void clearOrders() {
        currentOrders.clear();
    }

    public int getFloor() {
        return floor;
    }

    public  void addEmployee(List<Waiter> waiters, int employeeId, String name, double hourlyWage, int floor) {
        for (Waiter waiter : waiters) {
            if (waiter.getId() == employeeId) {
                System.out.println("Employee with ID " + employeeId + " already exists.");
                return;
            }
        }
        waiters.add(new Waiter(employeeId, name, hourlyWage));
        System.out.println("Employee added successfully.");
        writeEmployeeDetailsToFile(waiters, "waiter_details.txt");
    }

    private static void writeEmployeeDetailsToFile(List<Waiter> waiters, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {

            boolean isEmpty = new File(fileName).length() == 0;

            StringBuilder sb = new StringBuilder();

            if (isEmpty) {
                sb.append(String.format("%-12s | %-20s | %-15s | %-10s%n", "Employee ID", "Name", "Role", "Floor"));
            }

            for (Waiter waiter : waiters) {
                sb.append(String.format("%-12d | %-20s | %-15s | %-10d%n", waiter.getId(), waiter.getName(), waiter.getClass().getSimpleName()));
            }

            writer.print(sb.toString());
            System.out.println("Employee details written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public List<Waiter> readEmployeesFromFile(String fileName) {
        List<Waiter> waiters = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }

            while (scanner.hasNextLine()) {
                String[] employeeData = scanner.nextLine().split("\\s*\\|\\s*");
                int employeeId = Integer.parseInt(employeeData[0].trim());
                String name = employeeData[1].trim();
                int floor = Integer.parseInt(employeeData[3].trim());
                double hourlyWage = 0; // Since we don't have hourly wage in file, setting default
                waiters.add(new Waiter(employeeId, name, hourlyWage));
            }
            System.out.println("Employee details read from file successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return waiters;
    }
}


//import java.util.*;
//      import java.io.*;

 class Manager extends Employee {
    public Manager(int id, String name, double hourlyWage) {
        super(id, name, hourlyWage);
    }

    public void addEmployee(List<Manager> managers, int employeeId, String name, double hourlyWage) {
        for (Manager manager : managers) {
            if (manager.getId() == employeeId) {
                System.out.println("Employee with ID " + employeeId + " already exists.");
                return;
            }
        }
        managers.add(new Manager(employeeId, name, hourlyWage));
        System.out.println("Employee added successfully.");
        writeEmployeeDetailsToFile(managers, "manager_details.txt");
    }

    private static void writeEmployeeDetailsToFile(List<Manager> managers, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            boolean isEmpty = new File(fileName).length() == 0;
            StringBuilder sb = new StringBuilder();

            if (isEmpty) {
                sb.append(String.format("%-12s | %-20s | %-15s%n", "Employee ID", "Name", "Role"));
            }

            for (Manager manager : managers) {
                sb.append(String.format("%-12d | %-20s | %-15s%n", manager.getId(), manager.getName(), manager.getClass().getSimpleName()));
            }

            writer.print(sb.toString());
            System.out.println("Employee details written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public List<Manager> readEmployeesFromFile(String fileName) {
        List<Manager> managers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (scanner.hasNextLine()) {
                scanner.nextLine(); // Skip header
            }

            while (scanner.hasNextLine()) {
                String[] employeeData = scanner.nextLine().split("\\s*\\|\\s*");
                int employeeId = Integer.parseInt(employeeData[0].trim());
                String name = employeeData[1].trim();
                double hourlyWage = 0; // Since we don't have hourly wage in file, setting default
                managers.add(new Manager(employeeId, name, hourlyWage));
            }
            System.out.println("Employee details read from file successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return managers;
    }
}

 class Recipcinoist  extends JFrame implements ActionListener {
     private final JButton Logout;
     JButton ViewRoom;
    JButton Booking;
    JButton CheckIn ;
    JButton Checkout;
    JButton UpdateRoom;
    JButton UpdateBooking;
    JButton ViewWaiter;
    JButton Order;
    public Recipcinoist() {
        this.setTitle("Receptionist");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(280,5,1238,820);
        panel.setBackground(new Color(3,45,48));
        add(panel);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(5,5,270,820);
        panel1.setBackground(new Color(3,45,48));
        add(panel1);


//            this.setSize(500, 500);
//            this.getContentPane().setBackground(Color.lightGray);
        this.setResizable(false);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);


        ViewRoom = new JButton("View Room");
        ViewRoom.setBounds(30,30,200,30);
        ViewRoom.setBackground(Color.BLACK);
        ViewRoom.setForeground(Color.WHITE);
        panel1.add(ViewRoom);

        Booking = new JButton("Booking");
        Booking.setBounds(30,70,200,30);
        Booking.setBackground(Color.BLACK);
        Booking.setForeground(Color.WHITE);
        panel1.add(Booking);

//        CheckIn = new JButton("Check In");
//        CheckIn.setBounds(30,110,200,30);
//        CheckIn.setBackground(Color.BLACK);
//        CheckIn.setForeground(Color.WHITE);
//        panel1.add(CheckIn);

        Checkout = new JButton("Checkout");
        Checkout.setBounds(30,110,200,30);
        Checkout.setBackground(Color.BLACK);
        Checkout.setForeground(Color.WHITE);
        panel1.add(Checkout);

        UpdateRoom = new JButton("Update Room");
        UpdateRoom.setBounds(30,150,200,30);
        UpdateRoom.setBackground(Color.BLACK);
        UpdateRoom.setForeground(Color.WHITE);
        panel1.add(UpdateRoom);

        UpdateBooking = new JButton("Update Booking");
        UpdateBooking.setBounds(30,190,200,30);
        UpdateBooking.setBackground(Color.BLACK);
        UpdateBooking.setForeground(Color.WHITE);
        panel1.add(UpdateBooking);

        ViewWaiter = new JButton("View Waiter");
        ViewWaiter.setBounds(30,230,200,30);
        ViewWaiter.setBackground(Color.BLACK);
        ViewWaiter.setForeground(Color.WHITE);
        panel1.add(ViewWaiter);

        Order = new JButton("Order");
        Order.setBounds(30,270,200,30);
        Order.setBackground(Color.BLACK);
        Order.setForeground(Color.WHITE);
        panel1.add(Order);

        ImageIcon logoIcon = new ImageIcon("C:\\Users\\SHAHBAZ TRADERS\\IdeaProjects\\Project1\\src\\Logo.jpg");
        JLabel logoLabel = new JLabel(logoIcon);
        int logoWidth = logoIcon.getIconWidth();
        int logoHeight = logoIcon.getIconHeight();
        logoLabel.setBounds(6, 485, logoWidth, logoHeight);
        panel1.add(logoLabel);

        Logout = new JButton("Logout");
        Logout.setBounds(30, 310, 200, 30);
        Logout.setBackground(Color.BLACK);
        Logout.setForeground(Color.WHITE);
        panel1.add(Logout);

        // Set focusable to false for each button
        ViewRoom.setFocusable(false);
        Booking.setFocusable(false);
        //CheckIn.setFocusable(false);
        Checkout.setFocusable(false);
        UpdateRoom.setFocusable(false);
        UpdateBooking.setFocusable(false);
        ViewWaiter.setFocusable(false);
        Order.setFocusable(false);
        Logout.setFocusable(false);

        // Set bounds for each button
//            ViewRoom.setBounds(100, 160, 200, 30);
//            Booking.setBounds(100, 210, 200, 30);
//            CheckIn.setBounds(100, 260, 200, 30);
//            Checkout.setBounds(100, 310, 200, 30);
//            UpdateRoom.setBounds(100, 360, 200, 30);
//            UpdateBooking.setBounds(100, 410, 200, 30);
//            ViewWaiter.setBounds(100, 460, 200, 30);
//            Order.setBounds(100, 510, 200, 30);
//
//            // Add buttons to the frame
//            add(ViewRoom);
//            add(Booking);
//            add(CheckIn);
//            add(Checkout);
//            add(UpdateRoom);
//            add(UpdateBooking);
//            add(ViewWaiter);
//            add(Order);

        // Add action listeners
        ViewRoom.addActionListener(this);
        Booking.addActionListener(this);
        //CheckIn.addActionListener(this);
        Checkout.addActionListener(this);
        UpdateRoom.addActionListener(this);
        UpdateBooking.addActionListener(this);
        ViewWaiter.addActionListener(this);
        Order.addActionListener(this);
        Logout.addActionListener(this);
        setVisible(true);
    }

//        ViewRoom.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Room.TableGui(); // Create an instance of Room
//            }
//        });


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == ViewRoom) {
            HotelManagementSystem.showAllRoomDetails();
        }
        else if (source == Booking) {
            HotelManagementSystem hms  =new HotelManagementSystem();
            hms.BookingRoomGui();
        }  else if (source == Checkout) {
            HotelManagementSystem.checkOut();
        } else if (source == UpdateRoom) {
            // Handle UpdateRoom button click
        } else if (source == UpdateBooking) {
            // Handle UpdateBooking button click
        } else if (source == ViewWaiter) {
            // Handle ViewWaiter button click
        } else if (source == Order) {
           new Order();
        } else if (source==Logout) {
            dispose();
            new ChooseForm();

        }
    }

     public static void main(String[] args) {
         new Recipcinoist();
     }


}