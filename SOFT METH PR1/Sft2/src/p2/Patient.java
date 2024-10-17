/**
 *The Patient class represents a person receiving services, tracked by their profile
 *  and a linked list of visits (appointments). It includes methods to calculate the
 *  total charge based on the patient's visits and comparison methods to facilitate
 *   ordering and sorting of patient records.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package p2;

/**
 * The Patient class represents a person receiving services at the clinic.
 * It is tracked by their profile and a linked list of visits (appointments).
 * It includes methods for managing visits, such as adding or removing a visit,
 * and calculating the total charge for all visits.
 *
 * @author Siddharth
 */
public class Patient extends Person {

    private Visit visits; // A linked list of visits (completed appointments)

    /**
     * Constructor to create a Patient instance with no visits.
     *
     * @param p the profile of the patient
     */
    public Patient(Profile p) {
        super(p);
        this.visits = null;
    }

    /**
     * Constructor to create a Patient instance with initial visits.
     *
     * @param p the profile of the patient
     * @param visits the linked list of visits associated with the patient
     */
    public Patient(Profile p, Visit visits) {
        super(p);
        this.visits = visits;
    }

    /**
     * Gets the linked list of visits associated with the patient.
     *
     * @return the linked list of visits for this patient
     */
    public Visit getVisits() {
        return visits;
    }

    /**
     * Adds a new visit to the patient's linked list of visits.
     * If there are no existing visits, it sets the provided visit as the first.
     *
     * @param visit the visit to be added to the list of visits
     */
    public void addVisit(Visit visit) {
        if (visits == null) {
            this.visits = visit;
            return;
        }
        Visit current = this.visits;
        while (current != null) {
            if (current.getNext() == null) {
                current.setNext(visit);
                return;
            }
            current = current.getNext();
        }
    }

    /**
     * Removes a visit from the patient's linked list of visits.
     *
     * @param visitToRemove the visit to be removed
     * @return true if the visit was found and removed, false otherwise
     */
    public boolean removeVisit(Visit visitToRemove) {
        if (visits == null) {
            return false;  // No visits to remove
        }
        if (visits.equals(visitToRemove)) {
            visits = visits.getNext();  // Remove the first visit
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



}
