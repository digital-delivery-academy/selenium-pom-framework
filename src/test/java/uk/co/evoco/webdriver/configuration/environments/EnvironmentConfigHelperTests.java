package uk.co.evoco.webdriver.configuration.environments;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnvironmentConfigHelperTests {

    @Test
    public void testEnvironmentVariablesSet() {
        System.setProperty("ENVIRONMENT_CONFIG", "environment.json");
        assertThat(EnvironmentConfigHelper.get("sample"), is("Hello world"));
    }

    @Test
    public void testEnvironmentVariablesNotFound() {
        System.setProperty("ENVIRONMENT_CONFIG", "does_not_exist.json");
        assertThrows(Exception.class, () -> {
            EnvironmentConfigHelper.get("random");
        });
    }
}
