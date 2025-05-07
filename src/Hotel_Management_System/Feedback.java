package Hotel_Management_System;

public class Feedback {
    private final int rating; // Rating out of 5
    private final String comments;

    public Feedback(int rating, String comments) {
        this.rating = rating;
        this.comments = comments;
    }

    public boolean isPositive() {
        return rating >= 4;
    }

    public String getComments() {
        return comments;
    }

    public int getRating() {
        return rating;
    }
}