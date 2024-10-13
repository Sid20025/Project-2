/**
 *
 The Date class represents a calendar date with day, month, and year fields.
 * It provides methods for validating whether a date is correctly formatted, checking
 * for leap years, determining whether a date is in the past or future, and comparing
 * two dates. The class also supports operations to ensure that the date is valid within
 * specific business rules, such as no appointments on weekends or restricting dates to
 * a six-month window.
 * @author Siddharth Aggarwal, Michael Durugo
 */
package clinic;
import java.util.Calendar;

public class Date implements Comparable <Date>{
    private int date;
    private int month;
    private int year;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAl = 400;
    //Constructor methods for Date
    public Date (int year,int month, int date ){
        this.date = date;
        this.month = month;
        this.year = year;
    }
    //Getter method for date month and year
    public int getDate(){
        return date;
    }
    public int getMonth(){
        return month;
    }
    public int getYear(){
        return year;
    }
    //Checking if a date isValid
    public boolean isValid(){
        if(month < 1 || month > 12 || date < 1 || date > 31){ //checking if the month and date is valid
            return false;
        }
        if(date == 31){
            if(month == 2||month == 4||month == 6||month == 9||month == 11){ //checking if a month is valid for 31 days
                return false;
            }
        }
        if(date == 30 && month == 2){ // February cannot have 30 days
                return false;
        }
        if(month == 2 && date == 29){ //Chacking if a year is a leap year
            return isLeapYear(year);
        }

        return true;
    }
    // private method for checking the validity of a leap year
    private boolean isLeapYear(int year) {

        if (year % QUADRENNIAL == 0) {
            if (year % CENTENNIAL == 0) {
                return year % QUATERCENTENNIAl == 0;
            }
            return true;  // Divisible by 4 and not divisible by 100
        }
        return false;  // Not divisible by 4
    }

    //Checking if the date is not today's or a date before today
    public boolean isPastDate() {
        Calendar today = Calendar.getInstance(); // Getting today's date

        // Getting current year, month, and day
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentDay = today.get(Calendar.DAY_OF_MONTH);

        // Comparing the year first
        if (year < currentYear) {
            return true;
        } else if (year == currentYear) {
            // If the year is the same, comparing the month
            if (month < currentMonth) {
                return true; // The date is in a previous month this year
            } else if (month == currentMonth) {
                // If the month is the same, comparing the day
                return date < currentDay; // The date is earlier in the current month
            }
        }

        return false; // The date is today or in the future
    }

    //Checking if the date falls on a weekday and not Saturday/Sunday
    public boolean isWeekday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
    }

    // Checking if the date is within six months from now
    public boolean isWithinSixMonths() {
        Calendar today = Calendar.getInstance();  // Getting today's date
        Calendar sixMonthsFromNow = Calendar.getInstance();
        sixMonthsFromNow.add(Calendar.MONTH, 6);

        Calendar appointmentDate = Calendar.getInstance();
        appointmentDate.set(year, month - 1, date);

        // Checking if the appointment date is between now and six months from now
        return appointmentDate.after(today) && appointmentDate.before(sixMonthsFromNow);
    }
    // Comparing two dates
    @Override
    public int compareTo (Date b){
        if(this.year < b.year){
            return -1;
        }
        else{
            if(this.year>b.year){
                return 1;
            }
        }
        if (this.month < b.month) {
            return -1;
        }
        if(this.month>b.month){
            return 1;
        }
        if(this.date < b.date){
            return -1;
        }
        if(this.date>b.date){
            return 1;
        }
        return 0;
    }
    //Checking if two dates are equal
    @Override
    public boolean equals(Object o){
       if(this == o){
           return true;
       }
       Date temp = (Date) o;
       if(this.compareTo(temp) == 0){
           return true;
       }
       return false;
    }
    //Converting a date to String
    @Override
    public String toString() {
        return month + "/" + date + "/" + year;
    }
    // Testbed for Date class
    public static void main(String[] args) {
        System.out.println("Testing Date class:");

        // Valid date (Leap Year)
        Date date1 = new Date(2024, 2, 29);
        System.out.println("Date: " + date1 + ", Is valid: " + date1.isValid());

        // Invalid date (Not a leap year)
        Date date2 = new Date(2023, 2, 29);
        System.out.println("Date: " + date2 + ", Is valid: " + date2.isValid());

        // Invalid date (31st February)
        Date date3 = new Date(2023, 2, 31);
        System.out.println("Date: " + date3 + ", Is valid: " + date3.isValid());

        // Valid date (Regular Year)
        Date date4 = new Date(2023, 12, 15);
        System.out.println("Date: " + date4 + ", Is valid: " + date4.isValid());

        // Past date
        Date date5 = new Date(2020, 1, 1);
        System.out.println("Date: " + date5 + ", Is past date: " + date5.isPastDate());

        // Invalid month
        Date date7 = new Date(2023, 13, 1);
        System.out.println("Date: " + date7 + ", Is valid: " + date7.isValid());

        // Invalid day
        Date date8 = new Date(2023, 4, 31);
        System.out.println("Date: " + date8 + ", Is valid: " + date8.isValid());
    }


}
