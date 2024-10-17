
package p2;

import util.Date;

/**
 * The Appointment class represents a scheduled appointment, including the date,
 * timeslot, patient, and the provider responsible for the service. The class supports
 * comparisons between appointments to check for scheduling conflicts, and it also
 * provides methods for determining if a particular timeslot is available.
 *
 * @author Siddharth Aggarwal, Michael Durugo
 */
public class Appointment implements Comparable<Appointment> {

    // Instance variables representing the date, timeslot, patient, and provider for the appointment
    protected Date date;
    protected Timeslot timeslot;
    protected Person patient;   // Changed from Profile to Person to hold detailed patient information
    protected Person provider;  // Changed from Provider to Person to hold detailed provider information

    /**
     * Constructor for Appointment.
     *
     * @param date the date of the appointment
     * @param timeslot the timeslot for the appointment
     * @param patient the patient involved in the appointment
     * @param provider the provider responsible for the appointment
     */
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    /**
     * Compares two appointments based on date, timeslot, provider, and patient.
     *
     * @param b the appointment to be compared with
     * @return a negative integer, zero, or a positive integer as this appointment
     *         is less than, equal to, or greater than the specified appointment
     */
    @Override
    public int compareTo(Appointment b) {
        int dateComparison = this.date.compareTo(b.getDate());
        if (dateComparison != 0) {
            return dateComparison;  // If dates are different, return the comparison result
        }

        // Compare by timeslot if dates are the same
        int timeslotComparison = this.timeslot.compareTo(b.getTimeslot());
        if (timeslotComparison != 0) {
            return timeslotComparison;  // If timeslots are different, return the comparison result
        }

        // Compare by provider if both the date and timeslot are the same
        int providerComparison = this.provider.getProfile().compareTo(b.getProvider().getProfile());
        if (providerComparison != 0) {
            return providerComparison;  // If providers are different, return the comparison result
        }

        // Compare by patient if all else is equal
        return this.patient.getProfile().compareTo(b.getPatient().getProfile());
    }

    /**
     * Gets the patient associated with this appointment.
     *
     * @return the patient
     */
    public Person getPatient() {
        return patient;
    }

    /**
     * Changes the timeslot of the appointment.
     *
     * @param newTimeslot the new timeslot to be assigned
     */
    public void changeTimeslot(Timeslot newTimeslot) {
        this.timeslot = newTimeslot;
    }

    /**
     * Gets the timeslot of the appointment.
     *
     * @return the timeslot
     */
    public Timeslot getTimeslot() {
        return timeslot;
    }

    /**
     * Gets the date of the appointment.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the provider associated with this appointment.
     *
     * @return the provider
     */
    public Person getProvider() {
        return provider;
    }

    /**
     * Gets the location of the provider for this appointment.
     *
     * @return the provider's location
     * @throws IllegalStateException if the provider is not of type Provider
     */
    public Location getProviderLocation() {
        if (provider instanceof Provider) {
            Provider actualProvider = (Provider) provider;
            return actualProvider.getLocation();
        }
        throw new IllegalStateException("Provider is not of type Provider.");
    }

    /**
     * Checks if two appointments are equal based on date, timeslot, patient, and provider.
     *
     * @param o the object to be compared with
     * @return true if the appointments are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Appointment appointment) {
            return this.date.equals(appointment.date)
                    && this.timeslot.equals(appointment.timeslot)
                    && this.patient.equals(appointment.patient)
                    && this.provider.equals(appointment.provider);
        }
        return false;
    }

    /**
     * Converts the appointment to a String representation.
     *
     * @return a string containing the date, timeslot, patient, and provider information
     */
    @Override
    public String toString() {
        return date.toString() + " " + timeslot.toString() + " "
                + patient.getProfile().toString() + " "
                + provider.getProfile().toString();
    }
}


