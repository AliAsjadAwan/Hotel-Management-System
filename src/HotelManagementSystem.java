import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class HotelManagementSystem extends JFrame {

    private static List<Room> rooms=new ArrayList<>();
    private static List<Booking> bookings=new ArrayList<>();
    private List<Guest> guests =new ArrayList<>();
    JTextField  checkInDateField;
    JTextField  checkOutDateField;
    JComboBox <Integer>  roomComboBox;
    JTextField bookingDateField;

    JTextField nameField;
    JTextField emailField;
    private int maxBookingId = 0;

    private JTextField bookingIdField;

    //    private static final int HEADER_SIZE = 100;
//
    public HotelManagementSystem() {

//
//        this.setTitle("");
//        this.setSize(500, 500);
////        Border border=BorderFactory.createLineBorder(Color.green,10,true);
//        this.getContentPane().setBackground(Color.lightGray);
//        this.setResizable(false);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(null);
//         JButton viewRoom = new JButton("View Rooms");
//        JButton bookButton=new JButton("Book A Room");
//
//        viewRoom.setFocusable(false);
//        bookButton.setFocusable(false);
////        viewRoom.setBorder(border);
//       viewRoom.setBounds(100, 30, 200, 30);
//       bookButton.setBounds(100,70,200,30);
//        // Add buttons to frame
//        add(viewRoom);
//        add(bookButton);
////
//
//
//viewRoom.addActionListener(new ActionListener() {
//    @Override
//
//    public void actionPerformed(ActionEvent e) {
//        showAllRoomDetails();
//    }
//
//});
//
//bookButton.addActionListener(new ActionListener() {
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        BookingRoomGui();
//    }
//});
//        this.setVisible(true);
//
    }


    public static boolean addRoom(Room room) {
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


//    public void addBooking(int bookingId, Guest guest, BookingStatus bookingStatus, String paymentStatus, Room room, Date bookingDate, Date checkInDate, Date checkOutDate) {
//        bookingType bookingtype;
//        if (room.getRoomStatus() == RoomStatus.AVAILABLE) {
//            if (bookingDate.equals(checkInDate)) {
//                bookingtype = bookingType.ATTHESPOT;
//            } else {
//                bookingtype = bookingType.ADVANCE;
//            }
//            room.setRoomStatus(RoomStatus.OCCUPIED);
//            bookings.add(new Booking(bookingId, guest, room, bookingStatus, paymentStatus, bookingDate, checkInDate, checkOutDate, bookingtype)); // Ek naya booking add karna hai.
//        } else {
//            System.out.println("Room is not available for booking.");
//        }
//    }


    public List<Room> getAvailableRooms(RoomType roomType) {
        List<Room> availableRooms = new ArrayList<>();

        for (Room room:rooms) {
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

        System.out.println(sb.toString());
    }
//    public void showRoomDetails() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("%-10s | %-10s | %-12s | %-8s | %-30s%n", "Room No", "Type", "Status", "Rate", "Amenities"));
//        sb.append("--------------------------------------------------------------------------------------------\n");
//
//        for (Room room : rooms) {
//            sb.append(room.getRoomDetails());
//        }
//
//        System.out.println(sb.toString());
//    }

    public static void showAllRoomDetails() {
        String[] columnNames = {"Room No", "Type", "Status", "Rate", "Amenities"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (Room room : rooms) {
            List<String> amenities = room.getAmenities();
            String amenitiesStr = String.join(", ", amenities);
            Object[] row = {room.getRoomNumber(), room.getRoomType(), room.getRoomStatus(), room.getRate(), amenitiesStr};
            tableModel.addRow(row);
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

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = ImageIO.read(new File("C:\\Users\\SHAHBAZ TRADERS\\IdeaProjects\\Project1\\src\\Login1.jpg"));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        backgroundPanel.setBounds(0, 0, 600, 400);
        backgroundPanel.setLayout(null);

        // Add the scroll pane to the background panel
        backgroundPanel.add(scrollPane);

        JFrame detailsFrame = new JFrame("Room Details");
        detailsFrame.setBounds(400, 100, 600, 400);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setContentPane(backgroundPanel);
        detailsFrame.setVisible(true);
    }





    public void BookingRoomGui() {
        JFrame detailsFrame = new JFrame("Room Details");
        detailsFrame.setBounds(400, 100, 600, 400);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.getContentPane().setBackground(new Color(3, 45, 48));
         //Add background image


        JFrame bookingFrame = new JFrame("Add Booking");
        bookingFrame.setBounds(400,100,600, 600);
        bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bookingFrame.setLayout(null);

        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                ImageIcon backgroundImage = new ImageIcon("C:\\Users\\SHAHBAZ TRADERS\\IdeaProjects\\Project1\\src\\Booking1.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        contentPane.setLayout(null); // Use null layout for absolute positioning of components
        bookingFrame.setContentPane(contentPane);

        // Set background color for the content pane of bookingFrame
       // bookingFrame.getContentPane().setBackground(Color.BLACK);
        bookingFrame.getLayeredPane().setOpaque(false);
        //bookingFrame.getContentPane().setOpaque(false);
        //bookingFrame.setBackground(new Color(3,45,48));
        // Components for guest details
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

        // Components for booking details
        JLabel bookingIdLabel = new JLabel("Booking ID:   ");
        bookingIdLabel.setBounds(50,150,150,30);
        bookingIdLabel.setForeground(Color.WHITE);
        bookingIdField = new JTextField();
        bookingIdField.setBounds(300,150,180,30);
        bookingIdField.setEditable(false);
        JLabel bookingDateLabel = new JLabel("Days to Stay:  ");
        bookingDateLabel.setBounds(50,200,150,30);
        bookingDateLabel.setForeground(Color.WHITE);
        bookingDateField = new JTextField();
        bookingDateField.setBounds(300,200,180,30);
//        JLabel checkInDateLabel = new JLabel("Check-in Date:  ");
//        checkInDateLabel.setBounds(50,250,150,30);
//        checkInDateField = new JTextField();
//        checkInDateField.setBounds(300,250,180,30);
//        JLabel checkOutDateLabel = new JLabel("Check-out Date:  ");
//        checkOutDateLabel.setBounds(50,300,150,30);
//        checkOutDateField = new JTextField();
//        checkOutDateField.setBounds(50,250,150,30);
        JButton BookRoomButton = new JButton("Book");
        BookRoomButton.setBounds(170,400,150,40);
        JLabel roomType=new JLabel("Room Type");
        roomType.setBounds(50,250,150,30);
        roomType.setForeground(Color.WHITE);
        JRadioButton Single = new JRadioButton("Single");
        Single.setBounds(200, 250, 100, 30);
        Single.setForeground(Color.WHITE);
        JRadioButton Double = new JRadioButton("Double");
        Double.setBounds(300, 250, 100, 30);
        Double.setForeground(Color.WHITE);
        JRadioButton Suite = new JRadioButton("Suite");
        Suite.setBounds(400, 250, 100, 30);
        Suite.setForeground(Color.WHITE);
        ButtonGroup buttonGroup=new ButtonGroup();
        buttonGroup.add(Single);
        buttonGroup.add(Double);
        buttonGroup.add(Suite);
        Single.setOpaque(false);
        Double.setOpaque(false);
        Suite.setOpaque(false);

        JLabel roomLabel = new JLabel("ROOM: ");
        roomLabel.setBounds(50,300,150,30);
        roomLabel.setForeground(Color.WHITE);
        roomComboBox = new JComboBox<>();
        roomComboBox.setBounds(300,300,180,30);

        Single.addActionListener(e -> {
            displayRoomsByType(RoomType.SINGLE);
        });

        Double.addActionListener(e -> {
            displayRoomsByType(RoomType.DOUBLE);
        });

        Suite.addActionListener(e -> {
            displayRoomsByType(RoomType.SUITE);
        });
        BookRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AllocateRoomGui();
                } catch (RuntimeException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Add components to the form
        bookingFrame.add(nameLabel);
        bookingFrame.add(nameField);
        bookingFrame.add(emailLabel);
        bookingFrame.add(emailField);
        bookingFrame.add(bookingIdLabel);
        bookingFrame.add(bookingIdField);
        bookingFrame.add(bookingDateLabel);
        bookingFrame.add(bookingDateField);
       // bookingFrame.add(checkInDateLabel);
        //bookingFrame.add(checkInDateField);
       // bookingFrame.add(checkOutDateLabel);
       // bookingFrame.add(checkOutDateField);
        bookingFrame.add(roomLabel);
        bookingFrame.add(roomComboBox);
        bookingFrame.add(BookRoomButton);
        bookingFrame.add(roomType);
        bookingFrame.add(Single);
        bookingFrame.add(Double);
        bookingFrame.add(Suite);
        // Make the form visible
        bookingFrame.setVisible(true);
        generateBookingId();
    }


    //    private static boolean isHeaderWritten(String filename) {
////        File file = new File(filename);
////        return file.length() > HEADER_SIZE;
////    }
    public void displayRoomsByType(RoomType roomType){
        // Parse check-in date using your custom date class
        //Date checkInDate = Date.parse(checkInDateField.getText());

        // Parse check-out date using your custom date class
        //Date checkOutDate = Date.parse(checkOutDateField.getText());

        // Call getAvailableRooms with parsed dates
        List<Room> availableRooms = getAvailableRooms(roomType);
        // Clear existing items in the roomComboBox
        roomComboBox.removeAllItems();
        // Add available rooms to roomComboBox
        for (Room room : availableRooms) {
            roomComboBox.addItem(room.getRoomNumber());
        }
    }


    public void writeRoomsToFileByArrayList(String filePath) {
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

    public boolean isAvailable() {
        for (Room room :rooms) {
            if (room.getRoomStatus()==RoomStatus.AVAILABLE) {
                return true;
            }
        }
        return false;
    }


    public void allocateRoom(Room room, Guest guest, int bookingId,int rentDays) {
        if (room.getRoomStatus() == RoomStatus.AVAILABLE) {
            room.setRoomStatus(RoomStatus.OCCUPIED);
            bookings.add(new Booking(bookingId,guest,room,rentDays));
        } else {
            System.out.println("Room " + room.getRoomNumber() + " is not available for allocation.");
        }
    }

    public void AllocateRoomGui() throws RuntimeException {
        Room selectedRoom=null;
        int SelectedRoomNumber=(Integer) roomComboBox.getSelectedItem();
        for (Room room:rooms){
            if (room.getRoomNumber()==SelectedRoomNumber){
                selectedRoom=room;
                break;
            }
        }



        int Days=Integer.parseInt(bookingDateField.getText());
        String name=nameField.getText();
        String email=emailField.getText();

        Guest guest=new Guest(name,email,"Customer "+(guests.size()+1));
        guests.add(guest);
        int bookingid=Integer.parseInt(bookingIdField.getText());
        allocateRoom(selectedRoom,guest,bookingid,Days);
        BillForm billForm=new BillForm(selectedRoom, guest, bookingid,Days);


    }
    public void  generateBookingId(){
        int generatedId = (int) (Math.random() * 900) + 100;
        if (generatedId <= maxBookingId) {
            generatedId = maxBookingId + 1;
        }
        maxBookingId = generatedId;
        bookingIdField.setText(String.valueOf(generatedId));
    }
    public static void checkOut() {
        JFrame checkoutFrame = new JFrame("Checkout");
        checkoutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        checkoutFrame.setBounds(450,100,500, 500); // Set initial size

        // Set background image
        ImageIcon background = new ImageIcon("C:\\Users\\SHAHBAZ TRADERS\\IdeaProjects\\Project1\\src\\Checkout.jpg");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
        checkoutFrame.getLayeredPane().add(backgroundLabel, Integer.valueOf(Integer.MIN_VALUE));
        JPanel contentPane = (JPanel) checkoutFrame.getContentPane();
        contentPane.setOpaque(false);
       // checkoutFrame.setContentPane().setBack

        JPanel checkoutPanel = new JPanel(null); // Use null layout
        checkoutPanel.setPreferredSize(new Dimension(background.getIconWidth(), background.getIconHeight()));

        JLabel roomNoLabel = new JLabel("Room No:");
        roomNoLabel.setBounds(20, 20, 80, 25);
        checkoutPanel.add(roomNoLabel);

        JTextField roomNoText = new JTextField();
        roomNoText.setBounds(100, 20, 120, 25);
        checkoutPanel.add(roomNoText);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(250, 20, 100, 25);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchRoom(roomNoText);
            }
        });
        //checkoutPanel.add(searchButton);

        JLabel guestname = new JLabel("Name");
        guestname.setBounds(20, 60, 200, 25);
        checkoutPanel.add(guestname);

        JLabel GuestId = new JLabel("ID ");
        GuestId.setBounds(20, 90, 200, 25);
        checkoutPanel.add(GuestId);

        JLabel GuestMail = new JLabel("Email");
        GuestMail.setBounds(20, 120, 200, 25);
        checkoutPanel.add(GuestMail);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(20, 150, 100, 25);
        checkoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int roomNo = Integer.parseInt(roomNoText.getText());
                if (roomNo == 0) {
                    JOptionPane.showMessageDialog(null, "Room No cannot be empty.");
                    return;
                }

                Room roomToReturn = null;
                for (Room room : rooms) {
                    if (room.getRoomNumber() == roomNo && room.getRoomStatus() == RoomStatus.OCCUPIED) {
                        roomToReturn = room;
                        break;
                    }
                }

                if (roomToReturn == null) {
                    JOptionPane.showMessageDialog(null, "Invalid Room No or the room is not occupied.");
                    return;
                }

                Guest guest = null;
                Booking booking = null;
                for (Booking b : bookings) {
                    if (b.getRoom().equals(roomToReturn)) {
                        guest = b.getGuest();
                        booking = b;
                        break;
                    }
                }

                if (guest == null) {
                    JOptionPane.showMessageDialog(null, "Room was not booked.");
                    return;
                }

                ReturnRoom(roomToReturn);
                guestname.setText("Name: " + guest.getCustomerName());
                GuestId.setText("ID: " + guest.getCustomerId());
                GuestMail.setText("Email: " + guest.getCustomerEmail());
                JOptionPane.showMessageDialog(null,guestname+" has successfully Checkout");
            }
        });
        checkoutPanel.add(checkoutButton);

        checkoutFrame.add(checkoutPanel);
        checkoutFrame.pack();
        checkoutFrame.setVisible(true);
    }

    private static void searchRoom(JTextField roomNoText) {
        int roomNo;
        try {
            roomNo = Integer.parseInt(roomNoText.getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Room No.");
            return;
        }

        if (roomNo == 0) {
            JOptionPane.showMessageDialog(null, "Room No cannot be empty.");
            return;
        }

        Room roomToReturn = null;
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNo && room.getRoomStatus() == RoomStatus.OCCUPIED) {
                roomToReturn = room;
                break;
            }
        }

        if (roomToReturn == null) {
            JOptionPane.showMessageDialog(null, "Invalid Room No or the room is not occupied.");
            return;
        }

        Guest guest = null;
        Booking booking = null;
        for (Booking b : bookings) {
            if (b.getRoom().equals(roomToReturn)) {
                guest = b.getGuest();
                booking = b;
                break;
            }
        }

        if (guest == null) {
            JOptionPane.showMessageDialog(null, "Room was not booked.");
            return;
        }

        ReturnRoom(roomToReturn);
        JOptionPane.showMessageDialog(null, "Room returned successfully by " + guest.getCustomerName());
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
            System.out.println("Room returned successfully.");
        } else {
            System.out.println("Room was not rented.");
        }
    }

}





