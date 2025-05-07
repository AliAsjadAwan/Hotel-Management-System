package Hotel_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

enum RoomType {
    SINGLE, DOUBLE, SUITE
}

enum RoomStatus {
    AVAILABLE, OCCUPIED, MAINTENANCE
}

public class Room extends JFrame {
    private JButton addButton;
    private int roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private double rate;
    private List<String> amenities;
    private static final String[] COLUMN_NAMES = {"Room Number", "Room Type", "Status", "Rate", "Amenities"};
    Date checkInDate;
    Date checkOutDate;

    private static List<Room> rooms = new ArrayList<>();

    public Room(int roomNumber, RoomType roomType, double rate, List<String> amenities) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = RoomStatus.AVAILABLE;
        this.rate = rate;
        this.amenities = new ArrayList<>(amenities);
    }

    public Room() {
        super("Add A Room");
        this.setBounds(450, 100, 600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a panel with background image
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageUtils.loadImage("download.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // Create a panel for the form components
        JPanel formPanel = new JPanel(null);
        formPanel.setOpaque(false);
        formPanel.setBounds(100, 100, 400, 400);

        // Room Number
        JLabel roomNumberLabel = new JLabel("Room Number:");
        roomNumberLabel.setForeground(Color.WHITE);
        JTextField roomNumberField = new JTextField(10);
        roomNumberLabel.setBounds(50, 50, 100, 30);
        roomNumberField.setBounds(200, 50, 150, 30);
        formPanel.add(roomNumberLabel);
        formPanel.add(roomNumberField);

        // Room Type
        JLabel roomTypeLabel = new JLabel("Room Type:");
        roomTypeLabel.setForeground(Color.WHITE);
        JComboBox<RoomType> roomTypeComboBox = new JComboBox<>();
        roomTypeComboBox.addItem(RoomType.SINGLE);
        roomTypeComboBox.addItem(RoomType.DOUBLE);
        roomTypeComboBox.addItem(RoomType.SUITE);
        roomTypeLabel.setBounds(50, 100, 100, 30);
        roomTypeComboBox.setBounds(200, 100, 150, 30);
        formPanel.add(roomTypeLabel);
        formPanel.add(roomTypeComboBox);

        // Rate
        JLabel rateLabel = new JLabel("Rate:");
        rateLabel.setForeground(Color.WHITE);
        JTextField rateField = new JTextField(10);
        rateLabel.setBounds(50, 150, 100, 30);
        rateField.setBounds(200, 150, 150, 30);
        formPanel.add(rateLabel);
        formPanel.add(rateField);

        // Amenities
        JLabel amenitiesLabel = new JLabel("Amenities:");
        amenitiesLabel.setForeground(Color.WHITE);
        JTextField amenitiesField = new JTextField(10);
        amenitiesLabel.setBounds(50, 200, 100, 30);
        amenitiesField.setBounds(200, 200, 150, 30);
        formPanel.add(amenitiesLabel);
        formPanel.add(amenitiesField);

        // Add Room Button
        addButton = new JButton("Add Room");
        addButton.setBounds(150, 250, 120, 30);
        addButton.setBackground(new Color(3, 45, 48));
        addButton.setForeground(Color.WHITE);
        formPanel.add(addButton);

        // Add the form panel to the content pane
        contentPane.add(formPanel);

        // Add action listener for the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int roomNumber = Integer.parseInt(roomNumberField.getText());
                    RoomType roomType = (RoomType) roomTypeComboBox.getSelectedItem();
                    double rate = Double.parseDouble(rateField.getText());
                    String[] amenitiesArray = amenitiesField.getText().split(",");
                    List<String> amenities = new ArrayList<>();
                    for (String amenity : amenitiesArray) {
                        amenities.add(amenity.trim());
                    }

                    Room room = new Room(roomNumber, roomType, rate, amenities);
                    HotelManagementSystem.addRoom(room);
                    JOptionPane.showMessageDialog(null, "Room added Successfully");
                    writeRoomToFile(room);
                    dispose(); // Close the window after adding the room
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Room.this, "Invalid input for room number or rate.");
                }
            }
        });

        setVisible(true);
    }

    public void setCheckInDate(Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate)
    {
        this.checkOutDate = checkOutDate;
    }

    public void writeRoomToFile(Room room) {
        String filePath = "roomDetails.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            // Check if file is empty to write header
            File file = new File(filePath);
            if (file.length() == 0) {
                writer.write(String.format("%-10s | %-10s | %-12s | %-8s | %-30s%n", "Room No", "Type", "Status", "Rate", "Amenities"));
                writer.write("--------------------------------------------------------------------------------------------\n");
            }
            writer.write(room.getRoomDetails());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<Room> readRoomsFromFile(String filePath) {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Skip header lines
            reader.readLine(); // Skip column headers
            reader.readLine(); // Skip separator line
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // Skip empty lines
                
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 5) continue; // Skip invalid lines
                
                try {
                    int roomNumber = Integer.parseInt(parts[0].trim());
                    RoomType roomType = RoomType.valueOf(parts[1].trim());
                    RoomStatus roomStatus = RoomStatus.valueOf(parts[2].trim());
                    double rate = Double.parseDouble(parts[3].trim());
                    
                    // Parse amenities
                    List<String> amenities = new ArrayList<>();
                    String[] amenityParts = parts[4].trim().split(",\\s*");
                    for (String amenity : amenityParts) {
                        amenities.add(amenity.trim());
                    }
                    
                    Room room = new Room(roomNumber, roomType, rate, amenities);
                    room.setRoomStatus(roomStatus); // Set the room status from file
                    rooms.add(room);
                } catch (IllegalArgumentException e) {
                    System.err.println("Error parsing line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return rooms;
    }

    public static List<Room> getRooms() {
        return rooms;
    }

    private static void populateTable(JTable roomTable, List<Room> rooms) {
        DefaultTableModel tableModel = (DefaultTableModel) roomTable.getModel();
        for (Room room : rooms) {
            Object[] rowData = {
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getRoomStatus(),
                    room.getRate(),
                    String.join(", ", room.getAmenities())
            };
            tableModel.addRow(rowData);
        }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public double getRate() {
        return rate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void addAmenity(String amenity) {
        if (!amenities.contains(amenity)) {
            amenities.add(amenity);
        }
    }

    public void removeAmenity(String amenity) {
        amenities.remove(amenity);
    }

    public void deallocateRoom() {
        if (this.roomStatus == RoomStatus.OCCUPIED) {
            this.roomStatus = RoomStatus.AVAILABLE;
            System.out.println("Room " + roomNumber + " has been deallocated.");
        } else {
            System.out.println("Room " + roomNumber + " is not currently occupied.");
        }
    }

    public void putUnderMaintenance() {
        if (this.roomStatus == RoomStatus.AVAILABLE) {
            this.roomStatus = RoomStatus.MAINTENANCE;
            System.out.println("Room " + roomNumber + " is now under maintenance.");
        } else {
            System.out.println("Room " + roomNumber + " cannot be put under maintenance as it is currently " + this.roomStatus.toString().toLowerCase() + ".");
        }
    }

    public String getRoomDetails() {
        return String.format("%-10d | %-10s | %-12s | %-8.2f | %-30s",
                roomNumber, roomType, roomStatus, rate, String.join(", ", amenities));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", roomStatus=" + roomStatus +
                ", rate=" + rate +
                ", amenities=" + amenities +
                '}';
    }
}