package util;

import org.junit.Test;
import p2.*;

import static org.junit.Assert.*;


/**
 * Tests the add and remove methods in the List class.

 */
public class ListTest {
    /**
     * Creates a new doctor and verifies that the add method adds it to the list.
     */
    @Test
    public void testAdd() {
        List<Provider> providers = new List<>();
        Doctor testDoctor = new Doctor(new Profile("Olivia", "Jones", new Date(2024, 10, 10)),  // Year: 2024, Month: 10, Day: 10
                Location.PISCATAWAY, Specialty.ALLERGIST, "01");
        assertFalse(providers.contains(testDoctor));
        providers.add(testDoctor);
        assertTrue(providers.contains(testDoctor));
    }

    /**
     * Creates a new technician and verifies that the add method adds it to the list.
     */
    @Test
    public void testAddT() {
        List<Provider> providers = new List<>();
        Technician testTech = new Technician(new Profile("Lucas", "Green", new Date(2024, 2, 10)),  // Year: 2024, Month: 2, Day: 10
                Location.PISCATAWAY, 200);
        assertFalse(providers.contains(testTech));
        providers.add(testTech);
        assertTrue(providers.contains(testTech));
    }

    /**
     * Creates a new doctor and verifies that the remove method removes it from the list.
     */
    @Test
    public void testRemove() {
        List<Provider> providers = new List<>();
        Doctor testDoctor = new Doctor(new Profile("Sophia", "Williams", new Date(2024, 5, 8)),  // Year: 2024, Month: 5, Day: 8
                Location.PISCATAWAY, Specialty.ALLERGIST, "01");
        providers.add(testDoctor);
        assertTrue(providers.contains(testDoctor));
        providers.remove(testDoctor);
        assertFalse(providers.contains(testDoctor));
    }

    /**
     * Creates a new technician and verifies that the remove method removes it from the list.
     */
    @Test
    public void testRemoveT() {
        List<Provider> providers = new List<>();
        Technician testTech = new Technician(new Profile("Mason", "Brown", new Date(2024, 5, 8)),  // Year: 2024, Month: 5, Day: 8
                Location.PISCATAWAY, 100);
        providers.add(testTech);
        assertTrue(providers.contains(testTech));
        providers.remove(testTech);
        assertFalse(providers.contains(testTech));
    }
}

