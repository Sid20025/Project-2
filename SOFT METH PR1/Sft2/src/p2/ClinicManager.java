package p2;

import util.CircularList;
import util.Date;
import util.List;
import util.Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;



/**
 * ClinicManager class - Handles the scheduling and management of clinic appointments.
 * This class replaces the Scheduler class from Project 1.
 * It processes commands from the terminal for managing office visits and imaging services.
 *
 *
 */
public class ClinicManager {
    private final List<Provider> providers;
    private final List<Appointment> Appointments;
    private CircularList<Technician> technicianRotation;
    private String[] commandParts;


    public ClinicManager() {
        providers = new List<Provider>();
        Appointments = new List<>();
        technicianRotation = new CircularList<>();

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Clinic Manager is running.");
        loadProvidersFromFile("providers.txt");
        Sort.provider(providers);
        print(providers);
        System.out.println("\nRotation list for the technicians.");
        print(technicianRotation);

         String command;

        while (true) {
            command = scanner.nextLine().trim();
            if (command.isEmpty()) {
                continue;
            }
            if (command.equals("Q")) {
                System.out.println("Clinic Manager terminated");
                break;
            }
            processCommand(command);
        }
    }

    private void loadProvidersFromFile(String filename) {
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String currentLine = fileScanner.nextLine();
                commandParts = currentLine.split("  ");
                Provider provider = commandParts[0].equals("D") ?
                        new Doctor(new Profile(commandParts[1], commandParts[2], Date.fromString(commandParts[3])),
                                Location.valueOf(commandParts[4].toUpperCase()),
                                Specialty.valueOf(commandParts[5].toUpperCase()), commandParts[6])
                        :
                        new Technician(new Profile(commandParts[1], commandParts[2], Date.fromString(commandParts[3])),
                                Location.valueOf(commandParts[4].toUpperCase()),
                                Integer.parseInt(commandParts[5]));

                // Confirm that NPIs are unique
                if (provider instanceof Doctor && providers.contains(provider)) {
                    System.out.println("A provider with npi " + commandParts[6] + " already exists, skipping.");
                    continue;
                }
                providers.add(provider);
            }
            initializeTechnicianRotation();
        } catch (FileNotFoundException e) {
            System.err.println("Providers file not found or accessible: " + e);
        } catch (IllegalArgumentException e) {
            System.err.println("Technician's cost was not a parseable int: " + e);
        }
    }



    private void initializeTechnicianRotation() {
        technicianRotation = new CircularList<>();
        Technician head = null;
        for (int i = providers.size() - 1; i >= 0; i--) {
            if (providers.get(i) instanceof Technician tech) {
                technicianRotation.add(tech);
                if (i == providers.size() - 1) {
                    head = tech;
                }
            }
        }
        technicianRotation.setHead(head);
    }

    private Provider findProviderByNPI(String npi) {
        for (Provider provider : providers) {
            if (provider instanceof Doctor && ((Doctor) provider).getNpi().equals(npi)) {
                return provider;
            }
        }
        return null;
    }


    private void processCommand(String command) {
        try {
            commandParts = command.split(",");
            switch (commandParts[0]) {
                case "D":
                    scheduleOfficeAppointment();
                    break;
                case "T":
                    scheduleImagingAppointment();
                    break;
                case "C":
                    cancelAppointment();
                    break;
                case "R":
                    rescheduleAppointment();
                    break;
                case "PA":
                    listSortedByAppointment();
                    break;
                case "PP":
                    listSortedByPatient();
                    break;
                case "PL":
                    listSortedByLocation();
                    break;
                case "PS":
                    displayBillingStatements();
                    break;
                case "PO":
                    displayOfficeAppointments();
                    break;
                case "PI":
                  displayImagingAppointments();
               break;
                case "PC":
                    displayExpectedCreditAmounts();
                    break;
                default:
                    System.out.println("Invalid command: " + command);
                    break;
            }
        } catch (Exception e) {
            System.out.println("Error processing command: " + e.getMessage());
        }

    }

    public boolean isValidCommandSchedule(){
        if (commandParts.length < 7) {
            throw new IllegalArgumentException("Missing data tokens for office appointment.");
        }
        Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
        if (!dateChecks(appointmentDate)){
            return false;
        }
        Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
            if (!dob.isValid()) {
                System.out.println("Patient DOB: " + dob + " is not a valid date.");
                return false; // Exit the method if the DOB is invalid
            }
            if (!dob.isPastDate()) {
                System.out.println("Patient DOB: " + dob + " cannot be in the future.");
                return false; // Exit if the DOB is in the future
            }
        try {
            int timeslotNumber = Integer.parseInt(commandParts[2]);
            Timeslot timeslot = Timeslot.fromNumber(timeslotNumber);
            // Use the timeslot as needed after it's successfully created
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid timeslot format. It should be a valid integer.");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid timeslot number. Please provide a valid timeslot between 1 and 12.");
            return false;
        }
        String npi = commandParts[6];
        Provider provider = findProviderByNPI(npi);
        if (provider == null || !(provider instanceof Doctor)) {
            System.out.println("Invalid or unavailable doctor.");
            return false;
        }
        return true;
    }

    public boolean isValidImagingSchedule() {
        if (commandParts.length < 7) {
            throw new IllegalArgumentException("Missing data tokens for office appointment.");
        }
        Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
        if (!dateChecks(appointmentDate)){
            return false;
        }
        Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
        if (!dob.isValid()) {
            System.out.println("Patient DOB: " + dob + " is not a valid date.");
            return false; // Exit the method if the DOB is invalid
        }
        if (!dob.isPastDate()) {
            System.out.println("Patient DOB: " + dob + " cannot be in the future.");
            return false; // Exit if the DOB is in the future
        }
        try {
            int timeslotNumber = Integer.parseInt(commandParts[2]);
            Timeslot timeslot = Timeslot.fromNumber(timeslotNumber);
            // Use the timeslot as needed after it's successfully created
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid timeslot format. It should be a valid integer.");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid timeslot number. Please provide a valid timeslot between 1 and 12.");
            return false;
        }
        String imagingType = commandParts[6].toUpperCase();
        Radiology room;
        try {
            room = Radiology.valueOf(imagingType);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid imaging type.");
            return false;
        }

        return true;
    }

    public boolean isValidReschedule() {

        if (commandParts.length < 7) {
            throw new IllegalArgumentException("Missing data tokens for office appointment.");
        }
        Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
        if (!dateChecks(appointmentDate)) {
            return false;
        }
        Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
        if (!dob.isValid()) {
            System.out.println("Patient DOB: " + dob + " is not a valid date.");
            return false; // Exit the method if the DOB is invalid
        }
        if (!dob.isPastDate()) {
            System.out.println("Patient DOB: " + dob + " cannot be in the future.");
            return false; // Exit if the DOB is in the future
        }
        try {
            int timeslotNumber = Integer.parseInt(commandParts[2]);
            Timeslot oldTimeslot = Timeslot.fromNumber(timeslotNumber);
            // Use the timeslot as needed after it's successfully created
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid timeslot format. It should be a valid integer.");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid timeslot number. Please provide a valid timeslot between 1 and 12.");
            return false;
        }
        try{
        int newTimeslotNumber = Integer.parseInt(commandParts[6]);
        Timeslot newTimeslot = Timeslot.fromNumber(newTimeslotNumber);
        // Use the timeslot as needed after it's successfully created
    } catch (NumberFormatException e) {
        System.out.println("Error: Invalid timeslot format. It should be a valid integer.");
        return false;
    } catch (IllegalArgumentException e) {
        System.out.println("Error: Invalid timeslot number. Please provide a valid timeslot between 1 and 12.");
        return false;
    }

        return true;
    }

    public boolean isValidCancel(){
        if (commandParts.length < 6) {
            throw new IllegalArgumentException("Missing data tokens for office appointment.");
        }
        Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
        if (!dateChecks(appointmentDate)) {
            return false;
        }
        Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
        if (!dob.isValid()) {
            System.out.println("Patient DOB: " + dob + " is not a valid date.");
            return false; // Exit the method if the DOB is invalid
        }
        if (!dob.isPastDate()) {
            System.out.println("Patient DOB: " + dob + " cannot be in the future.");
            return false; // Exit if the DOB is in the future
        }
        try {
            int timeslotNumber = Integer.parseInt(commandParts[2]);
            Timeslot timeslot = Timeslot.fromNumber(timeslotNumber);
            // Use the timeslot as needed after it's successfully created
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid timeslot format. It should be a valid integer.");
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Invalid timeslot number. Please provide a valid timeslot between 1 and 12.");
            return false;
        }


        return true;
    }


    public boolean dateChecks(Date date){
            if (!date.isValid()) {
                System.out.println("Appointment Date: " + date + " is not a valid date.");
                return false;
            }
            if (date.isPastDate()) {
                System.out.println("Appointment Date: " + date + " cannot be in the past.");
                return false;
            }
            if (!date.isWeekday()) {
                System.out.println("Appointment Date: " + date + " is on a Saturday/Sunday.");
                return false;
            }
            if (!date.isWithinSixMonths()) {
                System.out.println("Appointment Date: " + date + " is not within six months from today.");
                return false;
            }
            return true;
        }

        private void scheduleOfficeAppointment() {
            if (!isValidCommandSchedule()) {
                return;
            }
            // Validate and schedule an office appointment
            Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
            String firstName = commandParts[3];
            String lastName = commandParts[4];
            String npi = commandParts[6];
            Provider doctor = findProviderByNPI(npi);
            int timeslotNumber = Integer.parseInt(commandParts[2]);
            Timeslot timeslot = Timeslot.fromNumber(timeslotNumber);
            Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
            if (doctor == null) {
                System.out.println(commandParts[6] + " - provider doesn't exist.");
                return;
            }
            if (!doctor.isAvailableAt(appointmentDate, timeslot, Appointments)) {
                System.out.println("Provider " + doctor.toString() + " is not available on " + appointmentDate + " at " + timeslot);
                return;
            }
            Profile p = new Profile(firstName, lastName, dob);
            Patient temp = new Patient(p);
            Person g = new Person(p);
            Appointment newAppointment = new Appointment(appointmentDate, timeslot, g, doctor);
            Appointments.add(newAppointment);
            System.out.println(newAppointment + " booked.");
        }



    private void scheduleImagingAppointment() {
        // Validate and schedule an imaging appointment
        // Validate and schedule an imaging appointment
        if (!isValidImagingSchedule()) {
            return;
        }

        Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
        int timeslotNumber = Integer.parseInt(commandParts[2]);
            Timeslot timeslot = Timeslot.fromNumber(timeslotNumber);
            String firstName = commandParts[3];
            String lastName = commandParts[4];
            Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
            String imagingType = commandParts[6].toUpperCase();
            Radiology room;
             room = Radiology.valueOf(imagingType);
            Technician appointmentTech = null;
            // Find the next available technician
        Profile p = new Profile(firstName, lastName, dob);

        int counter = 0;
        for (Technician tech: technicianRotation) {
            if (!tech.isAvailableAt(appointmentDate, timeslot, Appointments)) {
                counter++;
                continue;
            }
            Location techLocation = tech.getLocation();
            if (techLocation.roomAvailable(appointmentDate, timeslot, room, Appointments)) {
                Imaging imagingAppt = new Imaging(appointmentDate, timeslot, new Person(p), tech, room);
                Appointments.add(imagingAppt);
                System.out.println(imagingAppt + " booked.");
                technicianRotation.setHead(technicianRotation.circleGet(++counter));
                return;
            }
            counter++;
        }
        // If all technicians are busy at specified appointment timeslot
        System.out.println("Cannot find an available technician at all locations for "
                + room + " at slot " + commandParts[2] + ".");

    }


    // Add validation logic for date, timeslot, imaging type, and technician availability
        // If validation passes, add to imagingAppointments list


    private void cancelAppointment() {
        if (!isValidCancel()) {
            return;
        }
        Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
        int timeslotNumber = Integer.parseInt(commandParts[2]);
        Timeslot timeslot = Timeslot.fromNumber(timeslotNumber);
        String firstName = commandParts[3];
        String lastName = commandParts[4];
        Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
        Profile patientToCancel = new Profile(firstName, lastName, dob);
        Appointment appointmentToCancel = new Appointment(appointmentDate, timeslot, new Person(patientToCancel), null);
        for (Appointment appointment : Appointments) {
            if (appointment.getDate().equals(appointmentDate)
                    && appointment.getTimeslot().equals(timeslot)
                    && appointment.getPatient().equals(patientToCancel)) {
                Appointments.remove(appointment);
                System.out.println(appointmentToCancel + " - appointment has been canceled.");
                return;
            }

            System.out.println(appointmentToCancel + " - appointment does not exist.");


        }
    }

    private void rescheduleAppointment() {
        // Logic to reschedule an existing office appointment
        if(!isValidReschedule()){
            return;
        }
        Date appointmentDate = new Date(Integer.parseInt(commandParts[1].split("/")[2]), Integer.parseInt(commandParts[1].split("/")[0]), Integer.parseInt(commandParts[1].split("/")[1]));
        int timeslotNumber = Integer.parseInt(commandParts[2]);
        Timeslot oldtimeslot = Timeslot.fromNumber(timeslotNumber);
        String firstName = commandParts[3];
        String lastName = commandParts[4];
        Date dob = new Date(Integer.parseInt(commandParts[5].split("/")[2]), Integer.parseInt(commandParts[5].split("/")[0]), Integer.parseInt(commandParts[5].split("/")[1]));
        int newTimeslotNumber = Integer.parseInt(commandParts[6]);
        Timeslot newTimeslot = Timeslot.fromNumber(newTimeslotNumber);
        Profile patientProfile = new Profile(firstName, lastName, dob);
        Appointment appointmentToReschedule = new Appointment(appointmentDate, oldtimeslot, new Person(patientProfile), null);
        Appointment filledAppointment = null;
        for (Appointment appointment: Appointments) {
            if (appointment.getDate().equals(appointmentDate)
                    && appointment.getTimeslot().equals(oldtimeslot)
                    && appointment.getPatient().equals(new Person(patientProfile))) {
                filledAppointment = appointment;
            }
        }
        if (filledAppointment == null) {
            System.out.println(appointmentToReschedule + " does not exist.");
            return;
        }
        // Check if the appointment is being rescheduled for the same time slot
        for (Appointment appointment: Appointments) {
            if (appointment.getTimeslot().equals(newTimeslot)
                    && appointment.getPatient().equals(new Person(patientProfile))
                    && appointment.getDate().equals(appointmentDate)) {
                System.out.printf("%s has an existing appointment at %s %s\n", patientProfile, appointmentDate,
                        newTimeslot);
                return;
            }
        }
        // Check if provider is available at the new timeslot
        appointmentToReschedule = filledAppointment;
        if (newTimeslot == null) {
            System.out.println(commandParts[6] + " is not a valid time slot.");
            return;
        }
        if (!((Provider)appointmentToReschedule.getProvider()).isAvailableAt(appointmentToReschedule.getDate(), newTimeslot,
                Appointments)) {
            System.out.println(appointmentToReschedule.getProvider() + " is not available at slot " + commandParts[6] + ".");
            return;
        }
        appointmentToReschedule.changeTimeslot(newTimeslot);
        System.out.println("Rescheduled to " + appointmentToReschedule);

    }
    private void listSortedByAppointment() {
        System.out.println("\n** List of appointments, ordered by date/time/provider.");
        Sort.appointment(Appointments, Sort.SortType.APPOINTMENT_SORTING);
        System.out.println(Appointments);
        System.out.println("** end of list **");
    }

    /**
     * Sorts the appointment list by patient, then prints it.
     */
    private void listSortedByPatient() {
        Sort.appointment(Appointments, Sort.SortType.PATIENT_SORTING);
        System.out.println(Appointments);
    }

    /**
     * Sorts the appointment list by county, then prints it.
     */
    private void listSortedByLocation() {
        System.out.println("\n** List of appointments, ordered by county/date/time.");
        Sort.appointment(Appointments, Sort.SortType.LOCATION_SORTING);
        System.out.println(Appointments);
        System.out.println("** end of list **");
    }

    /**
     * Helper method to print a list of any type, delimited by \n.
     * @param list List to print
     * @param <T> Type of list
     */
    private <T> void print(List<T> list) {
        for(T item : list) {
            System.out.println(item);
        }
    }

    /**
     * Helper method to print a Circular List of Technician.
     * @param tList List to print
     */
    private void print(CircularList<Technician> tList){
        int count = 0;
        for(Technician tech : tList) {
            System.out.printf("%s %s (%s)", tech.getProfile().getFname(), tech.getProfile().getLname(),
                    tech.getLocation().name());
            if(count < tList.size() - 1) {
                System.out.print(" --> ");
            }
            count++;
        }
        System.out.println();
    }
    //endregion

    //region Display Methods

    /**
     * Helper method that sorts the patients for the purpose of displaying credit statements.
     * @param patients Patients list to sort
     */
    private void sortPatient(List<Profile> patients){
        for(int i = 1; i < patients.size(); i++){
            Profile profKey = patients.get(i);
            int j = i - 1;
            while (j >= 0 && profKey.compareTo(patients.get(j)) < 0) {
                patients.set(j+1, patients.get(j));
                j--;
            }
            patients.set(j+1, profKey);
        }
    }

    /**
     * Displays the billing statements, ordered by patient.
     */
    private void displayBillingStatements() {
        System.out.println("\n** Billing statement ordered by patient. **");

        // Aggregate List of Unique Patients
        List<Profile> patients = new List<>();
        for (Appointment appointment: Appointments) {
            Profile currPatient = appointment.getPatient().getProfile();
            if (!patients.contains(currPatient)) patients.add(currPatient);
        }

        sortPatient(patients);

        // Tally up their appointments, and print
        int count = 1;
        for (Profile patient: patients) {
            double amountDue = 0;
            for (Appointment appointment: Appointments) {
                if (appointment.getPatient().getProfile().equals(patient)) {
                    amountDue += ((Provider) (appointment.getProvider())).rate();
                }
            }
            System.out.printf("(%d) %s [due: $%,.2f]\n", count, patient, amountDue);
            count++;
        }

        System.out.println("** end of list **");
        // Clear appointments afterwards
        for (int i = Appointments.size() - 1; i >= 0; i--) {
            Appointments.remove(Appointments.get(i));
        }
    }



    private void displayOfficeAppointments() {
        System.out.println("\n** List of office appointments ordered by county/date/time.");
        Sort.appointment(Appointments, Sort.SortType.LOCATION_SORTING);
        for (Appointment appointment: Appointments) {
            if (!(appointment instanceof Imaging)) {
                System.out.println(appointment);
            }
        }
        System.out.println("** end of list **");
    }

    /**
     * Displays the list of radiology appointments, ordered by county/date/time.
     */
    private void displayImagingAppointments() {
        System.out.println("\n** List of radiology appointments ordered by county/date/time.");
        Sort.appointment(Appointments, Sort.SortType.LOCATION_SORTING);
        for (Appointment appointment: Appointments) {
            if (appointment instanceof Imaging) {
                System.out.println(appointment);
            }
        }
        System.out.println("** end of list **");
    }

    /**
     * Displays the expected credit amounts, ordered by provider.
     */
    private void displayExpectedCreditAmounts() {
        System.out.println("\n** Credit amount ordered by provider. **");
        Sort.provider(providers);
        int count = 1;
        for (Provider provider: providers) {
            int apptCount = 0;
            for (Appointment appointment: Appointments) {
                if (appointment.provider.equals(provider)) apptCount++;
            }
            double creditAmount = apptCount * provider.rate();
            System.out.printf("(%d) %s [credit amount: $%.2f]\n", count, provider.getProfile(), creditAmount);
            count++;
        }
        System.out.println("** end of list **");
    }

}









