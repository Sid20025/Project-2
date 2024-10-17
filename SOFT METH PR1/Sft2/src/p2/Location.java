/**
 *The Location enum represents different geographic locations or regions, such as
 *   counties, cities, or clinics, where appointments can take place. Each location
 *   is associated with a name, and this enum helps in organizing and sorting appointments
 *   based on where they occur. It supports comparison and retrieval of location data
 *   to assist with filtering or ordering appointments by location.
 *
 * @author Siddharth Aggarwal, Michael Durugo
 */
package p2;

import util.Date;
import util.List;

/**
 * The Location enum represents different clinic locations.
 * Each location includes the county it belongs to and its ZIP code.
 * It also provides a method to determine if a specific radiology room is available
 * at a given timeslot and date for the respective location.
 *
 * @author Siddharth
 */
public enum Location {

    // Enumeration constants representing different locations, each with a county and ZIP code
    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    // Fields
    private final String county;  // The county of the location
    private final String zip;     // The ZIP code of the location

    /**
     * Constructor to create a Location instance with the specified county and ZIP code.
     *
     * @param county the county of the location
     * @param zip    the ZIP code of the location
     */
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    /**
     * Determines if a radiology room is available at this location for a given date and timeslot.
     *
     * @param apptDate    the date of the appointment
     * @param timeslot    the timeslot of the appointment
     * @param radiology   the type of radiology room required (e.g., X-ray, ultrasound)
     * @param appointments the list of existing appointments
     * @return true if the radiology room is available, false otherwise
     */
    public boolean roomAvailable(Date apptDate, Timeslot timeslot, Radiology radiology, List<Appointment> appointments) {
        for (Appointment appointment : appointments) {
            if (appointment instanceof Imaging imaging) {
                if (!appointment.getDate().equals(apptDate)) continue;
                if (!appointment.getTimeslot().equals(timeslot)) continue;
                Location aptLocation = ((Provider) (appointment.getProvider())).getLocation();
                if (this.equals(aptLocation) && imaging.getRoom().equals(radiology)) return false;
            }
        }
        return true;
    }

    /**
     * Gets the county name of the location.
     *
     * @return the county name of the location
     */
    public String getCounty() {
        return county;
    }

    /**
     * Gets the ZIP code of the location.
     *
     * @return the ZIP code of the location
     */
    public String getZip() {
        return zip;
    }

    /**
     * Converts the location details to a user-friendly string representation.
     *
     * @return a string containing the location name, county, and ZIP code
     */
    @Override
    public String toString() {
        return String.format("%s, %s %s", name(), county, zip);
    }
}
