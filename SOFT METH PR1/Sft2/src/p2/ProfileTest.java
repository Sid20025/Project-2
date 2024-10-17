package p2;


import static org.junit.Assert.*;

import org.junit.Test;
import util.Date;


/**
 * Tests the Profile's compareTo method, with code reused from project 1.
 */
public class ProfileTest {
    private static final int EQUAL = 0, BEFORE = -1, AFTER = 1;

    /**
     * Tests that two profiles with the same first name, last name, and DOB are the same.
     */
    @Test
    public void areEqualProfiles() {
        Profile p1 = new Profile("Alice", "Smith", new Date(2021, 4, 3));  // Year: 2021, Month: 4, Date: 3
        Profile p2 = new Profile("Alice", "Smith", new Date(2021, 4, 3));  // Year: 2021, Month: 4, Date: 3
        assertEquals(EQUAL, p1.compareTo(p2));
    }

    /**
     * Tests that p1 comes before p2, if p1.lastname comes before p2.lastname.
     */
    @Test
    public void areLastNameComesBefore() {
        Profile p1 = new Profile("Michael", "Anderson", new Date(2021, 3, 4));  // Year: 2021, Month: 3, Date: 4
        Profile p2 = new Profile("David", "Baker", new Date(2022, 4, 6));  // Year: 2022, Month: 4, Date: 6
        assertEquals(BEFORE, p1.compareTo(p2));
    }

    /**
     * Tests that p1 comes before p2, if p1.firstname comes before p2.firstname.
     */
    @Test
    public void areFirstNameComesBefore() {
        Profile p1 = new Profile("Emily", "Carter", new Date(2021, 4, 3));  // Year: 2021, Month: 4, Date: 3
        Profile p2 = new Profile("Olivia", "Carter", new Date(2022, 6, 4));  // Year: 2022, Month: 6, Date: 4
        assertEquals(BEFORE, p1.compareTo(p2));
    }

    /**
     * Tests that p1 comes before p2, if p1.dob comes before p2.dob.
     */
    @Test
    public void aretestDOBComesBefore() {
        Profile p1 = new Profile("Noah", "Davis", new Date(2022, 6, 4));  // Year: 2022, Month: 6, Date: 4
        Profile p2 = new Profile("Noah", "Davis", new Date(2022, 23, 4));  // Year: 2022, Month: 23, Date: 4
        assertEquals(BEFORE, p1.compareTo(p2));
    }

    /**
     * Tests that p1 comes after p2, if p1.lastname comes after p2.lastname.
     */
    @Test
    public void aretestLastNameComesAfter() {
        Profile p1 = new Profile("Sophia", "Miller", new Date(2022, 23, 3));  // Year: 2022, Month: 23, Date: 3
        Profile p2 = new Profile("Sophia", "Johnson", new Date(2019, 23, 6));  // Year: 2019, Month: 23, Date: 6
        assertEquals(AFTER, p1.compareTo(p2));
    }

    /**
     * Tests that p1 comes after p2, if p1.firstname comes after p2.firstname.
     */
    @Test
    public void aretestFirstNameComesAfter() {
        Profile p1 = new Profile("Zara", "Moore", new Date(2019, 23, 6));  // Year: 2019, Month: 23, Date: 6
        Profile p2 = new Profile("Ethan", "Moore", new Date(2013, 11, 12));  // Year: 2013, Month: 11, Date: 12
        assertEquals(AFTER, p1.compareTo(p2));
    }

    /**
     * Tests that p1 comes after p2, if p1.dob comes after p2.dob.
     */
    @Test
    public void aretestDOBComesAfter() {
        Profile p1 = new Profile("Liam", "Taylor", new Date(2022, 23, 4));  // Year: 2022, Month: 23, Date: 4
        Profile p2 = new Profile("Liam", "Taylor", new Date(2022, 23, 3));  // Year: 2022, Month: 23, Date: 3
        assertEquals(AFTER, p1.compareTo(p2));
    }
}




