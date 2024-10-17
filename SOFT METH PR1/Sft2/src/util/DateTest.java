package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the Date isValid method, with code reused from project 1.
 */
public class DateTest {
    /**
     * Tests a date where the month is 0.
     */
    @Test
    public void testZeroMonthDate() {
        Date date = new Date(2024, 10, 0);
        assertFalse(date.isValid());
    }

    /**
     * Test a date where the month is 13.
     */
    @Test
    public void testThirteenMonthDate() {
        Date date = new Date(2024, 13, 10);
        assertFalse(date.isValid());
    }

    /**
     * Tests a date where a month has the incorrect number of days (like February having 30 days).
     */
    @Test
    public void testIncorrectNumberDaysInMonth() {
        Date date = new Date(2024, 2, 30);
        assertFalse(date.isValid());
    }

    /**
     * Tests a February 29th, with a year that is NOT a leap year.
     */
    @Test
    public void testIncorrectLeapYear() {
        Date date = new Date(2023, 2, 29);
        assertFalse(date.isValid());
    }

    /**
     * Tests that a date that should be valid, is valid.
     */
    @Test
    public void testCorrectDate() {
        Date date = new Date(2024, 6, 30);
        assertTrue(date.isValid());
    }

    /**
     * Tests a February 29th when it is a leap year
     */
    @Test
    public void testCorrectLeapYearDate() {
        Date date = new Date(2016, 2, 29);
        assertTrue(date.isValid());
    }
}