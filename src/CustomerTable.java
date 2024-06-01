
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerTable extends JFrame {
    public CustomerTable() {
        setTitle("Customer Table");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 400);

        String[] columnNames = {"Booking ID", "Guest Name", "Guest ID", "Guest Email", "Room Number", "Booking Days", "Total Bill"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        readFromFile("customer.txt", tableModel);
        setVisible(true);
    }

    public void readFromFile(String filename, DefaultTableModel tableModel) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                int bookingId = Integer.parseInt(fields[0].split(": ")[1]);
                String guestName = fields[1].split(": ")[1];
                String guestId = fields[2].split(": ")[1];
                String guestEmail = fields[3].split(": ")[1];
                int roomNumber = Integer.parseInt(fields[4].split(": ")[1]);
                int bookingDays = Integer.parseInt(fields[5].split(": ")[1]);
                double totalBill = Double.parseDouble(fields[6].split(": ")[1]);

                tableModel.addRow(new Object[]{bookingId, guestName, guestId, guestEmail, roomNumber, bookingDays, totalBill});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}