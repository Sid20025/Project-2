package p2;


/**
 * The Person class represents a general person with a profile containing their
 * first name, last name, and date of birth. It includes methods for comparison,
 * checking equality, and providing a string representation of the person.
 * This class serves as a base class for other specific types of individuals.
 *
 * @author Siddharth
 */
public class Person implements Comparable<Person> {

    // Instance variable to store the profile of the person
    protected Profile profile;

    /**
     * Constructor to create a Person instance with the given profile.
     *
     * @param profile the profile of the person
     */
    public Person(Profile profile) {
        this.profile = profile;
    }

    /**
     * Gets the profile associated with this person.
     *
     * @return the profile of this person
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Compares this Person with another Person based on their profiles.
     * This is used to sort Person objects, for example, in alphabetical order.
     *
     * @param otherPerson the other person to compare against
     * @return a negative integer, zero, or a positive integer as this person is less than,
     *         equal to, or greater than the specified person
     */
    @Override
    public int compareTo(Person otherPerson) {
        // Assuming Profile class has a compareTo method, use it here
        return this.profile.compareTo(otherPerson.profile);
    }

    /**
     * Checks if this Person is equal to another object based on their profile.
     *
     * @param obj the object to compare to this person
     * @return true if the object is a Person with the same profile, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person person = (Person) obj;
            // Assuming Profile class has an equals method
            return this.profile.equals(person.profile);
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of the Person, including their first name,
     * last name, and date of birth.
     *
     * @return a string representation of the person
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", profile.getFname(), profile.getLname(), profile.getDob());
    }
}



