package uk.co.evoco.webdriver.utils;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

public class TolerantExceptionHandlerTests {

    @Test
    public void testThrowsInputExceptionIfNoTolerantExceptionsAreListed() {
        TolerantExceptionHandler handler = new TolerantExceptionHandler(new ArrayList<>());
        NumberFormatException inputException = new NumberFormatException();
        assertThrows(NumberFormatException.class, () -> handler.propagateIfNotIgnored(inputException));
    }

    @Test
    public void testThrowsInputExceptionIfNotOnListOfTolerantExceptions() {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add(NullPointerException.class.getName());
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        NumberFormatException inputException = new NumberFormatException();
        assertThrows(NumberFormatException.class, () -> handler.propagateIfNotIgnored(inputException));
    }

    @Test
    public void testReturnsInputExceptionIfOnListOfTolerantExceptions() throws Throwable {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add(NullPointerException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        ArithmeticException inputException = new ArithmeticException();
        assertEquals(inputException, handler.propagateIfNotIgnored(inputException));
    }

    @Test
    public void testIgnoresInvalidValuesInTolerantExceptionList () throws Throwable {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add("NonExistentClass");
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        ArithmeticException inputException = new ArithmeticException();
        assertEquals(inputException, handler.propagateIfNotIgnored(inputException));
    }

    @Test
    public void testIgnoresClassesInTolerantExceptionListThatAreNotThrowable () throws Throwable {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add("java.lang.String");
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        ArithmeticException inputException = new ArithmeticException();
        assertEquals(inputException, handler.propagateIfNotIgnored(inputException));
    }

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
    public void testShouldNotPropagateExceptionOnStaleElementExceptionWithPacakgeName() throws Throwable {
        List<String> exceptions = new ArrayList<>();
        exceptions.add("org.openqa.selenium.StaleElementReferenceException");
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
