package Hotel_Management_System;

class Payment {
    private int paymentId;
    private Booking booking;
    private double amount;
    private String paymentMethod;

    public Payment(int paymentId, Booking booking, double amount, String paymentMethod) {
        this.paymentId = paymentId;
        this.booking = booking;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
