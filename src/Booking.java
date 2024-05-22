import java.util.List;

enum booking{
    ATTHESPOT,ADVANCE
}

enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED
}

class Booking extends Room{
    private int bookingId;
    private Guest guest;
    private Room room;
    private BookingStatus bookingStatus;
    private String paymentStatus;
    private Date bookingDate;
    private Date checkInDate;
    private Date checkOutDate;


    public Booking(int roomNumber, RoomType roomType, RoomStatus roomStatus, double rate, int floor, List<String> amenities, int bookingId, Guest guest, Room room, BookingStatus bookingStatus, String paymentStatus, Date bookingDate, Date checkInDate, Date checkOutDate) {
        super(roomNumber, roomType, roomStatus, rate, floor, amenities);
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.bookingStatus = bookingStatus;
        this.paymentStatus = paymentStatus;
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

}
