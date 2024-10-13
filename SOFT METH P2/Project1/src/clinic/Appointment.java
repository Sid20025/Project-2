/**
 *The Appointment class represents a scheduled appointment, including the date,
 *   timeslot, patient, and the provider responsible for the service. The class supports
 *   comparisons between appointments to check for scheduling conflicts, and it also
 *   provides methods for determining if a particular timeslot is available.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;

public class Appointment implements Comparable<Appointment> {
    // Changed the instance variables for patient and provider to Person
    protected Date date;
    protected Timeslot timeslot;
    protected Person patient;   // Changed from Profile to Person
    protected Person provider;  // Changed from Provider to Person

    // Constructor for Appointment
    public Appointment(Date date, Timeslot timeslot, Person patient, Person provider) {
        this.date = date;
        this.timeslot = timeslot;
        this.patient = patient;
        this.provider = provider;
    }

    // Comparing two appointments based on date, timeslot, provider, and patient
    @Override
    public int compareTo(Appointment b) {
        int dateComparison = this.date.compareTo(b.getDate());
        if (dateComparison != 0) {
            return dateComparison;  // If dates are different, return the result
        }

        // Compare by timeslot if the dates are the same
        int timeslotComparison = this.timeslot.compareTo(b.getTimeslot());
        if (timeslotComparison != 0) {
            return timeslotComparison;  // If timeslots are different, return the result
        }

        // Compare by provider if both the date and timeslot are the same
        int providerComparison = this.provider.getProfile().compareTo(b.getProvider().getProfile());
        if (providerComparison != 0) {
            return providerComparison;
        }

        // Compare by patient if all else is equal
        return this.patient.getProfile().compareTo(b.getPatient().getProfile());
    }

    // Getter methods for patient, timeslot, date, and provider
    public Person getPatient() {
        return patient;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public Date getDate() {
        return date;
    }

    public Person getProvider() {
        return provider;
    }

    // Checking if two appointments are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check if the same object
        if (obj == null || getClass() != obj.getClass()) return false; // Null check and type check

        Appointment b = (Appointment) obj; // Casting to Appointment

        return this.date.equals(b.date) &&
                this.timeslot.equals(b.timeslot) &&
                this.patient.equals(b.patient) &&
                this.provider.equals(b.provider);
    }

    // Converting an appointment to String format
    @Override
    public String toString() {
        return date.toString() + " " + timeslot.toString() + " " + patient.getProfile().toString() + " " + provider.getProfile().toString();
    }
}



