/**
 *The Location enum represents different geographic locations or regions, such as
 *   counties, cities, or clinics, where appointments can take place. Each location
 *   is associated with a name, and this enum helps in organizing and sorting appointments
 *   based on where they occur. It supports comparison and retrieval of location data
 *   to assist with filtering or ordering appointments by location.
 *
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;

public enum Location {

    BRIDGEWATER("Somerset", "08807"),
    EDISON("Middlesex", "08817"),
    PISCATAWAY("Middlesex", "08854"),
    PRINCETON("Mercer", "08542"),
    MORRISTOWN("Morris", "07960"),
    CLARK("Union", "07066");

    // Fields
    private final String county;
    private final String zip;

    // Constructor
    Location(String county, String zip) {
        this.county = county;
        this.zip = zip;
    }

    // Getter methods
    public String getCounty() {
        return county;
    }

    public String getZip() {
        return zip;
    }

    @Override
    public String toString() {
        return String.format("%s, %s %s", name(), county, zip);
    }
}
