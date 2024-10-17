package util;


import p2.Appointment;
import p2.Provider;

/**
 * Provides all sorting functionality for other classes.
 * The Sort class includes methods for sorting lists of providers and appointments based on different criteria.
 */
public class Sort {

    /**
     * Enum representing sorting keys.
     * It defines different types of sorting that can be applied to the data.
     */
    public enum SortType {
        PATIENT_SORTING,    // Sort by patient details.
        LOCATION_SORTING,   // Sort by location details.
        APPOINTMENT_SORTING // Sort by appointment details.
    }

    /**
     * Sorts the list array based on provider profiles, using insertion sort.
     * This sorts a list of Providers by their profile information.
     *
     * @param providers List to be sorted.
     */
    private static void sort(List<Provider> providers) {
        for (int i = 1; i < providers.size(); i++) {
            Provider appKey = providers.get(i);
            int j = i - 1;
            // Shift elements that are greater than appKey to one position ahead of their current position.
            while (j >= 0 && compareProvider(appKey, providers.get(j)) < 0) {
                providers.set(j + 1, providers.get(j));
                j--;
            }
            // Place appKey in the correct position.
            providers.set(j + 1, appKey);
        }
    }

    /**
     * Compares two patients by their last name, first name, dob, and appointment details.
     * This method helps in sorting patients.
     *
     * @param app1 Appointment 1.
     * @param app2 Appointment 2.
     * @return Negative if app1 comes before app2, positive if app1 comes after app2, or 0 if they are equal.
     */
    private static int comparePatient(Appointment app1, Appointment app2) {
        int comparePatient = app1.getPatient().getProfile().compareTo(app2.getPatient().getProfile());
        if (comparePatient != 0) {
            return comparePatient; // Return comparison based on patient's profile.
        }
        return app1.compareTo(app2); // If profiles are the same, compare appointments.
    }

    /**
     * Compares two appointments based on their county, then date, then time.
     * This helps in sorting appointments by location.
     *
     * @param app1 Appointment 1.
     * @param app2 Appointment 2.
     * @return Negative if app1 comes before app2, positive if app1 comes after app2, or 0 if they are equal.
     */
    private static int compareLocation(Appointment app1, Appointment app2) {
        String county1 = ((Provider) (app1.getProvider())).getLocation().getCounty();
        String county2 = ((Provider) (app2.getProvider())).getLocation().getCounty();
        int compareCounty = county1.compareTo(county2);
        if (compareCounty != 0) return compareCounty; // Return comparison based on location.

        return app1.compareTo(app2); // If counties are the same, compare by date and time.
    }

    /**
     * Compares two appointments based on date, then timeslot, then provider's name.
     * This helps in sorting appointments chronologically.
     *
     * @param app1 Appointment 1.
     * @param app2 Appointment 2.
     * @return Negative if app1 comes before app2, positive if app1 comes after app2, or 0 if they are equal.
     */
    private static int compareAppointment(Appointment app1, Appointment app2) {
        int compareDateTime = app1.compareTo(app2);
        if (compareDateTime != 0) {
            return compareDateTime; // Return comparison based on date and time.
        }
        // If date and time are the same, compare by provider's profile.
        return app1.getProvider().getProfile().compareTo(app2.getProvider().getProfile());
    }

    /**
     * Sorts the list array based on one of the sorting factors, using insertion sort.
     * It can sort based on patient details, appointment time, or location.
     *
     * @param appointments List to be sorted.
     * @param compare Key identifying the particular sort.
     */
    private static void sort(List<Appointment> appointments, SortType compare) {
        for (int i = 1; i < appointments.size(); i++) {
            Appointment appKey = appointments.get(i);
            int j = i - 1;
            switch (compare) {
                case PATIENT_SORTING:
                    // Sort by patient details.
                    while (j >= 0 && comparePatient(appKey, appointments.get(j)) < 0) {
                        appointments.set(j + 1, appointments.get(j));
                        j--;
                    }
                    break;
                case APPOINTMENT_SORTING:
                    // Sort by appointment time.
                    while (j >= 0 && compareAppointment(appKey, appointments.get(j)) < 0) {
                        appointments.set(j + 1, appointments.get(j));
                        j--;
                    }
                    break;
                case LOCATION_SORTING:
                    // Sort by location.
                    while (j >= 0 && compareLocation(appKey, appointments.get(j)) < 0) {
                        appointments.set(j + 1, appointments.get(j));
                        j--;
                    }
                    break;
            }
            appointments.set(j + 1, appKey);
        }
    }

    /**
     * Compares two providers by their last name, first name, and date of birth.
     * This helps in sorting providers alphabetically.
     *
     * @param p1 Provider 1.
     * @param p2 Provider 2.
     * @return Negative if p1 comes before p2, positive if p1 comes after p2, or 0 if they are equal.
     */
    public static int compareProvider(Provider p1, Provider p2) {
        return p1.getProfile().compareTo(p2.getProfile());
    }

    /**
     * Entry point to sort a list of Providers.
     * This sorts the provider list using the internal sorting method.
     *
     * @param list List of providers to be sorted.
     */
    public static void provider(List<Provider> list) {
        sort(list);
    }

    /**
     * Entry point to sort a list of Appointments based on the provided SortType key.
     *
     * @param list List of appointments to be sorted.
     * @param sortType Key to determine the sorting criteria.
     */
    public static void appointment(List<Appointment> list, SortType sortType) {
        sort(list, sortType);
    }
}
