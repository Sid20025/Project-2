package p2;


/**
 * The Radiology enum represents different types of radiology rooms that may be used
 * for imaging services, such as CAT Scan, Ultrasound, X-ray, and MRI. This enum
 * includes a user-friendly representation of each radiology service.
 *
 * @author Siddharth
 */
public enum Radiology {

    CATSCAN,    // CAT scan imaging service
    ULTRASOUND, // Ultrasound imaging service
    XRAY,       // X-ray imaging service
    MRI;        // MRI imaging service

    /**
     * Returns a user-friendly string representation of the radiology type.
     *
     * @return a string representation of the radiology type
     */
    @Override
    public String toString() {
        switch (this) {
            case CATSCAN:
                return "CAT Scan";
            case ULTRASOUND:
                return "Ultrasound";
            case XRAY:
                return "X-ray";
            case MRI:
                return "MRI";
            default:
                throw new IllegalArgumentException("Unknown Radiology type: " + this);
        }
    }
}



