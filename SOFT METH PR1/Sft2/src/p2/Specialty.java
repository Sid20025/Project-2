/**
 *The Specialty enum defines different medical specialties, such as FAMILY,
 *  PEDIATRICIAN, and ALLERGIST. Each specialty is associated with a specific charge rate
 *  that can be used for billing purposes. The enum also includes methods for retrieving
 *  the charge and printing the name of the specialty.
 *
 * @author Siddharth Aggarwal, Michael Durugo
 */
package p2;

/**
 * The Specialty enum represents different medical specialties that a provider may have,
 * along with their corresponding charges. This includes specialties such as FAMILY,
 * PEDIATRICIAN, and ALLERGIST.
 * Each specialty has a fixed charge associated with it.
 *
 * @author Siddharth, Michael Durugo
 */
public enum Specialty {

    FAMILY(250),         // Family medicine specialty with a charge of 250
    PEDIATRICIAN(300),   // Pediatrician specialty with a charge of 300
    ALLERGIST(350);      // Allergist specialty with a charge of 350

    // Fields
    private final int charge;  // Charge associated with the specialty

    /**
     * Constructor to create a Specialty with the given charge.
     *
     * @param charge the charge for the specialty
     */
    Specialty(int charge) {
        this.charge = charge;
    }

    /**
     * Gets the charge associated with the specialty.
     *
     * @return the charge for the specialty
     */
    public int getCharge() {
        return charge;
    }

    /**
     * Returns a string representation of the specialty.
     *
     * @return the name of the specialty
     */
    @Override
    public String toString() {
        return this.name();
    }
}
