package uk.co.evoco.webdriver.utils.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.evoco.webdriver.utils.data.Dates.*;

public class DatesTests {

    @Test
    public void testCanGetDateInFuture() {
        assertThat(futureDate("01/06/2019", 5, "dd/MM/yyyy"), is("06/06/2019"));
    }

    @Test
    public void testCanGetDateInPast() {
        assertThat(
                pastDate("06/06/2019", 5, "dd/MM/yyyy"), is("01/06/2019"));
    }

    @Test
    public void testCanDataInFutureAvoidingWeekend() {
        assertThat(
                futureDateBusinessDays("06/06/2019", 5, "dd/MM/yyyy"),
                is("13/06/2019"));
    }

    @Test
    public void testCanGetDateInFutureAvoidingWeekendsAndBankHolidays() throws JsonProcessingException {
        assertThat(futureDateBusinessDaysAvoidingBankHolidays(
                Locale.ENGLAND_AND_WALES, "28/08/2017", 2, "dd/MM/yyyy"),
                is("31/08/2017"));
    }

    @Test
    public void testCanGetDateInFutureAvoidingWeekendsAndBankHolidaysWhenStartDateIsOnFriday()
            throws JsonProcessingException {
        assertThat(futureDateBusinessDaysAvoidingBankHolidays(
                Locale.ENGLAND_AND_WALES, "25/08/2017", 2, "dd/MM/yyyy"),
                is("30/08/2017"));
    }
}
