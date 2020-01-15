package uk.co.evoco.webdriver.utils.data;

import net.andreinc.mockneat.abstraction.MockUnitBase;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Dates extends MockUnitBase {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Returns a string that represents the date that is the given days
     * ahead of the start date
     * @param startDate
     * @param daysToAdd
     * @return String
     */
    public static String futureDate(String startDate, int daysToAdd) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        LocalDate date = DateTime.parse(startDate, dateTimeFormatter).plusDays(daysToAdd).toLocalDate();
        return date.toString(dateTimeFormatter);
    }

    /**
     * Returns a string that represents the date that is the given days
     * behind of the start date
     * @param startDate
     * @param daysToRemove
     * @return String
     */
    public static String pastDate(String startDate, int daysToRemove) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DATE_FORMAT);
        LocalDate date = DateTime.parse(startDate, dateTimeFormatter).minusDays(daysToRemove).toLocalDate();
        return date.toString(dateTimeFormatter);
    }
}
