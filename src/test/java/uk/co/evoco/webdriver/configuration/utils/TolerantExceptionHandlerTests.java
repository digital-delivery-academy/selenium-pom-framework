package uk.co.evoco.webdriver.configuration.utils;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import uk.co.evoco.webdriver.utils.TolerantExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TolerantExceptionHandlerTests {

    @Test
    public void testShouldNotPropagateExceptionOnElementClickInterceptedException() throws Throwable {
        List<String> exceptions = new ArrayList<>();
        exceptions.add("ElementClickInterceptedException");
        TolerantExceptionHandler tolerantExceptionHandler = new TolerantExceptionHandler(exceptions);
        try {
            tolerantExceptionHandler.propagateIfNotIgnored(new ElementClickInterceptedException("Hello"));
            assertTrue(true);
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    public void testShouldNotPropagateExceptionOnStaleElementException() throws Throwable {
        List<String> exceptions = new ArrayList<>();
        exceptions.add("StaleElementReferenceException");
        TolerantExceptionHandler tolerantExceptionHandler = new TolerantExceptionHandler(exceptions);
        try {
            tolerantExceptionHandler.propagateIfNotIgnored(new StaleElementReferenceException("Hello"));
            assertTrue(true);
        } catch(Exception e) {
            fail();
        }
    }

    @Test
    public void testShouldPropagateExceptionWhenUnToleratedExceptionDetected() throws Throwable {
        List<String> exceptions = new ArrayList<>();
        exceptions.add("StaleElementReferenceException");
        TolerantExceptionHandler tolerantExceptionHandler = new TolerantExceptionHandler(exceptions);
        assertThrows(Throwable.class, () -> {
            tolerantExceptionHandler.propagateIfNotIgnored(new Exception("Hello"));
        });
    }
}
