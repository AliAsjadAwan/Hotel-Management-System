package Hotel_Management_System;

public class Booking {
    private final Room room;
    private final int bookingId;
    private final Guest guest;
    private final int bookingDays;

    public Booking(int bookingId, Guest guest, Room room, int bookingDays) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.bookingDays = bookingDays;
    }

    public Room getRoom() {
        return room;
    }

    public int getBookingId() {
        return bookingId;
    }

    public Guest getGuest() {
        return guest;
    }

    public int getBookingDays() {
        return bookingDays;
    }
}