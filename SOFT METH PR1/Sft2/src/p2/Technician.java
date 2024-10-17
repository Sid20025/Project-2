package p2;

/**
 * The Technician class represents a healthcare provider who performs imaging and other technical services.
 * This class extends the abstract Provider class, and includes information specific to technicians,
 * such as the rate per visit.
 */
public class Technician extends Provider {

    // Rate charged per visit by the technician
    private int ratePerVisit;

    /**
     * Constructs a Technician instance with the provided profile, location, and rate per visit.
     *
     * @param p            The profile of the technician (name and DOB).
     * @param location     The location where the technician works.
     * @param ratePerVisit The rate per visit charged by the technician.
     */
    public Technician(Profile p, Location location, int ratePerVisit) {
        super(p, location); // Calling the superclass (Provider) constructor
        this.ratePerVisit = ratePerVisit; // Setting the rate per visit
    }

    /**
     * Returns the rate per visit for the technician.
     *
     * @return The rate charged per visit.
     */
    @Override
    public int rate() {
        return ratePerVisit;
    }

    /**
     * Returns a string representation of the Technician, including the profile,
     * location, and rate per visit.
     *
     * @return A string representation of the Technician.
     */
    @Override
    public String toString() {
        return String.format("Technician: %s, Location: %s, Rate per visit: %d", getProfile().toString(), getLocation().toString(), ratePerVisit);
    }
}
