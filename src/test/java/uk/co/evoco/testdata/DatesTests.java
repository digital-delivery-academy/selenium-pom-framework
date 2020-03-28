package uk.co.evoco.testdata;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.evoco.testdata.Dates.*;

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
                futureDateAvoidingWeekends("06/06/2019", 5, "dd/MM/yyyy"),
                is("13/06/2019"));
    }

    @Test
    public void testCanGetDateInFutureAvoidingWeekendsAndBankHolidays() throws IOException {
        assertThat(futureDataAvoidingWeekendsAndBankHolidays(
                Locale.ENGLAND_AND_WALES, "28/08/2017", 2, "dd/MM/yyyy"),
                is("31/08/2017"));
    }

    @Test
    public void testCanGetDateInFutureAvoidingWeekendsAndBankHolidaysWhenStartDateIsOnFriday()
            throws IOException {
        assertThat(futureDataAvoidingWeekendsAndBankHolidays(
                Locale.ENGLAND_AND_WALES, "25/08/2017", 2, "dd/MM/yyyy"),
                is("30/08/2017"));
    }

    @Test
    public void testCanGetDateTimeInFuture() {
        assertThat(futureDate("01/06/2019 12:34", 5, "dd/MM/yyyy HH:mm"), is("06/06/2019 12:34"));
    }

    @Test
    public void testCanGetDateTimeInPast() {
        assertThat(
                pastDate("06/06/2019 11:45", 5, "dd/MM/yyyy HH:mm"), is("01/06/2019 11:45"));
    }

    @Test
    public void testCanDataTimeInFutureAvoidingWeekend() {
        assertThat(
                futureDateAvoidingWeekends("06/06/2019 13:54", 5, "dd/MM/yyyy HH:mm"),
                is("13/06/2019 13:54"));
    }

}
