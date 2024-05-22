import java.util.*;

public class HotelManagementSystem extends Date{
    private static List<Room> rooms = new ArrayList<>();
    private List<Guest> guests = new ArrayList<>();
    private List<Booking> bookings = new ArrayList<>();
    private List<Employee> employees = new ArrayList<>();
    private List<Payment> payments = new ArrayList<>();


    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void addGuest(Guest guest) {
        guests.add(guest);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void showAllRoomDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s | %-10s | %-12s | %-8s | %-30s%n", "Room No", "Type", "Status", "Rate", "Amenities"));
        sb.append("--------------------------------------------------------------------------------------------\n");

        for (Room room : rooms) {
            sb.append(room.getRoomDetails());
        }

        System.out.println(sb.toString());
    }

    public List<Room> getAvailableRooms(RoomType roomType, Date checkInDate, Date checkOutDate) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getRoomType() == roomType && room.isAvailable(checkInDate, checkOutDate)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public void displayAvailableRooms(RoomType roomType, Date checkInDate, Date checkOutDate) {
        List<Room> availableRooms = getAvailableRooms(roomType, checkInDate, checkOutDate);
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms for the selected category and date range.");
        } else {
            System.out.println("Available rooms for the selected category and date range:");
            for (Room room : availableRooms) {
                System.out.println(room.getRoomDetails());
            }
        }
    }
}
