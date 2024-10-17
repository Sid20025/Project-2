/**
 *The Timeslot enum defines specific time slots during the day in which appointments
 *   can be scheduled. Each timeslot has a unique identifier and start time. The enum also
 *   includes methods to facilitate comparison between timeslots.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package p2;
/**
 * The Timeslot class represents a specific time during the day for appointments.
 * Each timeslot is defined by an hour and a minute.
 * The class includes methods for comparing, representing, and converting timeslots.
 */
public class Timeslot implements Comparable<Timeslot> {

    // Hour of the timeslot (24-hour format)
    private int hour;

    // Minute of the timeslot
    private int minute;

    /**
     * Constructor to initialize a timeslot with a specific hour and minute.
     *
     * @param hour   The hour of the timeslot (in 24-hour format).
     * @param minute The minute of the timeslot.
     */
    public Timeslot(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Gets the hour of the timeslot.
     *
     * @return The hour of the timeslot.
     */
    public int getHour() {
        return hour;
    }

    /**
     * Gets the minute of the timeslot.
     *
     * @return The minute of the timeslot.
     */
    public int getMinute() {
        return minute;
    }

    /**
     * Compares this timeslot with another timeslot to determine their order.
     * Comparison is done first by hour and then by minute.
     *
     * @param other The other timeslot to compare against.
     * @return 1 if this timeslot is after the other, -1 if before, and 0 if they are equal.
     */
    @Override
    public int compareTo(Timeslot other) {
        if (this.hour > other.hour) {
            return 1; // Compare by hour first
        }
        if (this.hour < other.hour) {
            return -1;
        }
        if (this.minute > other.minute) {
            return 1;
        }
        if (this.minute < other.minute) {
            return -1;
        }
        return 0; // Compare by minute if the hours are equal
    }

    /**
     * Checks if two timeslots are equal by comparing both the hour and the minute.
     *
     * @param obj The object to compare against.
     * @return True if the timeslots have the same hour and minute, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if both objects are the same
        if (obj == null || getClass() != obj.getClass()) return false; // Null or class type check
        Timeslot timeslot = (Timeslot) obj; // Cast the object to Timeslot
        return hour == timeslot.hour && minute == timeslot.minute; // Compare hour and minute
    }

    /**
     * Returns a string representation of the timeslot in a readable 12-hour format.
     *
     * @return A formatted string representing the timeslot (e.g., "09:00 AM").
     */
    @Override
    public String toString() {
        String period = (hour >= 12) ? "PM" : "AM"; // Determine AM or PM
        int displayHour = (hour % 12 == 0) ? 12 : hour % 12; // Convert hour to 12-hour format
        return String.format("%02d:%02d %s", displayHour, minute, period); // Format the time
    }

    /**
     * Creates a timeslot from a given number representing a specific timeslot in the day.
     * Useful for quick conversions from an index or enumeration to an actual timeslot.
     *
     * @param number The number representing the timeslot (e.g., 1 for 9:00 AM).
     * @return The Timeslot corresponding to the given number.
     * @throws IllegalArgumentException If the number does not correspond to a valid timeslot.
     */
    public static Timeslot fromNumber(int number) {
        switch (number) {
            case 1:
                return new Timeslot(9, 0);  // 9:00 AM
            case 2:
                return new Timeslot(9, 30); // 9:30 AM
            case 3:
                return new Timeslot(10, 0); // 10:00 AM
            case 4:
                return new Timeslot(10, 30); // 10:30 AM
            case 5:
                return new Timeslot(11, 0); // 11:00 AM
            case 6:
                return new Timeslot(11, 30); // 11:30 AM
            case 7:
                return new Timeslot(14, 0); // 2:00 PM
            case 8:
                return new Timeslot(14, 30); // 2:30 PM
            case 9:
                return new Timeslot(15, 0); // 3:00 PM
            case 10:
                return new Timeslot(15, 30); // 3:30 PM
            case 11:
                return new Timeslot(16, 0); // 4:00 PM
            case 12:
                return new Timeslot(16, 30); // 4:30 PM
            default:
                throw new IllegalArgumentException("Invalid timeslot number: " + number); // Handle invalid input
        }
    }
}
