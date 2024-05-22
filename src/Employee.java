import java.io.*;
import java.util.*;

public class Employee {
    private int employeeId;
    private String name;
    private String role;

    public Employee(int employeeId, String name, String role) {
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static void addEmployee(List<Employee> employees, int employeeId, String name, String role) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId() == employeeId) {
                System.out.println("Employee with ID " + employeeId + " already exists.");
                return;
            }
        }
        employees.add(new Employee(employeeId, name, role));
        System.out.println("Employee added successfully.");
        writeEmployeeDetailsToFile(employees, "employee_details.txt");
    }

    private static void writeEmployeeDetailsToFile(List<Employee> employees, String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {

            boolean isEmpty = new File(fileName).length() == 0;

            StringBuilder sb = new StringBuilder();

            if (isEmpty) {
                sb.append(String.format("%-12s | %-20s | %-15s","Employee ID","Name","Role"));
            }

            for (Employee employee : employees) {
                sb.append(String.format("%-12d | %-20s | %-15s%n", employee.getEmployeeId(), employee.getName(), employee.getRole()));
            }

            writer.print(sb.toString());
            System.out.println("Employee details written to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<Employee> readEmployeesFromFile(String fileName) {
        List<Employee> employees = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] employeeData = scanner.nextLine().split("\\s*\\|\\s*");
                int employeeId = Integer.parseInt(employeeData[0].trim());
                String name = employeeData[1].trim();
                String role = employeeData[2].trim();
                employees.add(new Employee(employeeId, name, role));
            }
            System.out.println("Employee details read from file successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return employees;
    }
}
