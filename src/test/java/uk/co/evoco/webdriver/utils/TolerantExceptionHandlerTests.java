package uk.co.evoco.webdriver.utils;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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
}
