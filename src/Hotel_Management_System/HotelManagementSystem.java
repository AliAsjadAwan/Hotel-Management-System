package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class HotelManagementSystem extends JFrame {

    private static final List<Room> rooms = new ArrayList<>();
    private static final List<Booking> bookings = new ArrayList<>();
    private final List<Guest> guests = new ArrayList<>();
    private JTextField checkInDateField;
    private JTextField checkOutDateField;
    private JTextField bookingDateField;
    private JTextField nameField;
    private JTextField emailField;
    private int maxBookingId = 0;
    private JTextField bookingIdField;
    private JRadioButton Single;
    private JRadioButton Double;
    private JRadioButton Suite;
    private JComboBox<String> roomComboBox;

    private static final String BOOKINGS_FILE = "bookings.txt";
    private static final String BOOKINGS_HEADER = "Booking ID | Guest Name | Guest ID | Guest Email | Room Number | Booking Days | Total Bill";

    public HotelManagementSystem() {
        // Clear existing rooms and bookings before reading from files
        rooms.clear();
        bookings.clear();
        
        // Read rooms from file first
        rooms.addAll(Room.readRoomsFromFile("roomDetails.txt"));
        
        // Read bookings from file
        readBookingsFromFile();
    }

    public static List<Room> getRooms() {
        return new ArrayList<>(rooms); // Return a copy to prevent modification
    }

    public static void addRoom(Room room) {
        if (rooms.contains(room)) {
            System.out.println("Room with number " + room.getRoomNumber() + " already exists.");
            return;
        }
        rooms.add(room);
        // Write the new room to file
        room.writeRoomToFile(room);
    }

    public static Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }


    public List<Room> getAvailableRooms(RoomType roomType) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType() == roomType && room.getRoomStatus() == RoomStatus.AVAILABLE) {
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

        System.out.println(sb);
    }

    public static void showAllRoomDetails() {
        String[] columnNames = {"Room No", "Type", "Status", "Rate", "Amenities"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        // Use a Set to track unique room numbers
        Set<Integer> processedRooms = new HashSet<>();
        
        for (Room room : rooms) {
            // Only add each room number once
            if (processedRooms.add(room.getRoomNumber())) {
                List<String> amenities = room.getAmenities();
                String amenitiesStr = String.join(", ", amenities);
                Object[] row = {room.getRoomNumber(), room.getRoomType(), room.getRoomStatus(), room.getRate(), amenitiesStr};
                tableModel.addRow(row);
            }
        }
        
        JTable table = new JTable(tableModel);
        table.setOpaque(false);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setBackground(new Color(0, 0, 0, 0));
        table.setForeground(Color.WHITE);
        table.setGridColor(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 150, 500, 200);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JPanel backgroundPanel = getjPanel();
        backgroundPanel.add(scrollPane);

        JFrame detailsFrame = new JFrame("Room Details");
        detailsFrame.setBounds(400, 100, 600, 400);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setContentPane(backgroundPanel);
        detailsFrame.setVisible(true);
    }

    private static JPanel getjPanel() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageUtils.loadImage("Login1.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        backgroundPanel.setBounds(0, 0, 600, 400);
        backgroundPanel.setLayout(null);
        return backgroundPanel;
    }


    public void BookingRoomGui() {
        JFrame bookingFrame = new JFrame("Add Booking");
        bookingFrame.setBounds(400,100,600, 600);
        bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookingFrame.setLayout(null);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageUtils.loadImage("Booking1.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        contentPane.setLayout(null);
        bookingFrame.setContentPane(contentPane);

        // Add existing components
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 150, 30);
        nameLabel.setForeground(Color.WHITE);
        nameField = new JTextField();
        nameField.setBounds(300,50,180,30);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 150, 30);
        emailLabel.setForeground(Color.WHITE);
        emailField = new JTextField(20);
        emailField.setBounds(300,100,180,30);

        JLabel bookingIdLabel = new JLabel("Booking ID:");
        bookingIdLabel.setBounds(50,150,150,30);
        bookingIdLabel.setForeground(Color.WHITE);
        bookingIdField = new JTextField();
        bookingIdField.setBounds(300,150,180,30);
        bookingIdField.setEditable(false);

        JLabel bookingDateLabel = new JLabel("Days to Stay:");
        bookingDateLabel.setBounds(50, 200, 150, 30);
        bookingDateLabel.setForeground(Color.WHITE);
        bookingDateField = new JTextField();
        bookingDateField.setBounds(300, 200, 180, 30);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setBounds(50, 250, 150, 30);
        roomTypeLabel.setForeground(Color.WHITE);

        // Initialize radio buttons
        Single = new JRadioButton("Single");
        Single.setBounds(200, 250, 100, 30);
        Single.setForeground(Color.WHITE);
        Single.setOpaque(false);
        
        Double = new JRadioButton("Double");
        Double.setBounds(300, 250, 100, 30);
        Double.setForeground(Color.WHITE);
        Double.setOpaque(false);
        
        Suite = new JRadioButton("Suite");
        Suite.setBounds(400, 250, 100, 30);
        Suite.setForeground(Color.WHITE);
        Suite.setOpaque(false);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(Single);
        buttonGroup.add(Double);
        buttonGroup.add(Suite);

        JLabel roomLabel = new JLabel("Room:");
        roomLabel.setBounds(50, 300, 150, 30);
        roomLabel.setForeground(Color.WHITE);
        roomComboBox = new JComboBox<>();
        roomComboBox.setBounds(300, 300, 180, 30);

        JButton BookRoomButton = new JButton("Book");
        BookRoomButton.setBounds(170, 350, 150, 40);

        // Add action listener for radio buttons
        ActionListener roomTypeListener = e -> {
            JRadioButton source = (JRadioButton) e.getSource();
            if (source.isSelected()) {
                if (source == Single) {
                    displayRoomsByType(RoomType.SINGLE);
                } else if (source == Double) {
                    displayRoomsByType(RoomType.DOUBLE);
                } else if (source == Suite) {
                    displayRoomsByType(RoomType.SUITE);
                }
            }
        };

        Single.addActionListener(roomTypeListener);
        Double.addActionListener(roomTypeListener);
        Suite.addActionListener(roomTypeListener);

        BookRoomButton.addActionListener(e -> {
            try {
                AllocateRoomGui();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Add all components to the frame
        bookingFrame.add(nameLabel);
        bookingFrame.add(nameField);
        bookingFrame.add(emailLabel);
        bookingFrame.add(emailField);
        bookingFrame.add(bookingIdLabel);
        bookingFrame.add(bookingIdField);
        bookingFrame.add(bookingDateLabel);
        bookingFrame.add(bookingDateField);
        bookingFrame.add(roomTypeLabel);
        bookingFrame.add(Single);
        bookingFrame.add(Double);
        bookingFrame.add(Suite);
        bookingFrame.add(roomLabel);
        bookingFrame.add(roomComboBox);
        bookingFrame.add(BookRoomButton);

        bookingFrame.setVisible(true);
        generateBookingId();
    }

    public void allocateRoom(Room room, Guest guest, int bookingId, int rentDays) {
        if (room.getRoomStatus() == RoomStatus.AVAILABLE) {
            room.setRoomStatus(RoomStatus.OCCUPIED);
            bookings.add(new Booking(bookingId, guest, room, rentDays));
            writeBookingsToFile();
        } else {
            JOptionPane.showMessageDialog(null, "Room is not available");
        }
    }

    public static void writeBookingsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKINGS_FILE))) {
            writer.write(BOOKINGS_HEADER);
            writer.newLine();
            writer.write("--------------------------------------------------------------------------------------------");
            writer.newLine();

            for (Booking booking : bookings) {
                writer.write(String.format("%-10d | %-20s | %-10s | %-30s | %-12d | %-12d | %-10.2f",
                    booking.getBookingId(),
                    booking.getGuest().getCustomerName(),
                    booking.getGuest().getCustomerId(),
                    booking.getGuest().getCustomerEmail(),
                    booking.getRoom().getRoomNumber(),
                    booking.getBookingDays(),
                    booking.getRoom().getRate() * booking.getBookingDays()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing bookings to file: " + e.getMessage());
        }
    }

    public static void readBookingsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKINGS_FILE))) {
            // Skip header lines
            reader.readLine();
            reader.readLine();
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 7) continue;
                
                try {
                    int bookingId = Integer.parseInt(parts[0].trim());
                    String guestName = parts[1].trim();
                    String guestId = parts[2].trim();
                    String guestEmail = parts[3].trim();
                    int roomNumber = Integer.parseInt(parts[4].trim());
                    int bookingDays = Integer.parseInt(parts[5].trim());
                    
                    Room room = getRoomByNumber(roomNumber);
                    if (room != null) {
                        if (room.getRoomStatus() == RoomStatus.OCCUPIED) {
                            Guest guest = new Guest(guestName, guestEmail, guestId);
                            bookings.add(new Booking(bookingId, guest, room, bookingDays));
                        }
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing booking data: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading bookings from file: " + e.getMessage());
        }
    }

    public void displayRoomsByType(RoomType roomType) {
        // Clear existing items in the roomComboBox
        roomComboBox.removeAllItems();
        
        // Get available rooms of the selected type
        List<Room> availableRooms = getAvailableRooms(roomType);
        
        // If no rooms available, add a message and disable the combo box
        if (availableRooms.isEmpty()) {
            roomComboBox.addItem("No rooms available");
            roomComboBox.setEnabled(false);
            return;
        }
        
        // Enable the combo box and add available rooms
        roomComboBox.setEnabled(true);
        for (Room room : availableRooms) {
            roomComboBox.addItem(String.valueOf(room.getRoomNumber()));
        }
    }


    public static void writeRoomsToFileByArrayList(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write header
            writer.write(String.format("%-10s | %-10s | %-12s | %-8s | %-30s%n", "Room No", "Type", "Status", "Rate", "Amenities"));
            writer.write("--------------------------------------------------------------------------------------------\n");

            // Write all rooms
            for (Room room : rooms) {
                writer.write(room.getRoomDetails());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing rooms to file: " + e.getMessage());
        }
    }

    public void  generateBookingId(){
        int generatedId = (int) (Math.random() * 90000) + 100;
        if (generatedId <= maxBookingId) {
            generatedId = maxBookingId + 1;
        }
        maxBookingId = generatedId;
        bookingIdField.setText(String.valueOf(generatedId));
    }
    public static void checkOut() {
        JFrame checkoutFrame = new JFrame("Checkout");
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.setBounds(450, 100, 500, 500);

        // Create main panel with background
        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageUtils.loadImage("Checkout.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        mainPanel.setBounds(0, 0, 500, 500);

        // Create form panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(50, 50, 400, 400);
        formPanel.setOpaque(false);

        // Add components to form panel
        JLabel roomNoLabel = new JLabel("Room No:");
        roomNoLabel.setBounds(20, 20, 80, 25);
        roomNoLabel.setForeground(Color.WHITE);
        formPanel.add(roomNoLabel);

        // Create dropdown for occupied rooms
        JComboBox<String> roomComboBox = new JComboBox<>();
        roomComboBox.setBounds(100, 20, 140, 25);

        // Check if any rooms are occupied
        boolean hasOccupiedRooms = false;
        for (Room room : rooms) {
            if (room.getRoomStatus() == RoomStatus.OCCUPIED) {
                roomComboBox.addItem(String.valueOf(room.getRoomNumber()));
                hasOccupiedRooms = true;
            }
        }

        if (!hasOccupiedRooms) {
            roomComboBox.addItem("No rooms booked");
        }
        formPanel.add(roomComboBox);

        // Add labels for guest information
        JLabel guestNameLabel = new JLabel("Name:");
        guestNameLabel.setBounds(20, 60, 180, 25);
        guestNameLabel.setForeground(Color.WHITE);
        formPanel.add(guestNameLabel);

        JLabel guestIdLabel = new JLabel("ID:");
        guestIdLabel.setBounds(20, 100, 180, 25);
        guestIdLabel.setForeground(Color.WHITE);
        formPanel.add(guestIdLabel);

        JLabel guestMailLabel = new JLabel("Email:");
        guestMailLabel.setBounds(20, 140, 180, 25);
        guestMailLabel.setForeground(Color.WHITE);
        formPanel.add(guestMailLabel);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(250, 20, 100, 25);
        searchButton.addActionListener(e -> {
            String selectedRoom = (String) roomComboBox.getSelectedItem();
            if (selectedRoom.equals("No rooms booked")) {
                JOptionPane.showMessageDialog(null, "No rooms are currently booked.");
                return;
            }

            int selectedRoomNo = Integer.parseInt(selectedRoom);
            Room roomToReturn = getRoomByNumber(selectedRoomNo);
            if (roomToReturn != null) {
                Booking booking = null;
                for (Booking b : bookings) {
                    if (b.getRoom().equals(roomToReturn)) {
                        booking = b;
                        break;
                    }
                }
                if (booking != null) {
                    guestNameLabel.setText("Name: " + booking.getGuest().getCustomerName());
                    guestIdLabel.setText("ID: " + booking.getGuest().getCustomerId());
                    guestMailLabel.setText("Email: " + booking.getGuest().getCustomerEmail());
                }
            }
        });
        formPanel.add(searchButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(20, 180, 100, 25);
        checkoutButton.addActionListener(e -> {
            String selectedRoom = (String) roomComboBox.getSelectedItem();
            if (selectedRoom.equals("No rooms booked")) {
                JOptionPane.showMessageDialog(null, "No rooms are currently booked.");
                return;
            }
            
            int selectedRoomNo = Integer.parseInt(selectedRoom);
            Room roomToReturn = getRoomByNumber(selectedRoomNo);
            if (roomToReturn == null) {
                JOptionPane.showMessageDialog(null, "Invalid Room No or the room is not occupied.");
                return;
            }

            ReturnRoom(roomToReturn);
            JOptionPane.showMessageDialog(null, "Guest has successfully checked out");
            checkoutFrame.dispose();
        });
        formPanel.add(checkoutButton);

        // Add form panel to main panel
        mainPanel.add(formPanel);

        // Add main panel to frame
        checkoutFrame.add(mainPanel);
        checkoutFrame.setVisible(true);
    }

    private static void ReturnRoom(Room room) {
        room.setRoomStatus(RoomStatus.AVAILABLE);
        Booking bookingToRemove = null;

        for (Booking booking : bookings) {
            if (booking.getRoom().equals(room)) {
                bookingToRemove = booking;
                break;
            }
        }

        if (bookingToRemove != null) {
            bookings.remove(bookingToRemove);
            // Write updated room status to file
            writeRoomsToFileByArrayList("roomDetails.txt");
            // Write updated bookings to file
            writeBookingsToFile();
            System.out.println("Room returned successfully.");
        } else {
            System.out.println("Room was not rented.");
        }
    }

    public void AllocateRoomGui() {
        JFrame allocateFrame = new JFrame("Allocate Room");
        allocateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        allocateFrame.setBounds(450, 100, 500, 500);

        // Create main panel with background
        JPanel mainPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageUtils.loadImage("Booking1.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        mainPanel.setBounds(0, 0, 500, 500);

        // Create form panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(50, 50, 400, 400);
        formPanel.setOpaque(false);

        // Add components to form panel
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 25);
        nameLabel.setForeground(Color.WHITE);
        formPanel.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(100, 20, 200, 25);
        nameField.setText(this.nameField.getText()); // Set from booking form
        nameField.setEditable(false); // Make non-editable
        formPanel.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 60, 80, 25);
        emailLabel.setForeground(Color.WHITE);
        formPanel.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(100, 60, 200, 25);
        emailField.setText(this.emailField.getText()); // Set from booking form
        emailField.setEditable(false); // Make non-editable
        formPanel.add(emailField);

        JLabel bookingIdLabel = new JLabel("Booking ID:");
        bookingIdLabel.setBounds(20, 100, 80, 25);
        bookingIdLabel.setForeground(Color.WHITE);
        formPanel.add(bookingIdLabel);

        JTextField bookingIdField = new JTextField();
        bookingIdField.setBounds(100, 100, 200, 25);
        bookingIdField.setText(this.bookingIdField.getText()); // Set from booking form
        bookingIdField.setEditable(false);
        formPanel.add(bookingIdField);

        JLabel roomLabel = new JLabel("Room:");
        roomLabel.setBounds(20, 140, 80, 25);
        roomLabel.setForeground(Color.WHITE);
        formPanel.add(roomLabel);

        JTextField roomField = new JTextField();
        roomField.setBounds(100, 140, 200, 25);
        roomField.setText(roomComboBox.getSelectedItem().toString()); // Set from booking form
        roomField.setEditable(false); // Make non-editable
        formPanel.add(roomField);

        JButton allocateButton = new JButton("Allocate");
        allocateButton.setBounds(150, 180, 100, 25);
        allocateButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                String email = emailField.getText();
                Guest guest = new Guest(name, email, "Customer " + (guests.size() + 1));
                guests.add(guest);
                int bookingId = Integer.parseInt(bookingIdField.getText());
                Room selectedRoom = getRoomByNumber(Integer.parseInt(roomField.getText()));
                if (selectedRoom != null) {
                    allocateRoom(selectedRoom, guest, bookingId, Integer.parseInt(bookingDateField.getText()));
                    writeBookingsToFile(); // Save booking to file
                    JOptionPane.showMessageDialog(null, "Room allocated successfully");
                    allocateFrame.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid booking ID");
            }
        });
        formPanel.add(allocateButton);

        // Add form panel to main panel
        mainPanel.add(formPanel);

        // Add main panel to frame
        allocateFrame.add(mainPanel);
        allocateFrame.setVisible(true);
    }

}