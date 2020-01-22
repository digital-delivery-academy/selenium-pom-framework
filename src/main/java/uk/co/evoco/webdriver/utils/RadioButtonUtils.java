package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Utility methods to select radio buttons
 */
public final class RadioButtonUtils {

    /**
     * Given a list of WebElements that locate the labels of the radio buttons,
     * finds the radio button with the given visible label text and selects it.
     * @param webElements active WebElement, already located
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
}
