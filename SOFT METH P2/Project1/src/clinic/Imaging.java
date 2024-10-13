package clinic;


    public class Imaging extends Appointment {
        // Instance variable for the type of radiology room (X-ray, ultrasound, or CAT scan)
        private Radiology room;

        // Constructor for Imaging, calling the superclass constructor
        public Imaging(Date date, Timeslot timeslot, Person patient, Person provider, Radiology room) {
            super(date, timeslot, patient, provider);  // Call to Appointment's constructor
            this.room = room;  // Set the specific radiology room
        }

        // Getter for the radiology room
        public Radiology getRoom() {
            return room;
        }

        // Setter for the radiology room (optional, if you need to modify the room after creation)
        public void setRoom(Radiology room) {
            this.room = room;
        }

        // Overriding the toString method to include the room information
        @Override
        public String toString() {
            return super.toString() + " " + room.toString();  // Call the superclass toString and add room info
        }

        // Overriding equals method to compare Imaging objects, including the room field
        @Override
        public boolean equals(Object obj) {
            if (!super.equals(obj)) return false; // First check if the superclass fields match
            if (this == obj) return true; // Check if it's the same object
            if (obj == null || getClass() != obj.getClass()) return false; // Null or type check

            Imaging imaging = (Imaging) obj; // Cast the object to Imaging
            return this.room == imaging.room; // Check if the room is the same
        }
    }


