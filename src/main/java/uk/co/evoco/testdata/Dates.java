package uk.co.evoco.testdata;

import net.andreinc.mockneat.abstraction.MockUnitBase;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import uk.co.evoco.webdriver.utils.JsonUtils;

import java.io.IOException;

import static io.restassured.RestAssured.get;

public class Dates extends MockUnitBase {

    /**
     * Returns a string that represents the date that is the given days
     * ahead of the start date
     * @param startDate start date
     * @param daysToAdd days to add to start date
     * @param dateFormat date format (e.g. "dd/MM/yyyy","dd/MM/yyyy HH:mm")
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
     * @param dateFormat date format (e.g. "dd/MM/yyyy","dd/MM/yyyy HH:mm")
     * @return String representing resulting date
     */
    public static String pastDate(String startDate, int daysToRemove, String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        DateTime dateTime = DateTime.parse(startDate, dateTimeFormatter).minusDays(daysToRemove).toDateTime();
        return dateTime.toString(dateTimeFormatter);
    }

    /**
     * Returns the current date for today
     * @param dateFormat date format (e.g. "dd/MM/yyyy","dd/MM/yyyy HH:mm")
     * @return String representing resulting date
     */
    public static String now(String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        DateTime dateTime = DateTime.now().toDateTime();
        return dateTime.toString(dateTimeFormatter);
    }

    /**
     * Returns boolean result on if the two given dates are within the given tolerance
     * @param dateFormat
     * @param baseDate
     * @param comparisonDate
     * @param toleranceInMinutes
     * @return
     */
    public static boolean isDateWithinTolerance(String dateFormat, String baseDate, String comparisonDate, long toleranceInMinutes) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        long differenceInMillis = DateTime.parse(comparisonDate, dateTimeFormatter).getMillis() - DateTime.parse(baseDate, dateTimeFormatter).getMillis();
        long differenceInMinutes = (differenceInMillis/1000)/60;
        return differenceInMinutes <= toleranceInMinutes;
    }

    /**
     *
     * @param startDate the date to start with
     * @param numberOfBusinessDaysToAdd Days to add, avoiding weekends
     * @param dateFormat date format (e.g. "dd/MM/yyyy","dd/MM/yyyy HH:mm"")
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
     * @param dateFormat date format (e.g. "dd/MM/yyyy","dd/MM/yyyy HH:mm")
     * @return String representing resulting date
     * @throws IOException if the JSON source for bank holidays cannot be read
     */
    public static String futureDataAvoidingWeekendsAndBankHolidays(
            Locale locale, String startDate,
            int numberOfBusinessDaysToAdd, String dateFormat) throws IOException {
        BankHolidays bankHolidays = getBankHolidays();
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        DateTime now = DateTime.parse(startDate, dateTimeFormatter).toDateTime();
        DateTime futureDateTime = DateTime.parse(
                futureDateAvoidingWeekends(
                        startDate,
                        numberOfBusinessDaysToAdd,
                        dateFormat), dateTimeFormatter).toDateTime();
        int bankHolidayCount = 0;
        for(BankHoliday bankHoliday : bankHolidays.get(locale)) {
            if (!bankHoliday.getLocalDate().isBefore(new LocalDate(now)) && !bankHoliday.getLocalDate().isAfter(new LocalDate(futureDateTime))) {
                bankHolidayCount++;
            }
        }
        futureDateTime=DateTime.parse(
                futureDateAvoidingWeekends(
                        futureDateTime.toString(dateTimeFormatter),
                        bankHolidayCount,
                        dateFormat), dateTimeFormatter).toDateTime();
        return futureDateTime.toString(dateTimeFormatter);
    }

    private static BankHolidays getBankHolidays() throws IOException {
        BankHolidays bankHolidays;
        try {
            bankHolidays = JsonUtils.fromString(
                    get("https://www.gov.uk/bank-holidays.json").body().asString(), BankHolidays.class);
        } catch(Exception e) {
            bankHolidays = JsonUtils.fromFile(ClassLoader.getSystemResourceAsStream("bank-holidays.json"), BankHolidays.class);
        }
        return bankHolidays;
    }
}
