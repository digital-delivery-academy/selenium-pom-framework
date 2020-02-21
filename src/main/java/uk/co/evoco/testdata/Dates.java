package uk.co.evoco.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.andreinc.mockneat.abstraction.MockUnitBase;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.co.evoco.webdriver.utils.JsonUtils;

import static io.restassured.RestAssured.get;

public class Dates extends MockUnitBase {

    /**
     * Returns a string that represents the date that is the given days
     * ahead of the start date
     * @param startDate start date
     * @param daysToAdd days to add to start date
     * @param dateFormat date format (e.g. "dd/MM/yyyy")
     * @return String representing resulting date
     */
    public static String futureDate(String startDate, int daysToAdd, String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        DateTime dateTime = DateTime.parse(startDate, dateTimeFormatter).plusDays(daysToAdd).toDateTime();
        return dateTime.toString(dateTimeFormatter);
    }

    /**
     * Returns a string that represents the date that is the given days
     * behind of the start date
     * @param startDate start date
     * @param daysToRemove days to remove from start date
     * @param dateFormat date format (e.g. "dd/MM/yyyy")
     * @return String representing resulting date
     */
    public static String pastDate(String startDate, int daysToRemove, String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        DateTime dateTime = DateTime.parse(startDate, dateTimeFormatter).minusDays(daysToRemove).toDateTime();
        return dateTime.toString(dateTimeFormatter);
    }

    /**
     * Returns the current date for today
     * @param dateFormat date format (e.g. "dd/MM/yyyy")
     * @return String representing resulting date
     */
    public static String now(String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        LocalDate date = DateTime.now().toLocalDate();
        return date.toString(dateTimeFormatter);
    }

    /**
     *
     * @param startDate the date to start with
     * @param numberOfBusinessDaysToAdd Days to add, avoiding weekends
     * @param dateFormat date format (e.g. "dd/MM/yyyy")
     * @return String representing resulting date
     */
    public static String futureDateAvoidingWeekends(String startDate, int numberOfBusinessDaysToAdd, String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        DateTime futureDateTime = DateTime.parse(startDate, dateTimeFormatter).toDateTime();
        int addedDays = 0;
        while (addedDays < numberOfBusinessDaysToAdd) {
            futureDateTime = futureDateTime.plusDays(1);
            if (!((futureDateTime.getDayOfWeek() == 6) || (futureDateTime.getDayOfWeek() == 7))) {
                ++addedDays;
            }
        }
        return futureDateTime.toString(dateTimeFormatter);
    }

    /**
     *
     * @param locale the location for UK bank holidays (see @link Dates)
     * @param startDate the date to start with
     * @param numberOfBusinessDaysToAdd Days to add, avoiding weekends and UK bank holidays
     * @param dateFormat date format (e.g. "dd/MM/yyyy")
     * @return String representing resulting date
     * @throws JsonProcessingException if the JSON source for bank holidays cannot be read
     */
    public static String futureDataAvoidingWeekendsAndBankHolidays(
            Locale locale, String startDate,
            int numberOfBusinessDaysToAdd, String dateFormat) throws JsonProcessingException {
        BankHolidays bankHolidays = JsonUtils.fromString(
                get("https://www.gov.uk/bank-holidays.json").body().asString(), BankHolidays.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        DateTime now = DateTime.parse(startDate, dateTimeFormatter).toDateTime();
        DateTime futureDateTime = DateTime.parse(
                futureDateAvoidingWeekends(
                        startDate,
                        numberOfBusinessDaysToAdd,
                        dateFormat), dateTimeFormatter).toDateTime();
        int bankHolidayCount = 0;
        for(BankHoliday bankHoliday : bankHolidays.get(locale)) {
            if (!bankHoliday.getLocalDateTime().isBefore(now) && !bankHoliday.getLocalDateTime().isAfter(futureDateTime)) {
                bankHolidayCount++;
            }
        }
        futureDateTime = futureDateTime.plusDays(bankHolidayCount);
        return futureDateTime.toString(dateTimeFormatter);
    }
}
