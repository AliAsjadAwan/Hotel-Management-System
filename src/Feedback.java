public class Feedback {
    private int rating; // Rating out of 5
    private String comments;

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