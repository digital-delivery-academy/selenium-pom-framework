package uk.co.evoco.webdriver.utils;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TolerantExceptionHandlerTests {

    @Test
    void testThrowsInputExceptionIfNoTolerantExceptionsAreListed() {
        TolerantExceptionHandler handler = new TolerantExceptionHandler(new ArrayList<>());
        NumberFormatException inputException = new NumberFormatException();
        assertThrows(NumberFormatException.class, () -> handler.propagateIfNotIgnored(inputException));
    }

    @Test
    void testThrowsInputExceptionIfNotOnListOfTolerantExceptions() {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add(NullPointerException.class.getName());
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        NumberFormatException inputException = new NumberFormatException();
        assertThrows(NumberFormatException.class, () -> handler.propagateIfNotIgnored(inputException));
    }

    @Test
    void testReturnsInputExceptionIfOnListOfTolerantExceptions() throws Throwable {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add(NullPointerException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        ArithmeticException inputException = new ArithmeticException();
        assertEquals(inputException, handler.propagateIfNotIgnored(inputException));
    }

    @Test
    void testIgnoresInvalidValuesInTolerantExceptionList () throws Throwable {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add("NonExistentClass");
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        ArithmeticException inputException = new ArithmeticException();
        assertEquals(inputException, handler.propagateIfNotIgnored(inputException));
    }

    @Test
    void testIgnoresClassesInTolerantExceptionListThatAreNotThrowable () throws Throwable {
        List<String> tolerantExceptions = new ArrayList<>();
        tolerantExceptions.add("java.lang.String");
        tolerantExceptions.add(InvalidArgumentException.class.getName());
        tolerantExceptions.add(ArithmeticException.class.getName());
        TolerantExceptionHandler handler = new TolerantExceptionHandler(tolerantExceptions);

        ArithmeticException inputException = new ArithmeticException();
        assertEquals(inputException, handler.propagateIfNotIgnored(inputException));
    }
}
