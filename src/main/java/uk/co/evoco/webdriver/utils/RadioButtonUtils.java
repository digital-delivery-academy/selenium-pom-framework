package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebElement;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

import java.util.List;

/**
 * Utility methods to select radio buttons
 */
public final class RadioButtonUtils extends TolerantInteraction {

    /**
     * Given a list of WebElements that locate the labels of the radio buttons,
     * finds the radio button with the given visible label text and selects it.
     *
     * @param webElements      active WebElements, already located
     * @param visibleLabelText text that is visible on the page in the label tags
     */
    public static void selectByLabel(List<WebElement> webElements, String visibleLabelText) {
        for (WebElement webElement : webElements) {
            if (webElement.getText().equals(visibleLabelText)) {
                webElement.click();
                break;
            }
        }
    }

    /**
     * @param webElements      active WebElements, already located
     * @param visibleLabelText text that is visible on the page in the label tags
     * @param timeout          time in seconds to keep trying
     * @throws Throwable any unhandled or un-tolerated exception
     */
    public static void tolerantSelectByLabel(List<WebElement> webElements, String visibleLabelText, int timeout)
            throws Throwable {
        new RadioButtonUtils().tolerantInteraction(webElements, visibleLabelText, timeout);
    }

    public static void tolerantSelectByLabel(List<WebElement> webElements, String visibleLabelText)
            throws Throwable {
        new RadioButtonUtils().tolerantInteraction(webElements, visibleLabelText, TestConfigManager.get().getExceptionsWaitTimeOut());
    }
}
