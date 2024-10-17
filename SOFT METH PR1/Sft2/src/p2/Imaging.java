package p2;


import util.Date;

/**
 * The Imaging class represents an imaging appointment, such as an X-ray,
 * ultrasound, or CAT scan. It extends the Appointment class by adding
 * a radiology room type.
 *
 * @author Siddharth
 */
public class Imaging extends Appointment {
    // Instance variable for the type of radiology room (e.g., X-ray, ultrasound, or CAT scan)
    private Radiology room;

    /**
     * Constructor for creating an Imaging appointment.
     *
     * @param date      the date of the appointment
     * @param timeslot  the timeslot of the appointment
     * @param patient   the patient receiving the imaging service
     * @param provider  the healthcare provider responsible for the imaging service
     * @param room      the type of radiology room (e.g., X-ray, ultrasound, CAT scan)
     */
    public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
        super(date, timeslot, patient, provider);  // Call to Appointment's constructor
        this.room = room;  // Set the specific radiology room
    }

    /**
     * Gets the radiology room type for the imaging appointment.
     *
     * @return the type of radiology room
     */
    public Radiology getRoom() {
        return room;
    }

    /**
     * Sets the radiology room type for the imaging appointment.
     *
     * @param room the type of radiology room to be set
     */
    public void setRoom(Radiology room) {
        this.room = room;
    }

    /**
     * Converts the Imaging appointment's information to a string representation.
     *
     * @return a string containing the appointment details, including the radiology room
     */
    @Override
    public String toString() {
        return super.toString() + " " + room.toString();  // Call the superclass toString and add room info
    }

    /**
     * Checks if two Imaging appointments are equal, including the room type.
     *
     * @param obj the object to compare with
     * @return true if both Imaging appointments are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false; // First check if the superclass fields match
        if (this == obj) return true; // Check if it's the same object
        if (obj == null || getClass() != obj.getClass()) return false; // Null or type check

        Imaging imaging = (Imaging) obj; // Cast the object to Imaging
        return this.room == imaging.room; // Check if the room is the same
    }
}

