/**
 *The Patient class represents a person receiving services, tracked by their profile
 *  and a linked list of visits (appointments). It includes methods to calculate the
 *  total charge based on the patient's visits and comparison methods to facilitate
 *   ordering and sorting of patient records.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;

    public class Patient implements Comparable<Patient> {
        private Profile profile;
        private Visit visits; //a linked list of visits (completed appt.)

        // constructor method for patient
        public Patient(Profile p, Visit visits){
            this.profile = p;
            this.visits = visits;
        }
        // constructor method without any visits
        public Patient(Profile p){
            this.profile = p;
        }

        // getter method for profile
        public Profile getProfile(){
            return profile;
        }
        // getter method for visits
        public Visit getVisits(){
            return visits;
        }
        // method for adding new visit
        public void addVisit(Visit visit){
            if(visits == null){
                this.visits = visit;
                return;
            }
           Visit current = this.visits;
            while(current != null){
                if(current.getNext() == null){
                    current.setNext(visit);
                    return;
                }
                current = current.getNext();
            }
        }
        // method for removing a visit, which returns a boolean value
        public boolean removeVisit(Visit visitToRemove){
            if(visits == null){
                return false;
            }
            if(visits.equals(visitToRemove)){
                visits = visits.getNext();
                return true;
            }
            Visit current = visits;
            while (current.getNext() != null) {
                if (current.getNext().equals(visitToRemove)) {
                    current.setNext(current.getNext().getNext());  // Remove the visit
                    return true;
                }
                current = current.getNext();
            }
            return false;  // Visit not found

        }
        // method to compare two patients
        @Override
        public int compareTo(Patient q){
            return this.getProfile().compareTo(q.getProfile());
        }

        // method to get the final total charge of the patient
        public int finalCharge(){
            int finalCharge = 0;
            Visit currentVisit = visits;  // Start with the first visit (head of the linked list)

            // Traverse the linked list of visits
            while (currentVisit != null) {
                finalCharge += currentVisit.getTotalCharge();  // Add the charge for the current visit
                currentVisit = currentVisit.getNext();    // Move to the next visit in the list
            }

            return finalCharge;  // R
        }


    }

