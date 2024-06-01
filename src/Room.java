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

class Room extends JFrame {
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

        // Load the background image
        ImageIcon backgroundImage = new ImageIcon("C:\\Users\\SHAHBAZ TRADERS\\IdeaProjects\\Project1\\src\\download.jpg");
        // Resize the image to fit the frame size
        Image img = backgroundImage.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        backgroundImage = new ImageIcon(img);

        // Create a JLabel to hold the background image
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(null);

        // Create a JPanel to hold the form components
        JPanel panel = new JPanel(null);
        panel.setOpaque(false); // Make the panel transparent

        // Your code for components
        JLabel roomNumberLabel = new JLabel("Room Number:");
        JTextField roomNumberField = new JTextField(10);
        roomNumberLabel.setBounds(50, 50, 100, 30);
        roomNumberField.setBounds(200, 50, 150, 30);
        panel.add(roomNumberLabel);
        panel.add(roomNumberField);

        JLabel roomTypeLabel = new JLabel("Room Type:");
        JComboBox<RoomType> roomTypeComboBox = new JComboBox<>();
        roomTypeComboBox.addItem(RoomType.SINGLE);
        roomTypeComboBox.addItem(RoomType.DOUBLE);
        roomTypeComboBox.addItem(RoomType.SUITE);
        roomTypeLabel.setBounds(50, 100, 100, 30);
        roomTypeComboBox.setBounds(200, 100, 150, 30);
        panel.add(roomTypeLabel);
        panel.add(roomTypeComboBox);

        JLabel rateLabel = new JLabel("Rate:");
        JTextField rateField = new JTextField(10);
        rateLabel.setBounds(50, 150, 100, 30);
        rateField.setBounds(200, 150, 150, 30);
        panel.add(rateLabel);
        panel.add(rateField);

        JLabel amenitiesLabel = new JLabel("Amenities:");
        JTextField amenitiesField = new JTextField(10);
        amenitiesLabel.setBounds(50, 200, 100, 30);
        amenitiesField.setBounds(200, 200, 150, 30);
        panel.add(amenitiesLabel);
        panel.add(amenitiesField);

        JButton addButton = new JButton("Add Room");
        addButton.setBounds(150, 250, 120, 30);
        panel.add(addButton);

        // Set the size and position of the panel
        panel.setBounds((600 - 400) / 2, (600 - 300) / 2, 400, 300);

        // Add the panel with components to the background label
        backgroundLabel.add(panel);

        // Set the background label as the content pane of the frame
        setContentPane(backgroundLabel);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

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

                    // Call the saveRoom method with the room details
                    Room room = new Room(roomNumber, roomType, rate, amenities);
                    HotelManagementSystem.addRoom(room);
                    JOptionPane.showMessageDialog(null, "Room added Successfully");
                    // Write room details to the file
                    writeRoomToFile(room);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Room.this, "Invalid input for room number or rate.");
                }
            }
        });
    }




    public void setCheckInDate(Date checkInDate)
    {
        this.checkInDate = checkInDate;
    }

    public void setCheckOutDate(Date checkOutDate)
    {
        this.checkOutDate = checkOutDate;
    }

    private void writeRoomToFile(Room room) {
        String filePath = "RoomDetails.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(room.getRoomDetails());
            writer.newLine(); // Adding a newline after each room's details
            System.out.println("Room successfully written to the file.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while writing to the file: " + ex.getMessage());
        }
    }

    private static List<Room> readRoomsFromFile(String filePath) {

        rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s*\\|\\s*");
                int roomNumber = Integer.parseInt(parts[0].trim());
                RoomType roomType = RoomType.valueOf(parts[1].trim());
                RoomStatus roomStatus = RoomStatus.valueOf(parts[2].trim());
                double rate = Double.parseDouble(parts[3].trim());
                List<String> amenities = new ArrayList<>();
                for (int i = 4; i < parts.length; i++) {
                    amenities.add(parts[i].trim());
                }
                Room room = new Room(roomNumber, roomType, rate, amenities);
                rooms.add(room);

                System.out.println("Added room: " + room);
            }
        } catch (IOException | IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading from the file: " + ex.getMessage());
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