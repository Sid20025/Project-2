/**
 * The Date class represents a calendar date with day, month, and year fields.
 * It provides methods for validating whether a date is correctly formatted, checking
 * for leap years, determining whether a date is in the past or future, and comparing
 * two dates. The class also supports operations to ensure that the date is valid within
 * specific business rules, such as no appointments on weekends or restricting dates to
 * a six-month window.
 *
 * @author Siddharth Aggarwal, Michael Durugo
 */
package util;

import java.util.Calendar;

public class Date implements Comparable<Date> {
    // Instance variables to represent the day, month, and year of the date.
    private int date;
    private int month;
    private int year;

    // Constants used to check leap year conditions.
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAl = 400;

    // Constructor method for creating a Date instance.
    public Date(int year, int month, int date) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    // Getter methods for retrieving the day, month, and year.
    public int getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Method to validate if the date is valid.
    public boolean isValid() {
        // Check if the month and date are within valid ranges.
        if (month < 1 || month > 12 || date < 1 || date > 31) {
            return false;
        }

        // Validate if the given month can have 31 days.
        if (date == 31) {
            if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
                return false;
            }
        }

        // Check if February has 30 days, which is invalid.
        if (date == 30 && month == 2) {
            return false;
        }

        // Check if February 29th is valid (i.e., a leap year).
        if (month == 2 && date == 29) {
            return isLeapYear(year);
        }

        return true;
    }

    // Static method to create a Date from a string in MM/DD/YYYY format.
    public static Date fromString(String dateStr) {
        try {
            String[] parts = dateStr.split("/");
            int month = Integer.parseInt(parts[0]);
            int day = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);
            return new Date(year, month, day);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Private method to check if a year is a leap year.
    private boolean isLeapYear(int year) {
        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUATERCENTENNIAl == 0;
            }
            return true;  // Divisible by 4 but not divisible by 100.
        }
        return false;  // Not divisible by 4.
    }

    // Method to check if the date is a past date.
    public boolean isPastDate() {
        Calendar today = Calendar.getInstance(); // Get today's date.

        // Get the current year, month, and day.
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1; // Calendar.MONTH is 0-based.
        int currentDay = today.get(Calendar.DAY_OF_MONTH);

        // Compare the year first.
        if (year < currentYear) {
            return true;
        } else if (year == currentYear) {
            // If the year is the same, compare the month.
            if (month < currentMonth) {
                return true;
            } else if (month == currentMonth) {
                // If the month is the same, compare the day.
                return date < currentDay;
            }
        }

        return false; // The date is today or in the future.
    }

    // Method to check if the date falls on a weekday.
    public boolean isWeekday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date); // Calendar.MONTH is 0-based.
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

    // Method to check if the date is within the next six months.
    public boolean isWithinSixMonths() {
        Calendar today = Calendar.getInstance();  // Get today's date.
        Calendar sixMonthsFromNow = Calendar.getInstance();
        sixMonthsFromNow.add(Calendar.MONTH, 6); // Add six months to today's date.

        Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.set(year, month - 1, date); // Set the appointment date.

        // Check if the appointment date is between now and six months from now.
        return appointmentDate.after(today) && appointmentDate.before(sixMonthsFromNow);
    }

    // Comparing two dates using Comparable interface.
    @Override
    public int compareTo(Date b) {
        // Compare by year.
        if (this.year < b.year) {
            return -1;
        } else if (this.year > b.year) {
            return 1;
        }

        // If years are equal, compare by month.
        if (this.month < b.month) {
            return -1;
        } else if (this.month > b.month) {
            return 1;
        }

        // If months are equal, compare by date.
        if (this.date < b.date) {
            return -1;
        } else if (this.date > b.date) {
            return 1;
        }

        return 0; // Dates are equal.
    }

    // Method to check if two dates are equal.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Date)) {
            return false;
        }
        Date temp = (Date) o;
        return this.compareTo(temp) == 0;
    }

    // Method to convert a date to String format (MM/DD/YYYY).
    @Override
    public String toString() {
        return month + "/" + date + "/" + year;
    }

    // Testbed for the Date class to test various scenarios.

}
