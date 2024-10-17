package p2;


/**
 * The Doctor class represents a healthcare provider with a specialty.
 * It extends the Provider class, adding fields for the doctor's specialty
 * and National Provider Identifier (NPI).
 *
 * @author Siddharth
 */
public class Doctor extends Provider {
    private Specialty specialty; // The doctor's specialty
    private String npi;           // The doctor's National Provider Identifier (NPI)

    /**
     * Constructor for creating a Doctor with a profile, location, specialty, and NPI.
     *
     * @param p        the profile of the doctor (including first name, last name, and date of birth)
     * @param location the location where the doctor works
     * @param specialty the doctor's medical specialty
     * @param npi      the National Provider Identifier for the doctor
     */
    public Doctor(Profile p, Location location, Specialty specialty, String npi) {
        super(p, location);
        this.specialty = specialty;
        this.npi = npi;
    }

    /**
     * Constructor for creating a Doctor with only a profile and location.
     *
     * @param p        the profile of the doctor (including first name, last name, and date of birth)
     * @param location the location where the doctor works
     */
    public Doctor(Profile p, Location location) {
        super(p, location);
    }

    /**
     * Gets the specialty of the doctor.
     *
     * @return the specialty of the doctor
     */
    public Specialty getSpecialty() {
        return specialty;
    }

    /**
     * Gets the National Provider Identifier (NPI) of the doctor.
     *
     * @return the NPI of the doctor
     */
    public String getNpi() {
        return npi;
    }

    /**
     * Calculates the rate for the doctor based on their specialty.
     *
     * @return the rate (charge) for the doctor's specialty
     */
    @Override
    public int rate() {
        return specialty.getCharge();
    }

    /**
     * Converts the doctor's information to a string representation.
     *
     * @return a string containing the doctor's profile, location, specialty, and NPI
     */
    @Override
    public String toString() {
        return String.format("[%s, %s, %s, NPI: %s]", getProfile().toString(), getLocation().toString(), specialty.toString(), npi);
    }
}

