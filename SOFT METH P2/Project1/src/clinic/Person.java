package clinic;


    public class Person implements Comparable<Person> {
        // Instance variable (protected as per the requirement)
        protected Profile profile;

        // Constructor
        public Person(Profile profile) {
            this.profile = profile;
        }

        // Getter for the profile
        public Profile getProfile() {
            return profile;
        }

        // Overriding the compareTo method for sorting purposes
        @Override
        public int compareTo(Person otherPerson) {
            // Assuming Profile class has a compareTo method, use it here
            return this.profile.compareTo(otherPerson.profile);
        }

        // Overriding the equals method to compare two Person objects based on their profile
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Person person = (Person) obj;
            // Assuming Profile class has an equals method
            return this.profile.equals(person.profile);
        }

        // Overriding the toString method to provide a string representation of the Person
        @Override
        public String toString() {
            // Assuming Profile class has a toString method
            return "Person{" + "profile=" + profile.toString() + '}';
        }
    }


