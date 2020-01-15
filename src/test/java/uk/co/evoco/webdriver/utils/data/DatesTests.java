package uk.co.evoco.webdriver.utils.data;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.evoco.webdriver.utils.data.Dates.futureDate;
import static uk.co.evoco.webdriver.utils.data.Dates.pastDate;

public class DatesTests {

    @Test
    public void testCanGetDateInFuture() {
        assertThat(futureDate("01/06/2019", 5), is("06/06/2019"));
    }

    @Test
    public void testCanGetDateInPast() {
        assertThat(pastDate("06/06/2019", 5), is("01/06/2019"));
    }
}
