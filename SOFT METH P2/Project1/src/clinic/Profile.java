/**
 * The Profile class represents a user's profile containing a first name, last name,
 *    and date of birth. It includes comparison methods based on these fields to allow for
 *    sorting and equality checks. This class is useful for keeping track of individual
 *    users or patients in the context of a scheduling system.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;

public class Profile implements Comparable <Profile>{
    private String fname;
    private String lname;
    private Date dob;

    // constructor method for profile
    public Profile(String fname, String lname, Date dob){
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }
    // method to convert the profile to string
    @Override
    public String toString(){
        return fname + " " + lname + " " + dob;
    }
    // getter method for first name
    public String getFname(){
        return fname;
    }
    // getter method for last name
    public String getLname(){
        return lname;
    }
    // getter method for date of birth
    public Date getDob(){
        return dob;
    }

    // comparison method for comparing two profiles
    @Override
    public int compareTo (Profile other){
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
    // method to check if two profiles are equal
    @Override
    public boolean equals (Object obj){
        if(this == obj){
            return true;
        }
        Profile temp = (Profile) obj;
        if(this.compareTo(temp) == 0){
            return true;
        }
        return false;
    }
    // Testbed for Profile class
    public static void main(String[] args) {
        System.out.println("Testing Profile class:");

        // Creating profiles
        Profile profile1 = new Profile("John", "Doe", new Date(1985, 1, 15));
        Profile profile2 = new Profile("Jane", "Doe", new Date(1990, 5, 20));
        Profile profile3 = new Profile("John", "Doe", new Date(1985, 1, 15)); // Same DOB as profile1

        // Comparing profiles
        System.out.println("Comparing " + profile1 + " and " + profile2 + ": " + profile1.compareTo(profile2)); // Should be > 0
        System.out.println("Comparing " + profile2 + " and " + profile1 + ": " + profile2.compareTo(profile1)); // Should be < 0
        System.out.println("Comparing " + profile1 + " and " + profile3 + ": " + profile1.compareTo(profile3)); // Should be 0

        // Output for profiles
        System.out.println("Profile 1: " + profile1);
        System.out.println("Profile 2: " + profile2);
        System.out.println("Profile 3: " + profile3);

        // Ensuring profiles are equal
        System.out.println("Profile1 equals Profile3: " + profile1.equals(profile3)); // Should be true
    }



}
