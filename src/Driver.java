import java.util.*;

public class Driver {
    public static void main(String[] args) {
        HotelManagementSystem hms = new HotelManagementSystem();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        //Room room=
       // List<Booking> bookings = hms.getBookings();
        // Adding some initial rooms
        hms.addRoom(new Room(101, RoomType.SINGLE,RoomStatus.AVAILABLE,230,2, Arrays.asList("TV", "Wi-Fi")));
        hms.addRoom(new Room(102, RoomType.DOUBLE, RoomStatus.AVAILABLE,240,2, Arrays.asList("TV", "Wi-Fi", "Mini-bar")));
        hms.addRoom(new Room(103, RoomType.SUITE,RoomStatus.AVAILABLE,250,1, Arrays.asList("TV", "Wi-Fi", "Mini-bar", "Swimming Pool")));
        Room r1 = new Room(104, RoomType.SUITE,RoomStatus.MAINTENANCE,250,1, Arrays.asList("TV", "Wi-Fi", "Mini-bar", "Swimming Pool"));
        hms.addRoom(r1);
        r1.putUnderMaintenance();
        Guest guest1 = new Guest("John Doe", "123 Main St", "555-1234");
        hms.addGuest(guest1);

        Employee emp1 = new Employee(1, "Alice Smith", "Receptionist");
        hms.addEmployee(emp1);

        /*while (!exit) {
            System.out.println("Hotel Management System");
            System.out.println("1. Add Room");
            System.out.println("2. Display Available Rooms");
            System.out.println("3. Add Booking");
            System.out.println("4. Add Guest");
            System.out.println("5. Add Employee");
            System.out.println("6. Show All Room Details");
            System.out.println("7. Write Room Details to File");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter room type (1: SINGLE, 2: DOUBLE, 3: SUITE): ");
                    int roomTypeInput = scanner.nextInt();
                    RoomType roomType = RoomType.values()[roomTypeInput - 1];
                    System.out.print("Enter room rate: ");
                    double rate = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter amenities (comma separated): ");
                    String[] amenities = scanner.nextLine().split(",");
                    Room room = new Room(roomNumber, roomType, rate, amenities);
                    hms.addRoom(room);
                    System.out.println("Room added successfully.");
                    break;

                case 2:
                    System.out.print("Enter room type to check availability (1: SINGLE, 2: DOUBLE, 3: SUITE): ");
                    roomTypeInput = scanner.nextInt();
                    roomType = RoomType.values()[roomTypeInput - 1];
                    System.out.print("Enter check-in date (dd mm yyyy): ");
                    int checkInDay = scanner.nextInt();
                    int checkInMonth = scanner.nextInt();
                    int checkInYear = scanner.nextInt();
                    Date checkInDate = new Date(checkInYear, checkInMonth - 1, checkInDay);
                    System.out.print("Enter check-out date (dd mm yyyy): ");
                    int checkOutDay = scanner.nextInt();
                    int checkOutMonth = scanner.nextInt();
                    int checkOutYear = scanner.nextInt();
                    Date checkOutDate = new Date(checkOutYear, checkOutMonth - 1, checkOutDay);
                    hms.displayAvailableRooms(roomType, checkInDate, checkOutDate);
                    break;

                case 3:
                    System.out.print("Enter guest name: ");
                    scanner.nextLine(); // Consume newline
                    String guestName = scanner.nextLine();
                    System.out.print("Enter guest address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter guest contact number: ");
                    String contactNumber = scanner.nextLine();
                    Guest guest = new Guest(guestName, address, contactNumber);
                    hms.addGuest(guest);

                    System.out.print("Enter room number for booking: ");
                    roomNumber = scanner.nextInt();
                    Room bookingRoom = hms.getRoomByNumber(roomNumber);
                    if (bookingRoom == null) {
                        System.out.println("Room not found.");
                        break;
                    }
                    System.out.print("Enter booking date (dd mm yyyy): ");
                    int bookingDay = scanner.nextInt();
                    int bookingMonth = scanner.nextInt();
                    int bookingYear = scanner.nextInt();
                    Date bookingDate = new Date(bookingYear, bookingMonth - 1, bookingDay);
                    System.out.print("Enter check-in date (dd mm yyyy): ");
                    checkInDay = scanner.nextInt();
                    checkInMonth = scanner.nextInt();
                    checkInYear = scanner.nextInt();
                    checkInDate = new Date(checkInYear, checkInMonth - 1, checkInDay);
                    System.out.print("Enter check-out date (dd mm yyyy): ");
                    checkOutDay = scanner.nextInt();
                    checkOutMonth = scanner.nextInt();
                    checkOutYear = scanner.nextInt();
                    checkOutDate = new Date(checkOutYear, checkOutMonth - 1, checkOutDay);
                    System.out.print("Enter booking status (1: PENDING, 2: CONFIRMED, 3: CANCELLED): ");
                    int bookingStatusInput = scanner.nextInt();
                    BookingStatus bookingStatus = BookingStatus.values()[bookingStatusInput - 1];
                    System.out.print("Enter payment status: ");
                    scanner.nextLine(); // Consume newline
                    String paymentStatus = scanner.nextLine();
                    Booking booking = new Booking(hms.bookings.size() + 1, guest, bookingRoom, bookingStatus, paymentStatus, bookingDate, checkInDate, checkOutDate);
                    hms.addBooking(booking);
                    System.out.println("Booking added successfully.");
                    break;

                case 4:
                    System.out.print("Enter guest name: ");
                    scanner.nextLine(); // Consume newline
                    guestName = scanner.nextLine();
                    System.out.print("Enter guest address: ");
                    address = scanner.nextLine();
                    System.out.print("Enter guest contact number: ");
                    contactNumber = scanner.nextLine();
                    guest = new Guest(guestName, address, contactNumber);
                    hms.addGuest(guest);
                    System.out.println("Guest added successfully.");
                    break;

                case 5:
                    System.out.print("Enter employee ID: ");
                    int empId = scanner.nextInt();
                    System.out.print("Enter employee name: ");
                    scanner.nextLine(); // Consume newline
                    String empName = scanner.nextLine();
                    System.out.print("Enter employee role: ");
                    String empRole = scanner.nextLine();
                    Employee employee = new Employee(empId, empName, empRole);
                    hms.addEmployee(employee);
                    System.out.println("Employee added successfully.");
                    break;

                case 6:
                    hms.showAllRoomDetails();
                    break;

               /* case 7:
                    Room.writeRoomsDetailsToFile(rooms, "room_details.txt");
                    System.out.println("Room details written to file successfully.");
                    break;

                case 8:
                    exit = true;
                    System.out.println("Exiting system.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();*/
    }
}
