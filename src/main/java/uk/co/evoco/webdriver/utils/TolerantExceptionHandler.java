package uk.co.evoco.webdriver.utils;

import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TolerantExceptionHandler {
    private List<String> tolerantExceptions;
    private Logger logger = LoggerFactory.getLogger(TolerantInteraction.class);

    public TolerantExceptionHandler(List<String> tolerantExceptions) {
        this.tolerantExceptions = tolerantExceptions;
    }

    public TolerantExceptionHandler(List<String> tolerantExceptions, Logger logger) {
        this(tolerantExceptions);
        this.logger = logger;
    }

    /**
     * Checks an input exception against a predefined list of tolerant exceptions. It either:
     * <ol>
     *     <li>returns the exception (if it's on the tolerant list) or</li>
     *     <li>throws the exception if it's not on the tolerant list.</li>
     * </ol>
     * @param throwable the throwable that needs to be handled
     * @return the input exception if is on the tolerant exceptions list
     * @throws Throwable the input exception if it's not on the tolerant exceptions list
     */
    public Throwable propagateIfNotIgnored(Throwable throwable) throws Throwable {
        for (String tolerantExceptionClassName : tolerantExceptions) {
            if (isInstanceOf(tolerantExceptionClassName, throwable)) {
                logger.info("Exception {} will be ignored", tolerantExceptionClassName);
                return throwable;
            } else {
                logger.error("Un-tolerated Exception {} encountered during tolerant action attempt", throwable.getClass().getName());
            }
        }

        throw throwable;
    }

    private boolean isInstanceOf (String source, Throwable target) {
        return target.getClass().getName().contains(source);
    }
}
