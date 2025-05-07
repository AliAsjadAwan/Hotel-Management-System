package Hotel_Management_System;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewRoomForm extends JFrame {
    public ViewRoomForm() {
        setTitle("View Rooms");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(450, 100, 800, 600);

        // Create main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image backgroundImage = ImageUtils.loadImage("Login1.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
                }
            }
        };
        mainPanel.setBounds(0, 0, 800, 600);

        // Create table model
        String[] columnNames = {"Room No", "Type", "Status", "Rate", "Amenities"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Get rooms from HotelManagementSystem
        List<Room> rooms = HotelManagementSystem.getRooms();
        
        // Check if rooms list is null or empty
        if (rooms != null && rooms.size() > 0) {
            // Populate table with room data
            for (Room room : rooms) {
                List<String> amenities = room.getAmenities();
                String amenitiesStr = String.join(", ", amenities);
                Object[] row = {
                    room.getRoomNumber(),
                    room.getRoomType(),
                    room.getRoomStatus(),
                    room.getRate(),
                    amenitiesStr
                };
                tableModel.addRow(row);
            }
        } else {
            // Show message if no rooms are available
            JOptionPane.showMessageDialog(this, "No rooms are currently available.", "No Rooms", JOptionPane.INFORMATION_MESSAGE);
        }

        // Create table
        JTable roomTable = new JTable(tableModel);
        roomTable.setOpaque(false);
        roomTable.setForeground(Color.WHITE);
        roomTable.setBackground(new Color(0, 0, 0, 150));
        roomTable.setFillsViewportHeight(true);

        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(roomTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Create back button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose();
        });
        buttonPanel.add(backButton);

        // Add components to main panel
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ViewRoomForm();
    }
} 