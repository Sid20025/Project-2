package clinic;


    public enum Radiology {
        CATSCAN,    // CAT scan imaging service
        ULTRASOUND, // Ultrasound imaging service
        XRAY;       // X-ray imaging service

        // Overriding toString() to return a user-friendly representation of the enum
        @Override
        public String toString() {
            switch (this) {
                case CATSCAN:
                    return "CAT Scan";
                case ULTRASOUND:
                    return "Ultrasound";
                case XRAY:
                    return "X-ray";
                default:
                    throw new IllegalArgumentException("Unknown Radiology type: " + this);
            }
        }
    }


