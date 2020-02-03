package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebElement;

import java.util.Optional;

public final class SendKeysUtils extends TolerantInteraction {

    /**
     *
     * @param webElement active WebElement, already located
     * @param textToType text that should be typed
     * @param timeout time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantType(WebElement webElement, String textToType, int timeout) throws Throwable {
        new SendKeysUtils().tolerantInteraction(webElement, Optional.of(textToType), timeout);
    }
}
