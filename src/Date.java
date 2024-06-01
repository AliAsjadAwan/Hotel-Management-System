import java.text.ParseException;

public class Date {
    private int day;
    private int month;
    private int year;

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
        if (other == null) {
            return false;
        }
        if (this.year < other.year) return true;
        if (this.year == other.year && this.month < other.month) return true;
        if (this.year == other.year && this.month == other.month && this.day < other.day) return true;
        return false;
    }

    public boolean isAfter(Date other) {
        if (other == null) {
            return false;
        }
        if (this.year > other.year) return true;
        if (this.year == other.year && this.month > other.month) return true;
        if (this.year == other.year && this.month == other.month && this.day > other.day) return true;
        return false;
    }

    public boolean isEqual(Date other) {
        if (other == null) {
            return false;
        }
        return this.year == other.year && this.month == other.month && this.day == other.day;
    }

    public static Date parse(String dateString) throws ParseException {
        if (dateString == null || dateString.isEmpty()) {
            throw new ParseException("Empty or null string", 0);
        }

        String[] parts = dateString.split("-");
        if (parts.length != 3) {
            throw new ParseException("Invalid date format: " + dateString, 0);
        }
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);
        return new Date(day, month, year);
    }
}