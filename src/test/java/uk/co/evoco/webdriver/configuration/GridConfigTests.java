package uk.co.evoco.webdriver.configuration;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GridConfigTests {

    @Test
    public void testSettingBaseUrlWithBadGridUrlGivesGoodException() {
        GridConfig gridConfig = new GridConfig();
        assertThrows(MalformedURLException.class, () -> {
            gridConfig.setGridUrl("bad-url");
        });
    }

    @Test
    public void testSettingBaseUrlWithValidGridUrl() throws MalformedURLException {
        GridConfig gridConfig = new GridConfig();
        gridConfig.setGridUrl("http://localhost:4444/wd/hub");
        assertThat(gridConfig.getGridUrl().toString(), is("http://localhost:4444/wd/hub"));
    }
}
