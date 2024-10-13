/**
 *The Scheduler class handles the core functionality of processing commands,
 *   scheduling appointments, checking for conflicts, and managing medical records. It
 *   acts as an interface between the user and the underlying data, continuously processing
 *   input commands until termination.
 *
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;
import java.util.Scanner;

public class Scheduler {
    private List list;
    private MedicalRecord medicalRecord;
    private String[] commandParts;

// Constructor for scheduler
    public Scheduler(){
        this.list = new List();
        this.medicalRecord =  new MedicalRecord();
        this.commandParts = new String[7];

    }

    // Run method to process command lines
    public void run() {
        System.out.println("Scheduler is running.");
        Scanner scanner = new Scanner(System.in);

        // Continuously processing commands until "Q" is entered
        while (true) {
            String commandLine = scanner.nextLine().trim();  // Reading input and trimming extra spaces

            if (commandLine.equals("Q")) {
                System.out.println("Scheduler terminated");
                break;  // Exiting the loop and terminate the program
            } else if (!commandLine.isEmpty()) {
                processCommand(commandLine);  // Handling non-empty command lines
            }
        }

        scanner.close();  // Closing the scanner when done
    }

    public void processCommand(String commandLine) {
        // Splitting the command into parts
         commandParts = commandLine.split(",");
        String command = commandParts[0].trim();


        switch (command) {
            case "S":
                scheduleAppointment();
                break;
            case "C":
                cancelAppointment();
                break;
            case "R":
                rescheduleAppointment();
                break;
            case "PA":
                list.printByAppointment();  // Printing sorted by date/timeslot/provider
                break;
            case "PP":
                list.printByPatient();  // Printing sorted by patient profile
                break;
            case "PL":
                list.printByLocation();  // Printing sorted by location
                break;
            case "PS":
                printBillingStatement();  // Printing the bill statement
                break;
            case "Q":

                System.out.println("Program terminated. ");
                break;
            default:
                System.out.println("Invalid command! " );
                break;
        }
    }
    // method to check if a provider is available
    public boolean isAvailable(Provider provider, Date appointmentDate, Timeslot timeslot) {

        if(list.getSize() == 0){
            return true;
        }
       Appointment[] appointments = list.getAppointments();
        for (int i = 0; i < list.getSize(); i++) {
            // Checking if the appointment date, timeslot, and provider match
            if (appointments[i].getDate().equals(appointmentDate) &&
                    appointments[i].getTimeslot().equals(timeslot) &&
                    appointments[i].getProvider().equals(provider)) {
                // If a match is found, return false (not available)
                return false;
            }
        }

        return true;
    }
    // method to print billing statement
    public void printBillingStatement() {
        medicalRecord.sort();
         Patient[] patientsBill = medicalRecord.getPatients();
        // Sorting patients by profile
        System.out.println("** Billing statement ordered by patient **");


        int count = 1;
        for (int i = 0; i < medicalRecord.getSize(); i++) {
            Profile profile = patientsBill[i].getProfile();
            int totalCharge = patientsBill[i].finalCharge();

            // Format and print the billing statement
            System.out.println("(" + count + ") " + profile.getFname() + " " + profile.getLname() +
                    " " + profile.getDob() + " [amount due: $" + String.format("%,.2f", (double) totalCharge) + "]");
            count++;
        }

        System.out.println("** end of list **");
        list.emptyList();
    }

    //Scheduler method to schedule a new appointment
    public void scheduleAppointment(){
        if (commandParts.length < 7) {
            System.out.println("Invalid command. Usage: S <PatientName> <Date> <Timeslot> <Provider>");
            return;
        }

        String dateStr = commandParts[1];
        String timeslotStr= commandParts[2];
        String patientFirstName = commandParts[3];
        String patientLastName = commandParts[4];
        String dobStr = commandParts[5];
        String providerStr = commandParts[6];

        //Creating the date object
        Date appointmentDate;
        try {
            String[] dateParts = dateStr.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            appointmentDate = new Date(year, month, day);
            if (!appointmentDate.isValid()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " is not a valid date.");
                return;
            }
            if (appointmentDate.isPastDate()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " cannot be in the past.");
                return;
            }
            if (!appointmentDate.isWeekday()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " is on a Saturday/Sunday.");
                return;
            }
            if (!appointmentDate.isWithinSixMonths()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " is not within six months from today.");
                return;
            }



        } catch (Exception e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return;
        }
        //Creating the date of birth object
        Date dob;
        try {
            String[] dobParts = dobStr.split("/");
            int month = Integer.parseInt(dobParts[0]);
            int day = Integer.parseInt(dobParts[1]);
            int year = Integer.parseInt(dobParts[2]);
            dob = new Date(year, month, day);
            if (!dob.isValid()) {
                System.out.println("Patient DOB: " + dob.toString() + " is not a valid date.");
                return; // Exit the method if the DOB is invalid
            }
            if (!dob.isPastDate()) {
                System.out.println("Patient DOB: " + dob.toString() + " cannot be in the future.");
                return; // Exit if the DOB is in the future
            }

        } catch (Exception e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return;
        }

        // Parsing timeslot
        int timeslotNumber;
        try {
            timeslotNumber = Integer.parseInt(timeslotStr); // Convert the string to an integer
        } catch (NumberFormatException e) {
            System.out.println(timeslotStr + " is not a valid timeslot");
            return;
        }

        // Getting the corresponding Timeslot enum
        Timeslot timeslot;
        try {
            timeslot = Timeslot.fromNumber(timeslotNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(timeslotStr + " is not a valid timeslot"); // Printing error message if invalid timeslot number
            return;
        }

        // Parsing provider
        Provider provider;
        try {
            provider = Provider.valueOf(providerStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(providerStr + " -provider does not exist");
            return;
        }

        // Checking if patient already exists
        Patient patient = medicalRecord.findPatientByName(patientFirstName, patientLastName, dob);
        Appointment newAppointment;
        if (patient == null) {
            if (!isAvailable(provider, appointmentDate, timeslot)) {
                System.out.println("Provider " + provider.toString() + " is not available on " + appointmentDate.toString() + " at " + timeslot.toString());
                return;
            }
            Profile p = new Profile(patientFirstName,patientLastName, dob);
             newAppointment = new Appointment(appointmentDate, timeslot, p, provider);
            Visit newVisit = new Visit(newAppointment);
              patient = new Patient(p, newVisit);
            medicalRecord.add(patient);// Adjust based on your Profile and Patient classes
        }
        else{
            Visit visits = patient.getVisits();
            while(visits != null){
                Appointment temp = visits.getAppointment();
                if(temp.getDate().equals(appointmentDate) && temp.getTimeslot().equals(timeslot)&& temp.getProvider().equals(provider)){
                    System.out.println("Appointment already exists for " + patient.getProfile().toString()   + " on " + appointmentDate.toString() + " at " + timeslot.toString() );
                    return;
                }
                visits = visits.getNext();
            }
            if (!isAvailable(provider, appointmentDate, timeslot)) {
                System.out.println("Provider " + provider.toString() + " is not available on " + appointmentDate.toString() + " at " + timeslot.toString());
                return;
            }
             newAppointment = new Appointment(appointmentDate, timeslot, patient.getProfile(), provider);
            Visit newVisit = new Visit(newAppointment);
            patient.addVisit(newVisit);
        }

        list.add(newAppointment);
        // Printing the scheduled appointment
        System.out.println(appointmentDate.toString() + " " + timeslot.toString() + " " + patient.getProfile().toString() + " " + provider.toString()   + " booked");
    }

    //Method to reschedule an already scheduled appointment
    public void rescheduleAppointment(){
        if (commandParts.length < 7) {
            System.out.println("Invalid command. Usage: R <OldDate> <OldTimeslot> <PatientFirstName> <PatientLastName> <DOB> <NewTimeslot>");
            return;
        }

        String oldDateStr = commandParts[1];
        String oldTimeslotStr = commandParts[2];
        String patientFirstName = commandParts[3];
        String patientLastName = commandParts[4];
        String dobStr = commandParts[5];
        String newTimeslotStr = commandParts[6];

        // Validating the old appointment date
        Date oldAppointmentDate;
        try {
            String[] dateParts = oldDateStr.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            oldAppointmentDate = new Date(year, month, day);
            if (!oldAppointmentDate.isValid()) {
                System.out.println("Appointment Date: " + oldAppointmentDate.toString() + " is not a valid date.");
                return; // Exit if the appointment date is invalid
            }
            if (oldAppointmentDate.isPastDate()) {
                System.out.println("Appointment Date: " + oldAppointmentDate.toString() + " cannot be in the past.");
                return; // Exit if the appointment date is in the past
            }
            if (!oldAppointmentDate.isWeekday()) {
                System.out.println("Appointment Date: " + oldAppointmentDate.toString() + " cannot be on a weekend (Saturday/Sunday).");
                return; // Exit if the appointment date is on a weekend
            }
            if (!oldAppointmentDate.isWithinSixMonths()) {
                System.out.println("Appointment Date: " + oldAppointmentDate.toString() + " must be within six months from today.");
                return; // Exit if the appointment date is not within six months
            }
        } catch (Exception e) {
            System.out.println("Invalid old date format. Please use MM/DD/YYYY.");
            return;
        }
        // Checking for the validity of date of birth
        Date dob;
        try {
            String[] dobParts = dobStr.split("/");
            int month = Integer.parseInt(dobParts[0]);
            int day = Integer.parseInt(dobParts[1]);
            int year = Integer.parseInt(dobParts[2]);
            dob = new Date(year, month, day);
            if (!dob.isValid()) {
                System.out.println("Patient DOB: " + dob.toString() + " is not a valid date.");
                return; // Exit the method if the DOB is invalid
            }
            if (!dob.isPastDate()) {
                System.out.println("Patient DOB: " + dob.toString() + " cannot be in the future.");
                return; // Exit if the DOB is in the future
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return;
        }

        int timeslotNumber;
        try {
            timeslotNumber = Integer.parseInt(oldTimeslotStr); // Convert the string to an integer
        } catch (NumberFormatException e) {
            System.out.println(oldTimeslotStr + " is not a valid timeslot");
            return;
        }

        // Getting the corresponding oldTimeslot enum
        Timeslot oldTimeslot;
        try {
            oldTimeslot = Timeslot.fromNumber(timeslotNumber); // Use the method to get the Timeslot enum
        } catch (IllegalArgumentException e) {
            System.out.println(oldTimeslotStr + " is not a valid timeslot"); // Print error message if invalid timeslot number
            return;
        }
        int newTimeslotNumber;
        try {
            newTimeslotNumber = Integer.parseInt(newTimeslotStr); // Convert the string to an integer
        } catch (NumberFormatException e) {
            System.out.println(newTimeslotStr + " is not a valid timeslot");
            return;
        }

        // getting the new timeslot enum
        Timeslot newTimeslot;
        try {
            newTimeslot = Timeslot.fromNumber(newTimeslotNumber);  // Assuming Timeslot is an enum
        } catch (IllegalArgumentException e) {
            System.out.println(newTimeslotStr + " is not a valid timeslot");
            return;
        }

        // Finding the patient and the already scheduled appointment
        boolean found = false;
        Patient patient = medicalRecord.findPatientByName(patientFirstName, patientLastName, dob);
        if (patient == null) {
            System.out.println("Patient " + patientFirstName + " " + patientLastName + " not found.");
            return;
        }

        // Creating a temporary appointment object for the old appointment
        Appointment oldAppointment = null;
        Visit currentVisit = patient.getVisits();
        while(currentVisit != null) {
            if (currentVisit.getAppointment().getDate().equals(oldAppointmentDate) && currentVisit.getAppointment().getTimeslot().equals(oldTimeslot)) {
                found = true;
                oldAppointment = currentVisit.getAppointment();
                break;
            }
            currentVisit = currentVisit.getNext();
            }



    // Printing error message if appointment not found
    if (!found) {
        System.out.println(oldAppointmentDate.toString() + " " + oldTimeslot.toString() + " " + patient.getProfile().toString() + " does not exist");
        return;
    }
    Provider p = oldAppointment.getProvider();

        Visit visits = patient.getVisits();
        while(visits != null){
            Appointment temp = visits.getAppointment();
            if(temp.getDate().equals(oldAppointmentDate) && temp.getTimeslot().equals(newTimeslot)&& temp.getProvider().equals(p)){
                System.out.println("Appointment already exists for " + patient.getProfile().toString()   + " on " + oldAppointmentDate.toString() + " at " + newTimeslot.toString() );
                return;
            }
            visits = visits.getNext();
        }
        if (!isAvailable(p, oldAppointmentDate, newTimeslot)) {
            System.out.println("Provider " + p.toString() + " is not available on " + oldAppointmentDate.toString() + " at " + newTimeslot.toString());
            return;
        }
    // Creating a new appointment with the updated details
    Appointment newAppointment = new Appointment(oldAppointmentDate, newTimeslot, patient.getProfile(),p); // Provider is not needed

    // Removing the old visit and add the new one
    Visit oldVisit = new Visit(oldAppointment);
    patient.removeVisit(oldVisit);
    patient.addVisit(new Visit(newAppointment));
    list.add(newAppointment);

    // Confirmation message
    System.out.println("Rescheduled to " + oldAppointmentDate.toString() + " " + newTimeslot.toString() + " " + patient.getProfile().toString() + " " + p.toString());
}
    //Method to cancel an already existing appointment
    public void cancelAppointment(){
        if (commandParts.length < 7) {
            System.out.println("Invalid command. Usage: C <PatientName> <Date> <Timeslot> <Provider>");
            return;
        }

        String dateStr = commandParts[1];
        String timeslotStr = commandParts[2]; // e.g., "SLOT1"
        String patientFirstName = commandParts[3]; // e.g., "John"
        String patientLastName = commandParts[4];
        String dobStr = commandParts[5];// e.g., "Doe"
        String providerStr = commandParts[6]; // e.g., "PATEL"

        // Validating the date format
        Date appointmentDate;
        try {
            String[] dateParts = dateStr.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            appointmentDate = new Date(year, month, day);
            if (!appointmentDate.isValid()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " is not a valid date.");
                return; // Exiting if the date is invalid
            }
            if (appointmentDate.isPastDate()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " cannot be in the past.");
                return; // Exiting if the date is in the past
            }
            if (!appointmentDate.isWeekday()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " cannot be on a weekend (Saturday/Sunday).");
                return; // Exiting if the date is a weekend
            }
            if (!appointmentDate.isWithinSixMonths()) {
                System.out.println("Appointment Date: " + appointmentDate.toString() + " must be within six months from today.");
                return; // Exiting if the date is not within six months
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return;
        }
        //Validating date of birth
        Date dob;
        try {
            String[] dobParts = dobStr.split("/");
            int month = Integer.parseInt(dobParts[0]);
            int day = Integer.parseInt(dobParts[1]);
            int year = Integer.parseInt(dobParts[2]);
            dob = new Date(year, month, day);
            if (!dob.isValid()) {
                System.out.println("Patient DOB: " + dob.toString() + " is not a valid date.");
                return; // Exiting the method if the DOB is invalid
            }
            if (!dob.isPastDate()) {
                System.out.println("Patient DOB: " + dob.toString() + " cannot be in the future.");
                return; // Exiting if the DOB is in the future
            }
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use MM/DD/YYYY.");
            return;
        }
        int timeslotNumber;
        try {
            timeslotNumber = Integer.parseInt(timeslotStr); // Convert the string to an integer
        } catch (NumberFormatException e) {
            System.out.println(timeslotStr + " is not a valid timeslot");;
            return;
        }

        // Getting the corresponding Timeslot enum
        Timeslot timeslot;
        try {
            timeslot = Timeslot.fromNumber(timeslotNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(timeslotStr + " is not a valid timeslot"); // Printing error message if invalid timeslot number
            return;
        }
        Provider provider;
        try {
            provider = Provider.valueOf(providerStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(providerStr + " is not a provider");
            return;
        }
        // Finding the patient
        Patient patient = medicalRecord.findPatientByName(patientFirstName, patientLastName, dob);
        if (patient == null) {
            System.out.println("Patient " + patientFirstName + " " + patientLastName + " not found.");
            return;
        }
        // Creating a temporary appointment object to match against
        boolean found = false;
        Appointment appointmentToCancel = null;
        Visit currentVisit = patient.getVisits();
        while(currentVisit != null) {
            if (currentVisit.getAppointment().getDate().equals(appointmentDate) &&
                    currentVisit.getAppointment().getTimeslot().equals(timeslot)&&
                    currentVisit.getAppointment().getProvider().equals(provider)) {
                found = true;
                appointmentToCancel = currentVisit.getAppointment();
                break;
            }
            currentVisit = currentVisit.getNext();
            }


        if(!found){
            System.out.println(appointmentDate.toString() + " " + timeslot.toString() + " " + patient.getProfile().toString() + " does not exist");
            return;
        }

        // Removing the canceled appointment from the main list
        list.remove(appointmentToCancel);
        patient.removeVisit(currentVisit);

        // Confirmation message
        System.out.println(appointmentDate.toString() + " " + timeslot.toString() + " " + patient.getProfile().toString() + " " + provider.toString() +  " canceled");
    }
        }






