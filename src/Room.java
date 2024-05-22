import java.util.*;
import java.io.*;

enum RoomType {
    SINGLE, DOUBLE, SUITE
}

enum RoomStatus {
    AVAILABLE, OCCUPIED, MAINTENANCE
}

class Room{
    private int roomNumber;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private double rate;
    private int floor;
    private List<String> amenities;
    private List<Booking> bookings;
    //private static final int HEADER_SIZE = 100;
    private static final String ROOM_FILE = "room_details.txt";
    private static final List<Integer> writtenRoomNumbers = new ArrayList<>();

    public Room(int roomNumber, RoomType roomType, RoomStatus roomStatus, double rate, int floor, List<String> amenities) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.rate = rate;
        this.floor = floor;
        this.amenities = amenities;
        //this.bookings = bookings;
    }

    public int getRoomNumber() {
        return roomNumber;
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

    public int getFloor() {
        return floor;
    }

    public void allocateRoom() {
        if (this.roomStatus == RoomStatus.AVAILABLE) {
            this.roomStatus = RoomStatus.OCCUPIED;
            System.out.println("Room " + roomNumber + " has been allocated.");
        } else {
            System.out.println("Room " + roomNumber + " is not available for allocation.");
        }
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
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10d | %-10s | %-12s | %-8.2f | %-30s%n",
                roomNumber, roomType, roomStatus, rate, String.join(", ", amenities)));
        return sb.toString();
    }

  /*  private static boolean isHeaderWritten(String filename) {
        File file = new File(ROOM_FILE);
        return file.length() > HEADER_SIZE;
    }*/


    public static void writeRoomsDetailsToFile(List<Room> rooms, String filename) {
        //boolean headerWritten = isHeaderWritten(filename);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {

            boolean isEmpty = new File(ROOM_FILE).length() == 0;
            if (isEmpty) {
                writer.write(String.format("%-10s | %-10s | %-12s | %-8s | %-30s%n", "Room No", "Type", "Status", "Rate", "Amenities"));
                writer.write("--------------------------------------------------------------------------------------------\n");
            }

            for (Room room : rooms) {

                    writer.write(room.getRoomDetails());
                    writtenRoomNumbers.add(room.roomNumber);
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public boolean isAvailable(Date checkInDate, Date checkOutDate) {
        for (Booking booking : bookings) {
            if ((checkInDate.isBefore(booking.getCheckOutDate()) && checkOutDate.isAfter(booking.getCheckInDate())) ||
                    checkInDate.isEqual(booking.getCheckInDate()) || checkOutDate.isEqual(booking.getCheckOutDate())) {
                return false;
            }
        }
        return true;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    /*public static void readRoomsDetailsFromFile(List<Room> rooms, String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Skip the header lines
            reader.readLine(); // Skip the header line
            reader.readLine(); // Skip the separator line

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                int roomNumber = Integer.parseInt(parts[0].trim());
                RoomType roomType = RoomType.valueOf(parts[1].trim());
                RoomStatus roomStatus = RoomStatus.valueOf(parts[2].trim());
                double rate = Double.parseDouble(parts[3].trim());
                int floor = Integer.parseInt(parts[4].trim());
                List<String> amenities = Arrays.asList(parts[5].trim().split(","));

                // Parse booking details
                List<Booking> bookings = new ArrayList<>();
                if (parts.length > 6) {
                    String[] bookingDetails = parts[6].trim().split(",");
                    for (String bookingDetail : bookingDetails) {
                        int bookingId = Integer.parseInt(bookingDetail.trim()); // Assuming booking details are booking IDs
                        // Create a booking with default values (adjust as needed)
                        Booking booking = new Booking(bookingId, null, null, 0, 1, null, null, 121, null);
                        bookings.add(booking);
                    }
                }

                Room room = new Room(roomNumber, roomType, roomStatus, rate, floor, amenities, bookings);
                rooms.add(room);
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
    }*/


}