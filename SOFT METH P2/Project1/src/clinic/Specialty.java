/**
 *The Specialty enum defines different medical specialties, such as FAMILY,
 *  PEDIATRICIAN, and ALLERGIST. Each specialty is associated with a specific charge rate
 *  that can be used for billing purposes. The enum also includes methods for retrieving
 *  the charge and printing the name of the specialty.
 *
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;

public enum Specialty {

    FAMILY(250),
    PEDIATRICIAN(300),
    ALLERGIST(350);

    // Fields
    private final int charge;

    // Constructor
    Specialty(int charge) {
        this.charge = charge;
    }

    // Getter method
    public int getCharge() {
        return charge;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
