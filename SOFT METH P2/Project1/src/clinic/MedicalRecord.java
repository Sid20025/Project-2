/**
 *The MedicalRecord class manages a list of patients and their corresponding medical
 *  history. It supports operations such as adding patients, sorting the list of patients
 *  by their profile, and printing out billing statements. This class is integral to
 *  organizing and maintaining patient records in a medical scheduling system.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;

public class MedicalRecord {
    private Patient[] patients;
    private int size; //number of patient objects in the array

    // constructor for medical record
    public MedicalRecord(){
        this.patients = new Patient[4];
        this.size = 0;
    }
    // adding a new element to the medical record
    public void add(Patient p){
        if(patients.length == size){
            grow();
        }
       patients[size] = p;
       size++;
    }
    // helper method to grow the list size by 4
    private void grow(){

            Patient[] newPatients = new Patient[size + 4];
            for(int i = 0; i < size; i++){
                newPatients[i] = patients[i];
            }


        patients = newPatients;
    }
    // sorting method to sort the list by patients
    public void sort(){
        for(int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                // Compare the profiles of adjacent patients
                if (patients[j].getProfile().compareTo(patients[j + 1].getProfile()) > 0) {
                    // Swap patients[j] and patients[j + 1] if they are out of order
                    Patient temp = patients[j];
                    patients[j] = patients[j + 1];
                    patients[j + 1] = temp;
                }
            }
        }
    }

    // getter method for patient
    public Patient[] getPatients() {
        return patients;
    }
    // getter method for size
    public int getSize() {
        return size;
    }

    // finder method for finding patient by name and date of birth
    public Patient findPatientByName(String patientFirstName, String patientLastName, Date dob){
        for(int i = 0; i < size; i++){
            Patient q = patients[i];
            String patientName = patientFirstName + " " + patientLastName;
          Profile profile  = q.getProfile();
          String fullName = profile.getFname() + " " + profile.getLname();
          Date dobCheck = profile.getDob();
            if (fullName.equalsIgnoreCase(patientName) && dobCheck.equals(dob)) {

                    return q;  // Return the matching patient
            }
        }

        // If no match is found, return null
        return null;
        }



}
