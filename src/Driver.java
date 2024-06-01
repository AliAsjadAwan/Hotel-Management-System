import javax.swing.*;
import java.util.List;

public class Driver {
    public static void main(String[] args) {
        new Splash();
        SwingUtilities.invokeLater(() ->{


            HotelManagementSystem hms = new HotelManagementSystem();
//            hms.printRooms();
            hms.addRoom(new Room(101, RoomType.SINGLE, 100, List.of(new String[]{"TV", "Wi-Fi"})));
            hms.addRoom(new Room(102, RoomType.DOUBLE, 150, List.of(new String[]{"TV", "Wi-Fi", "Mini-bar"})));
            hms.addRoom(new Room(103,RoomType.SUITE,200, List.of(new String[]{"TV", "WI-Fi", "Mini-bar", "Swimming Pool"})));
            hms.addRoom(new Room(105,RoomType.SUITE,200, List.of(new String[]{"TV", "WI-Fi", "Mini-bar", "Swimming Pool"})));
            Room r1=new Room(104,RoomType.SINGLE,200, List.of(new String[]{"TV", "Swimming Pool"}));
            Room r2=new Room(106,RoomType.DOUBLE,400, List.of(new String[]{"TV", "WI-Fi", "Mini-bar"}));
            Room r3=new Room(107,RoomType.SUITE,500, List.of(new String[]{"TV", "WI-Fi", "Mini-bar", "Swimming Pool"}));
            Room r4=new Room(108,RoomType.DOUBLE,400, List.of(new String[]{"TV", "Swimming Pool"}));
            Room r5=new Room(109,RoomType.SINGLE,200, List.of(new String[]{"WI-Fi", "Mini-bar"}));
            Room r6=new Room(109,RoomType.SUITE,1000, List.of(new String[]{"TV", "WI-Fi", "Mini-bar", "Swimming Pool"}));
            Room r7=new Room(109,RoomType.SINGLE,200, List.of(new String[]{"WI-Fi", "Mini-bar"}));
            hms.addRoom(r1);
            hms.addRoom(r2);
            hms.addRoom(r4);
            hms.addRoom(r3);
            hms.addRoom(r5);
            hms.addRoom(r6);
            hms.addRoom(r7);

            //        r1.putUnderMaintenance();
            hms.showRoomDetails();
            //        System.out.println(hms.getRoomByNumber(104));
            String filePath = "roomDetails.txt";
            hms.writeRoomsToFileByArrayList(filePath);

            System.out.println("Hotel Management System initialized successfully!");

        });
    }}