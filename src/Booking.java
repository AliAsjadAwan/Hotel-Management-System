

import java.util.Date;
import java.util.List;


class Booking{
    private  Room room;
    private int bookingId;
    private Guest guest;
    private int Bookingdays;


    private Date bookingDate;

    public Booking( int bookingId, Guest guest, Room room,int Bookingdays) {

        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;

        this.Bookingdays=Bookingdays;
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



    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getBookingdays() {
        return Bookingdays;
    }

    public void setBookingdays(int bookingdays) {
        Bookingdays = bookingdays;
    }
}