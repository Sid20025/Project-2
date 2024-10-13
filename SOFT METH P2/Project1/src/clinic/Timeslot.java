/**
 *The Timeslot enum defines specific time slots during the day in which appointments
 *   can be scheduled. Each timeslot has a unique identifier and start time. The enum also
 *   includes methods to facilitate comparison between timeslots.
 * @author Siddharth Aggarwal, Michael Durugo
 */
public class Timeslot implements Comparable<Timeslot> {
    // Instance variables (private as per the requirement)
    private int hour;
    private int minute;

    // Constructor to initialize hour and minute
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // Getter methods
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    // Method to compare timeslots based on hour and minute
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour != other.hour) {
            return this.hour - other.hour; // Compare by hour first
        }
        return this.minute - other.minute; // Compare by minute if the hours are equal
    }

    // Overriding equals() method for comparing two Timeslot objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Timeslot timeslot = (Timeslot) obj;
        return hour == timeslot.hour && minute == timeslot.minute;
    }

    // Overriding toString() to display the time in a readable format
    @Override
    public String toString() {
        String period = (hour >= 12) ? "PM" : "AM";
        int displayHour = (hour % 12 == 0) ? 12 : hour % 12;
        return String.format("%02d:%02d %s", displayHour, minute, period);
    }

    // Static method to create Timeslot from a number (if necessary)
    // Adjust as needed for your specific use case
    public static Timeslot fromNumber(int number) {
        switch (number) {
            case 1:
                return new Timeslot(9, 0);  // 9:00 AM
            case 2:
                return new Timeslot(10, 45); // 10:45 AM
            case 3:
                return new Timeslot(11, 15); // 11:15 AM
            case 4:
                return new Timeslot(13, 30); // 1:30 PM
            case 5:
                return new Timeslot(15, 0);  // 3:00 PM
            case 6:
                return new Timeslot(16, 15); // 4:15 PM
            default:
                throw new IllegalArgumentException("Invalid timeslot number: " + number);
        }
    }
}
