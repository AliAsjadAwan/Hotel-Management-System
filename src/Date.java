public class Date {
    private int day;
    private int month;
    private int year;

    public Date() {
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isBefore(Date other) {
        if (this.year < other.year) return true;
        if (this.year == other.year && this.month < other.month) return true;
        if (this.year == other.year && this.month == other.month && this.day < other.day) return true;
        return false;
    }

    public boolean isAfter(Date other) {
        if (this.year > other.year) return true;
        if (this.year == other.year && this.month > other.month) return true;
        if (this.year == other.year && this.month == other.month && this.day > other.day) return true;
        return false;
    }

    public boolean isEqual(Date other) {
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }
}
