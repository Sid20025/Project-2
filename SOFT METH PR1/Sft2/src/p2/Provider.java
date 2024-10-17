
package p2;

import util.Date;
import util.List;

/**
 *The Provider enum represents healthcare providers in the scheduling system, such as
 *   doctors or specialists. Each provider is associated with a specific name, and this
 *   enum allows for easy management and comparison of providers when scheduling
 *   appointments. It also supports comparisons to check for provider availability in
 *   specific time slots.
 * @author Siddharth Aggarwal, Michael Durugo
 */
public abstract class Provider extends Person {

    // Instance variable for the location of the provider
    private Location location;

    /**
     * Constructor to create a Provider instance with a profile and location.
     *
     * @param p       the profile of the provider
     * @param location the location where the provider works
     */
    public Provider(Profile p, Location location) {
        super(p);
        this.location = location;
    }

    /**
     * Gets the location of the provider.
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Checks if the provider is available at a specified date and timeslot.
     *
     * @param apptDate the date of the appointment to check
     * @param timeslot the timeslot of the appointment to check
     * @param appList  the list of appointments to compare against
     * @return true if the provider is available, otherwise false
     */
    public boolean isAvailableAt(Date apptDate, Timeslot timeslot, List<Appointment> appList) {
        for (Appointment appt : appList) {
            if (appt.getProvider().equals(this)
                    && appt.getTimeslot().equals(timeslot)
                    && appt.getDate().equals(apptDate)) {
                return false; // Provider is not available if they already have an appointment at this timeslot
            }
        }
        return true; // Provider is available
    }

    /**
     * Compares this provider with another object for equality based on their profile and location.
     *
     * @param o the object to compare with this provider
     * @return true if both objects have the same profile and location, otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Provider provider) {
            return super.equals(provider) && this.location.equals(provider.location);
        }
        return false;
    }

    /**
     * Abstract method to get the rate of the provider. Must be implemented by subclasses.
     *
     * @return the rate for the provider's service
     */
    public abstract int rate();

    /**
     * Converts the provider information to a string representation.
     *
     * @return a string containing the profile and location of the provider
     */
    @Override
    public String toString() {
        return String.format("%s, %s", super.toString(), location);
    }
}
