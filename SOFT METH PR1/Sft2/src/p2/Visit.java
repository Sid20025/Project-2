/**
 *The Visit class is a record of a single appointment or session that a patient has
 *   attended. It stores information about the associated appointment and provides methods
 *   for linking appointments together, effectively creating a linked list of visits for
 *   each patient.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package p2;

/**
 * The Visit class represents a patient's visit, which is linked to an appointment.
 * It is part of a linked list structure where each visit has a reference to the next one.
 */
public class Visit {

    // A reference to an appointment object associated with this visit
    private Appointment appointment;

    // A reference to the next visit object in the linked list
    private Visit next;

    /**
     * Constructor for creating a Visit with an appointment and the next Visit in the list.
     *
     * @param appointment The appointment associated with this visit.
     * @param next        The next visit in the linked list.
     */
    public Visit(Appointment appointment, Visit next) {
        this.appointment = appointment;
        this.next = next;
    }

    /**
     * Constructor for creating a Visit with only one appointment.
     *
     * @param a The appointment associated with this visit.
     */
    public Visit(Appointment a) {
        this(a, null); // Set next as null if no next visit is specified
    }

    /**
     * Getter method for retrieving the appointment of this visit.
     *
     * @return The appointment associated with this visit.
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * Getter method for retrieving the next visit in the linked list.
     *
     * @return The next visit in the linked list, or null if it is the last visit.
     */
    public Visit getNext() {
        return next;
    }

    /**
     * Setter method for setting the next visit in the linked list.
     *
     * @param next The next visit to set in the linked list.
     */
    public void setNext(Visit next) {
        this.next = next;
    }

    /**
     * Equals method to compare if two Visit objects are equal.
     * Two visits are considered equal if their associated appointments are the same.
     *
     * @param obj The object to compare against.
     * @return True if the visits are equal, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // If comparing to itself, return true
        if (obj == null || getClass() != obj.getClass()) return false; // Null or type check

        Visit otherVisit = (Visit) obj; // Cast the object to Visit
        return this.appointment.equals(otherVisit.appointment); // Compare appointments for equality
    }
}

