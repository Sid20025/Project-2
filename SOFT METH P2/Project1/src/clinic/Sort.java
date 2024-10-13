package clinic;


    public class Sort {

        // Sort appointments based on different keys: 'p' for patient, 'd' for date, 't' for timeslot, 'l' for location (provider's location)
        public static void appointment(List<Appointment> list, char key) {
            switch (key) {
                case 'p':  // Sort by patient
                    sortByPatient(list);
                    break;
                case 'd':  // Sort by date
                    sortByDate(list);
                    break;
                case 't':  // Sort by timeslot
                    sortByTimeslot(list);
                    break;
                case 'l':  // Sort by location (provider's county)
                    sortByLocation(list);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid sorting key: " + key);
            }
        }

        // Sort providers based on their profile (assumes Provider has a method to compare profiles)
        public static void provider(List<Provider> list) {
            // Using a basic bubble sort for demonstration, but this can be replaced by more efficient algorithms like quicksort
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = 0; j < list.size() - i - 1; j++) {
                    Provider p1 = list.get(j);
                    Provider p2 = list.get(j + 1);
                    if (p1.getProfile().compareTo(p2.getProfile()) > 0) {
                        swap(list, j, j + 1);
                    }
                }
            }
        }

        // Helper method to swap elements in the list
        private static <E> void swap(List<E> list, int i, int j) {
            E temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }

        // Sort appointments by patient
        private static void sortByPatient(List<Appointment> list) {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = 0; j < list.size() - i - 1; j++) {
                    Appointment a1 = list.get(j);
                    Appointment a2 = list.get(j + 1);
                    if (a1.getPatient().getProfile().compareTo(a2.getPatient().getProfile()) > 0) {
                        swap(list, j, j + 1);
                    }
                }
            }
        }

        // Sort appointments by date
        private static void sortByDate(List<Appointment> list) {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = 0; j < list.size() - i - 1; j++) {
                    Appointment a1 = list.get(j);
                    Appointment a2 = list.get(j + 1);
                    if (a1.getDate().compareTo(a2.getDate()) > 0) {
                        swap(list, j, j + 1);
                    }
                }
            }
        }

        // Sort appointments by timeslot
        private static void sortByTimeslot(List<Appointment> list) {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = 0; j < list.size() - i - 1; j++) {
                    Appointment a1 = list.get(j);
                    Appointment a2 = list.get(j + 1);
                    if (a1.getTimeslot().compareTo(a2.getTimeslot()) > 0) {
                        swap(list, j, j + 1);
                    }
                }
            }
        }

        // Sort appointments by provider's location (county)
        private static void sortByLocation(List<Appointment> list) {
            for (int i = 0; i < list.size() - 1; i++) {
                for (int j = 0; j < list.size() - i - 1; j++) {
                    Appointment a1 = list.get(j);
                    Appointment a2 = list.get(j + 1);
                    String county1 = a1.getProvider().getLocation().getCounty();
                    String county2 = a2.getProvider().getLocation().getCounty();
                    if (county1.compareToIgnoreCase(county2) > 0) {
                        swap(list, j, j + 1);
                    }
                }
            }
        }
    }


