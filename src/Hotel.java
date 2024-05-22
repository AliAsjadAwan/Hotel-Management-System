package HMS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.util.*;

import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Hotel extends JFrame {

    private List<Room> rooms;
    private List<Booking> bookings;
    private List<Guest> guests ;



    //    private static final int HEADER_SIZE = 100;
//
    public Hotel() {
        rooms = new ArrayList<>();
        bookings=new ArrayList<>();
        guests=new ArrayList<>();

        this.setTitle("");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        JButton viewRoom = new JButton("View Rooms");
        JButton bookButton=new JButton("Book A Room");



        viewRoom.setBounds(100, 30, 200, 30);
        bookButton.setBounds(100,70,200,30);
        // Add buttons to frame
        add(viewRoom);
        add(bookButton);


        viewRoom.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                showAllRoomDetails();
            }

        });

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookingRoomGui();
            }
        });
        this.setVisible(true);

    }


    public boolean addRoom(Room room) {
//method1
//        for (Room existingRoom : rooms) {
//            if (existingRoom.getRoomNumber() == room.getRoomNumber()) {
//                System.out.println("Room with number " + room.getRoomNumber() + " already exists.");
//                return false;
//            }
//        }
//        rooms.add(room);
//        return true;
//
//        }

//method 2
        if (rooms.contains(room)){
            System.out.println("Room with number " + room.getRoomNumber() + " already exists.");
            return false;
        }
        rooms.add(room);
        return true;
    }
    //method3
//        if (getRoomByNumber(room.getRoomNumber()) != null) {
//            throw new IllegalArgumentException("Room number already exists.");
//        }
//        rooms.add(room);
//    }
    public Room getRoomByNumber(int roomNumber) {

        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }


    public void addBooking(int bookingId, Guest guest, BookingStatus bookingStatus, String paymentStatus, Room room, Date bookingDate, Date checkInDate, Date checkOutDate) {
        bookingType bookingtype;
        if (room.getRoomStatus() == RoomStatus.AVAILABLE) {
            if (bookingDate.equals(checkInDate)) {
                bookingtype = bookingType.ATTHESPOT;
            } else {
                bookingtype = bookingType.ADVANCE;
            }
            room.setRoomStatus(RoomStatus.OCCUPIED);
            bookings.add(new Booking(bookingId, guest, room, bookingStatus, paymentStatus, bookingDate, checkInDate, checkOutDate, bookingtype)); // Ek naya booking add karna hai.
        } else {
            System.out.println("Room is not available for booking.");
        }
    }


    public List<Room> getAvailableRooms(RoomType roomType, Date CheckInDate, Date checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType() == roomType && room.isAvailable(CheckInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }


    public void showRoomDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-10s | %-12s | %-8s | %-30s%n", "Room No", "Type", "Status", "Rate", "Amenities"));
        sb.append("--------------------------------------------------------------------------------------------\n");

        for (Room room : rooms) {
            sb.append(room.getRoomDetails());
        }

        System.out.println(sb.toString());
    }
    public void showAllRoomDetails() {
        String[] columnNames = {"Room No", "Type", "Status", "Rate", "Amenities"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Room room : rooms) {
            List<String> amenities = room.getAmenities();
            String amenitiesStr = String.join(", ", amenities);
            Object[] row = {room.getRoomNumber(), room.getRoomType(), room.getRoomStatus(), room.getRate(), amenitiesStr};
            tableModel.addRow(row);
        }
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 500, 200);

        JFrame detailsFrame = new JFrame("Room Details");
        detailsFrame.setSize(600, 400);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.add(scrollPane);
        detailsFrame.setVisible(true);



    }

    public  void BookingRoomGui(){


        JFrame bookingFrame = new JFrame("Add Booking");
        bookingFrame.setSize(400, 300);
        bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookingFrame.setLayout(new GridLayout(7, 2));

        // Components for guest details
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        // Components for booking details
        JLabel bookingIdLabel = new JLabel("Booking ID:");
        JTextField bookingIdField = new JTextField(10);
        JLabel bookingDateLabel = new JLabel("Booking Date:");
        JTextField bookingDateField = new JTextField(10);
        JLabel checkInDateLabel = new JLabel("Check-in Date:");
        JTextField checkInDateField = new JTextField(10);
        JLabel checkOutDateLabel = new JLabel("Check-out Date:");
        JTextField checkOutDateField = new JTextField(10);

        // Components for room details
        JLabel roomStatusLabel = new JLabel("Room Status:");
        JComboBox<RoomStatus> roomStatusComboBox = new JComboBox<>(RoomStatus.values());
        JLabel roomLabel=new JLabel("ROOM: ");
        JComboBox<Room> roomComboBox = new JComboBox<>();




        // Add components to the form
        bookingFrame.add(nameLabel);
        bookingFrame.add(nameField);
        bookingFrame.add(emailLabel);
        bookingFrame.add(emailField);
        bookingFrame.add(bookingIdLabel);
        bookingFrame.add(bookingIdField);
        bookingFrame.add(bookingDateLabel);
        bookingFrame.add(bookingDateField);
        bookingFrame.add(checkInDateLabel);
        bookingFrame.add(checkInDateField);
        bookingFrame.add(checkOutDateLabel);
        bookingFrame.add(checkOutDateField);
        bookingFrame.add(roomStatusLabel);
        bookingFrame.add(roomStatusComboBox);
        bookingFrame.add(roomLabel);
        bookingFrame.add(roomComboBox);
        // Make the form visible
        bookingFrame.setVisible(true);

    }

//    private static boolean isHeaderWritten(String filename) {
////        File file = new File(filename);
////        return file.length() > HEADER_SIZE;
////    }

    public void writeRoomsToFile(String filePath) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));

            writer.write(String.format("%-10s | %-10s | %-12s | %-8s | %-30s%n", "Room No", "Type", "Status", "Rate", "Amenities"));
            writer.write("--------------------------------------------------------------------------------------------\n");

            for (Room room : rooms) {
                writer.write(room.getRoomDetails());
            }

            System.out.println("Rooms successfully written to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.err.println("An error occurred while closing the writer: " + e.getMessage());
            }
        }
    }
//    }