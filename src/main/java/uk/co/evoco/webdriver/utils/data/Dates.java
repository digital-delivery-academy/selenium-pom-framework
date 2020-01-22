package uk.co.evoco.webdriver.utils.data;

import net.andreinc.mockneat.abstraction.MockUnitBase;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.time.DayOfWeek;

public class Dates extends MockUnitBase {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Returns a string that represents the date that is the given days
     * ahead of the start date
     * @param startDate start date
     * @param daysToAdd days to add to start date
     * @return String representing resulting date
     */
    public static String futureDate(String startDate, int daysToAdd) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        LocalDate date = DateTime.parse(startDate, dateTimeFormatter).plusDays(daysToAdd).toLocalDate();
        return date.toString(dateTimeFormatter);
    }

    /**
     * Returns a string that represents the date that is the given days
     * behind of the start date
     * @param startDate start date
     * @param daysToRemove days to remove from start date
     * @return String representing resulting date
     */
    public static String pastDate(String startDate, int daysToRemove) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        LocalDate date = DateTime.parse(startDate, dateTimeFormatter).minusDays(daysToRemove).toLocalDate();
        return date.toString(dateTimeFormatter);
    }

    /**
     * Returns the current date for today
     * @return String representing resulting date
     */
    public static String now() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        LocalDate date = DateTime.now().toLocalDate();
        return date.toString(dateTimeFormatter);
    }

    /**
     *
     * @param startDate the date to start with
     * @param numberOfBusinessDaysToAdd Days to add, avoiding weekends
     * @return String representing resulting date
     */
    public static String futureDateBusinessDays(String startDate, int numberOfBusinessDaysToAdd) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        LocalDate futureDate = DateTime.parse(startDate, dateTimeFormatter).toLocalDate();
        int addedDays = 0;
        while (addedDays < numberOfBusinessDaysToAdd) {
            futureDate = futureDate.plusDays(1);
            if (!((futureDate.getDayOfWeek() == 6) || (futureDate.getDayOfWeek() == 7))) {
                ++addedDays;
            }
        }
        return futureDate.toString(dateTimeFormatter);
    }
}
