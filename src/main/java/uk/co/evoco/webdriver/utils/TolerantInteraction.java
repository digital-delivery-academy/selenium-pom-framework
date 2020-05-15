package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Sleeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class TolerantInteraction {

    private final Logger logger = LoggerFactory.getLogger(TolerantInteraction.class);

    private final Clock clock = Clock.systemDefaultZone();
    private Instant end;
    private final Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
    private final Duration intervalDuration = Duration.ofMillis(500);
    private Throwable lastException = null;
    private TolerantExceptionHandler tolerantExceptionHandler = new TolerantExceptionHandler(
            TestConfigHelper.get().getTolerantActionExceptions().getExceptionsToHandle(),
            logger);

    /**
     *
     * @param webElement active WebElement
     * @param selectBoxInteractionType type of selection for the interaction
     * @param visibleTextOrHtmlValueString Optional - the HTML value of the select box or the visible text
     * @param itemIndex Optional - the index item to select in the select box
     * @param timeoutInSeconds time to continue trying for
     * @return WebElement to allow fluent method stringed calls
     * @throws Throwable the last exception to be thrown
     */
    public WebElement tolerantInteraction(
            WebElement webElement,
            SelectBoxInteractionType selectBoxInteractionType,
            Optional<String> visibleTextOrHtmlValueString,
            Optional<Integer> itemIndex,
            int timeoutInSeconds) throws Throwable {
        end = clock.instant().plusSeconds(timeoutInSeconds);
        while (true) {
            try {
                if(Boolean.TRUE.equals(webElement.isEnabled())) {
                    visibleTextOrHtmlValueString.ifPresent(
                            text -> {
                                interact(new Select(webElement), text, selectBoxInteractionType); });
                    itemIndex.ifPresent(index -> { interact(new Select(webElement), index); });
                    return webElement;
                }
            } catch (Throwable e) {
                lastException = tolerantExceptionHandler.handle(e);
            }

            if (end.isBefore(clock.instant())) {
                if (null == lastException) {
                    logger.error(
                            "Exception condition failed: Timeout (tried for {} seconds with 500ms interval",
                            timeoutInSeconds);
                    lastException = new TimeoutException();
                } else {
                    logger.error("Exception condition failed: {} (tried for {} seconds with 500ms interval",
                            lastException.getCause(), timeoutInSeconds);
                }
                throw lastException;
            }

            try {
                sleeper.sleep(intervalDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    /**
     *
     * @param webElements list of locators we will use to re-lookup the element on retry
     * @param timeoutInSeconds time to continue trying for
     * @return WebElement to allow fluent method stringed calls
     * @throws Throwable the last exception to be thrown
     */
    public WebElement tolerantInteraction(
            List<WebElement> webElements, String visibleText, int timeoutInSeconds) throws Throwable {
        end = clock.instant().plusSeconds(timeoutInSeconds);
        while (true) {
            try {
                for (WebElement webElement : webElements) {
                    if (webElement.getText().equals(visibleText)) {
                        interact(webElement);
                        return webElement;
                    }
                }
            } catch (Throwable e) {
                lastException = tolerantExceptionHandler.handle(e);
            }

            if (end.isBefore(clock.instant())) {
                if (null == lastException) {
                    logger.error(
                            "Exception condition failed: Timeout (tried for {} seconds with 500ms interval",
                            timeoutInSeconds);
                    lastException = new TimeoutException();
                } else {
                    logger.error("Exception condition failed: {} (tried for {} seconds with 500ms interval",
                            lastException.getCause(), timeoutInSeconds);
                }
                throw lastException;
            }

            try {
                sleeper.sleep(intervalDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    /**
     *
     * @param webElement locator we will use to re-lookup the element on retry
     * @param timeoutInSeconds time to continue trying for
     * @return WebElement to allow fluent method stringed calls
     * @throws Throwable the last exception to be thrown
     */
    public WebElement tolerantInteraction(
            WebElement webElement, Optional<String> textToType, int timeoutInSeconds)
            throws Throwable {
        end = clock.instant().plusSeconds(timeoutInSeconds);
        while(true) {
            try {
                if(Boolean.TRUE.equals(webElement.isEnabled())) {
                    textToType.ifPresentOrElse(
                            text -> { interact(webElement, text); },
                            () -> { interact(webElement); });
                    return webElement;
                }
            } catch(Throwable e) {
                lastException = tolerantExceptionHandler.handle(e);
            }
            if(end.isBefore(clock.instant())) {
                if(null == lastException) {
                    logger.error(
                            "Exception condition failed: Timeout (tried for {} seconds with 500ms interval",
                            timeoutInSeconds);
                    lastException = new TimeoutException();
                } else {
                    logger.error("Exception condition failed: {} (tried for {} seconds with 500ms interval",
                            lastException.getCause(), timeoutInSeconds);
                }
                throw lastException;
            }

            try {
                sleeper.sleep(intervalDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    /**
     *
     * @param webElement locator we will use to re-lookup the element on retry
     * @param attribute WebElement attribute
     * @param timeoutInSeconds time to continue trying for
     * @return attribute property value
     * @throws Throwable the last exception to be thrown
     */
    public String tolerantInteractionToGetAttribute(
            WebElement webElement, String attribute, int timeoutInSeconds)
            throws Throwable {
        end = clock.instant().plusSeconds(timeoutInSeconds);
        while (true) {
            try {
                if (Boolean.TRUE.equals(webElement.isEnabled())) {
                    return interactToGetAttribute(webElement, attribute);
                }
            } catch (Throwable e) {
                lastException = tolerantExceptionHandler.handle(e);
            }
            if (end.isBefore(clock.instant())) {
                if (null == lastException) {
                    logger.error(
                            "Exception condition failed: Timeout (tried for {} seconds with 500ms interval",
                            timeoutInSeconds);
                    lastException = new TimeoutException();
                } else {
                    logger.error("Exception condition failed: {} (tried for {} seconds with 500ms interval",
                            lastException.getCause(), timeoutInSeconds);
                }
                throw lastException;
            }

            try {
                sleeper.sleep(intervalDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    /**
     *
     * @param webElement locator we will use to re-lookup the element on retry
     * @param timeoutInSeconds time to continue trying for
     * @return text value of the WebElement
     * @throws Throwable the last exception to be thrown
     */
    public String tolerantInteractionToGetText(
            WebElement webElement, int timeoutInSeconds)
            throws Throwable {
        end = clock.instant().plusSeconds(timeoutInSeconds);
        while (true) {
            try {
                if (Boolean.TRUE.equals(webElement.isEnabled())) {
                    return interactToGetText(webElement);
                }
            } catch (Throwable e) {
                lastException = tolerantExceptionHandler.handle(e);
            }
            if (end.isBefore(clock.instant())) {
                if (null == lastException) {
                    logger.error(
                            "Exception condition failed: Timeout (tried for {} seconds with 500ms interval",
                            timeoutInSeconds);
                    lastException = new TimeoutException();
                } else {
                    logger.error("Exception condition failed: {} (tried for {} seconds with 500ms interval",
                            lastException.getCause(), timeoutInSeconds);
                }
                throw lastException;
            }

            try {
                sleeper.sleep(intervalDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    /**
     *
     * @param webElement locator we will use to re-lookup the element on retry
     * @param timeoutInSeconds time to continue trying for
     * @return WebElement to allow fluent method stringed calls
     * @throws Throwable the last exception to be thrown
     */
    public WebElement tolerantInteractionToClear(
            WebElement webElement, int timeoutInSeconds)
            throws Throwable {
        end = clock.instant().plusSeconds(timeoutInSeconds);
        while (true) {
            try {
                if (Boolean.TRUE.equals(webElement.isEnabled())) {
                    interactToClearField(webElement);
                    return webElement;
                }
            } catch (Throwable e) {
                lastException = tolerantExceptionHandler.handle(e);
            }
            if (end.isBefore(clock.instant())) {
                if (null == lastException) {
                    logger.error(
                            "Exception condition failed: Timeout (tried for {} seconds with 500ms interval",
                            timeoutInSeconds);
                    lastException = new TimeoutException();
                } else {
                    logger.error("Exception condition failed: {} (tried for {} seconds with 500ms interval",
                            lastException.getCause(), timeoutInSeconds);
                }
                throw lastException;
            }

            try {
                sleeper.sleep(intervalDuration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new WebDriverException(e);
            }
        }
    }

    private void interact(WebElement webElement, String textToType) {
        webElement.sendKeys(textToType);
    }

    private void interact(WebElement webElement) {
        webElement.click();
    }

    private String interactToGetText(WebElement webElement) {
        return webElement.getText();
    }

    private String interactToGetAttribute(WebElement webElement, String attribute) {
        return webElement.getAttribute(attribute);
    }

    private void interactToClearField(WebElement webElement) {
        webElement.clear();
    }

    private void interact(Select selectBox, String value, SelectBoxInteractionType selectBoxInteractionType) {
        switch(selectBoxInteractionType) {
            case BY_VALUE:
                selectBox.selectByValue(value);
                break;
            case BY_VISIBLE_TEXT:
                selectBox.selectByVisibleText(value);
                break;
            default:
                throw new WebDriverException("Must be one of BY_VALUE or BY_VISIBLE_TEXT");
        }
    }

    private void interact(Select selectBox, int index) {
        int normalisedIndex = index - 1;
        selectBox.selectByIndex(normalisedIndex);
    }
}
