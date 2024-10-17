/**
 * The Profile class represents a user's profile containing a first name, last name,
 *    and date of birth. It includes comparison methods based on these fields to allow for
 *    sorting and equality checks. This class is useful for keeping track of individual
 *    users or patients in the context of a scheduling system.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package p2;

import util.Date;

/**
 * The Profile class represents an individual's personal information including
 * their first name, last name, and date of birth. This class provides methods
 * for comparing profiles, checking for equality, and converting profile information
 * to a string format.
 *
 * @author Siddharth
 */
public class Profile implements Comparable<Profile> {

    // Instance variables for first name, last name, and date of birth
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor to create a Profile instance with the given first name, last name, and date of birth.
     *
     * @param fname the first name of the individual
     * @param lname the last name of the individual
     * @param dob the date of birth of the individual
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Converts the profile information to a string representation.
     *
     * @return a string containing the first name, last name, and date of birth
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob;
    }

    /**
     * Gets the first name of the individual.
     *
     * @return the first name
     */
    public String getFname() {
        return fname;
    }

    /**
     * Gets the last name of the individual.
     *
     * @return the last name
     */
    public String getLname() {
        return lname;
    }

    /**
     * Gets the date of birth of the individual.
     *
     * @return the date of birth
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Compares this profile with another profile. The comparison is performed by
     * first comparing the last names, then the first names if the last names are
     * identical, and finally by comparing the dates of birth.
     *
     * @param other the profile to compare against
     * @return a negative integer, zero, or a positive integer as this profile is less than,
     *         equal to, or greater than the specified profile
     */
    @Override
    public int compareTo(Profile other) {
        int lastNameComparison = this.lname.compareToIgnoreCase(other.getLname());
        if (lastNameComparison != 0) {
            return lastNameComparison;
        }

        int firstNameComparison = this.fname.compareToIgnoreCase(other.getFname());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        }

        return this.dob.compareTo(other.getDob());
    }

    /**
     * Checks if this profile is equal to another object based on the first name,
     * last name, and date of birth.
     *
     * @param obj the object to compare with this profile
     * @return true if the object is a Profile and has the same first name, last name,
     *         and date of birth, otherwise false
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // If the two references point to the same object
        }
        if (!(obj instanceof Profile)) {
            return false; // If the object is not an instance of Profile
        }
        Profile temp = (Profile) obj;
        return this.compareTo(temp) == 0;
    }
}

