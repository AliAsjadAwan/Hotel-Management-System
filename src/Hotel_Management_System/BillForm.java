package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BillForm extends JFrame {
    JLabel billLabel;
    private final Room selectedRoom;
    private final Guest guest;

    JLabel billLabelValue;
    private final int bookingId;
    private final int bookingDays;

    public BillForm(Room selectedRoom, Guest guest, int bookingId, int bookingDays) {
        this.selectedRoom = selectedRoom;
        this.guest = guest;

        this.bookingDays = bookingDays;
        this.bookingId = bookingId;

        setTitle("Booking Bill");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null); // Center the frame

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel roomLabel = new JLabel("Room Number:");
        JLabel roomValueLabel = new JLabel(String.valueOf(selectedRoom.getRoomNumber()));
        JLabel roomTypeLabel = new JLabel("Room Type:");
        JLabel roomTypeValueLabel = new JLabel(String.valueOf(selectedRoom.getRoomType()));
        JLabel roomPriceLabel = new JLabel("Room Rate:");
        JLabel roomPriceValueLabel = new JLabel(String.valueOf(selectedRoom.getRate()));
        JLabel roomAmenitiesLabel = new JLabel("Room Amenities:");
        JLabel roomAmenitiesValueLabel = new JLabel(String.valueOf(selectedRoom.getAmenities()));
        JLabel guestLabel = new JLabel("Guest Name:");
        JLabel guestValueLabel = new JLabel(guest.getCustomerName());
        JLabel guestIdLabel = new JLabel("Guest ID:");
        JLabel guestIdValueLabel = new JLabel(guest.getCustomerId());
        JLabel guestEmailLabel = new JLabel("Guest Email:");
        JLabel guestEmailValueLabel = new JLabel(guest.getCustomerEmail());

        JLabel bookLabel = new JLabel("Book Days:");
        JLabel bookDateValueLabel = new JLabel(String.valueOf(bookingDays));

        JLabel bookingIdLabel = new JLabel("Booking ID:");
        JLabel bookingIdValueLabel = new JLabel(String.valueOf(bookingId));

        billLabel = new JLabel("Total BILL");
        billLabelValue = new JLabel();

        panel.add(billLabel);
        panel.add(billLabelValue);
        panel.add(roomLabel);
        panel.add(roomValueLabel);
        panel.add(roomTypeLabel);
        panel.add(roomTypeValueLabel);
        panel.add(roomPriceLabel);
        panel.add(roomPriceValueLabel);
        panel.add(roomAmenitiesLabel);
        panel.add(roomAmenitiesValueLabel);
        panel.add(guestLabel);
        panel.add(guestValueLabel);
        panel.add(guestEmailLabel);
        panel.add(guestEmailValueLabel);
        panel.add(guestIdLabel);
        panel.add(guestIdValueLabel);
        panel.add(bookLabel);
        panel.add(bookDateValueLabel);
        panel.add(bookingIdLabel);
        panel.add(bookingIdValueLabel);

        add(panel);
        setVisible(true);
        displayBill();
        writeToFile("client.txt");
        writeToFile2("RoomDetails.txt");
        writeToFile3("Customer.txt");
    }

    private double calculateBill(int rentDays) {
        double totalBill = selectedRoom.getRate() * rentDays;
        if (selectedRoom.getRoomType() == RoomType.SINGLE) {
            return totalBill + (totalBill * 0.06);
        } else if (selectedRoom.getRoomType() == RoomType.DOUBLE) {
            return totalBill + (totalBill * 0.10);
        } else {
            return totalBill + (totalBill * 0.15);
        }
    }

    public void writeToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Concatenate all the necessary information with commas
            writer.println(STR."Room Number: \{selectedRoom.getRoomNumber()}, Room Type: \{selectedRoom.getRoomType()}, Room Rate: \{selectedRoom.getRate()}, Room Amenities: \{selectedRoom.getAmenities()}, Guest Name: \{guest.getCustomerName()}, Guest ID: \{guest.getCustomerId()}, Guest Email: \{guest.getCustomerEmail()}, Booking Days: \{bookingDays}, Booking ID: \{bookingId}, Total Bill: \{calculateBill(bookingDays)}"); // Assuming calculateBill() calculates the total bill
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayBill() {
        double billAmount = calculateBill(bookingDays);
        billLabelValue.setText(STR."$\{billAmount}"); // Set the value of BillLabelValue
    }

    public void writeToFile2(String filename) {
        // Mark the selected room as Occupied
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(STR."\{selectedRoom.getRoomNumber()} ")) {
                    lines.add(line.replace("AVAILABLE", "OCCUPIED"));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Rewrite the file with updated data
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename, true))) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeToFile3(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Concatenate all the necessary information with commas
            writer.println(
                    STR." Booking ID: \{bookingId}, Guest Name: \{guest.getCustomerName()}, Guest ID: \{guest.getCustomerId()}, Guest Email: \{guest.getCustomerEmail()}, Room Number: \{selectedRoom.getRoomNumber()}, Booking Days: \{bookingDays}, Total Bill: \{calculateBill(bookingDays)}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}