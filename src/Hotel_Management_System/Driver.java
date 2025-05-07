package Hotel_Management_System;

import javax.swing.*;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        new Splash();
        SwingUtilities.invokeLater(() -> {
            // Initialize HotelManagementSystem
            HotelManagementSystem hms = new HotelManagementSystem();
            
            // Read rooms from file
            String filePath = "roomDetails.txt";
            List<Room> rooms = Room.readRoomsFromFile(filePath);
            
            // Add rooms to HotelManagementSystem
            for (Room room : rooms) {
                HotelManagementSystem.addRoom(room);
            }
            
            // Show room details
            hms.showRoomDetails();
            System.out.println(hms.getRoomByNumber(104));
            
            // Write rooms to file (in case the file doesn't exist)
            hms.writeRoomsToFileByArrayList(filePath);

            System.out.println("Hotel Management System initialized successfully!");
        });
    }
}