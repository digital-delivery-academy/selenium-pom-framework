package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebElement;
import uk.co.evoco.webdriver.configuration.TestConfigHelper;

import java.util.Optional;

/**
 * Only for us in the situations outlined for the provided methods.
 * There's nothing wrong with WebDrivers normal click method, if you don't need this, steer well clear.
 */
public final class ClickUtils extends TolerantInteraction {

    /**
     *
     * @param webElement active WebElements, already located
     * @param timeout time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantClick(WebElement webElement, int timeout) throws Throwable {
        new ClickUtils().tolerantInteraction(webElement, Optional.empty(), timeout);
    }

    public static void tolerantClick(WebElement webElement) throws Throwable {
        new ClickUtils().tolerantInteraction(webElement, Optional.empty(),
                TestConfigHelper.get().getTolerantActionWaitTimeoutInSeconds());
    }
}
