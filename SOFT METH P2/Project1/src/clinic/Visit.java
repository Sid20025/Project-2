/**
 *The Visit class is a record of a single appointment or session that a patient has
 *   attended. It stores information about the associated appointment and provides methods
 *   for linking appointments together, effectively creating a linked list of visits for
 *   each patient.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;

public class Visit {

    private Appointment appointment; //a reference to an appointment object
    private Visit next; //a ref. to the next appointment object in the list

    // constructor for visits
    public Visit(Appointment appointment, Visit next){
        this.appointment = appointment;
        this.next = next;
    }
    // constructor for visits with only one appointment
    public Visit(Appointment a){
        this(a, null);
    }
    // getter method for appointment
    public Appointment getAppointment(){
        return appointment;
    }
    // getter method for getting the next visit in the linked list
    public Visit getNext(){
        return next;
    }
    // setter method for setting the next visit in the linked list
    public void setNext(Visit next){
        this.next = next;
    }
    // method to get total charge for patient
    public int getTotalCharge(){
       Appointment a = this.getAppointment();
      int totalCharge = a.getProvider().getSpecialty().getCharge();
      return totalCharge;
    }
    // equals method for checking if two visits are equal
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Visit otherVisit = (Visit) obj;  // Cast to Visit
        return this.appointment.equals(otherVisit.appointment);  // Comparing appointments
    }



}
