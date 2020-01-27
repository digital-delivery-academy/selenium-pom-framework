package uk.co.evoco.webdriver.utils.data;

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
        LocalDate date = DateTime.parse(startDate, dateTimeFormatter).plusDays(daysToAdd).toLocalDate();
        return date.toString(dateTimeFormatter);
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
        LocalDate date = DateTime.parse(startDate, dateTimeFormatter).minusDays(daysToRemove).toLocalDate();
        return date.toString(dateTimeFormatter);
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
    public static String futureDateBusinessDays(String startDate, int numberOfBusinessDaysToAdd, String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
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

    /**
     *
     * @param locale the location for UK bank holidays (see @link uk.co.evoco.webdriver.utils.data.Dates)
     * @param startDate the date to start with
     * @param numberOfBusinessDaysToAdd Days to add, avoiding weekends and UK bank holidays
     * @param dateFormat date format (e.g. "dd/MM/yyyy")
     * @return String representing resulting date
     * @throws JsonProcessingException if the JSON source for bank holidays cannot be read
     */
    public static String futureDateBusinessDaysAvoidingBankHolidays(
            Locale locale, String startDate,
            int numberOfBusinessDaysToAdd, String dateFormat) throws JsonProcessingException {
        BankHolidays bankHolidays = JsonUtils.fromString(
                get("https://www.gov.uk/bank-holidays.json").body().asString(), BankHolidays.class);
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        LocalDate now = DateTime.parse(startDate, dateTimeFormatter).toLocalDate();
        LocalDate futureDate = DateTime.parse(
                futureDateBusinessDays(
                        startDate,
                        numberOfBusinessDaysToAdd,
                        dateFormat), dateTimeFormatter).toLocalDate();
        int bankHolidayCount = 0;
        for(BankHoliday bankHoliday : bankHolidays.get(locale)) {
            if (!bankHoliday.getLocalDate().isBefore(now) && !bankHoliday.getLocalDate().isAfter(futureDate)) {
                bankHolidayCount++;
            }
        }
        futureDate = futureDate.plusDays(bankHolidayCount);
        return futureDate.toString(dateTimeFormatter);
    }
}
