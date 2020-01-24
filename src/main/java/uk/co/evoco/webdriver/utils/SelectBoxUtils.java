package uk.co.evoco.webdriver.utils;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Utilities class providing support methods for Select Boxes
 * While this is straight forward, its less than obvious to newcomers.
 * Feel free not to use this at all and just do it with WebDriver!
 */
public final class SelectBoxUtils {

    /**
     * Selects an option that has a matching value attribute in the Options tag markup
     * @param selectBox active WebElement, already located
     * @param htmlValue HTML value attribute
     */
    public static void itemByHtmlValueAttribute(WebElement selectBox, String htmlValue) {
        Select select = new Select(selectBox);
        select.selectByValue(htmlValue);
    }

    /**
     * Selects an option by the index of the option in the list.
     * The input is NOT zero based, we're normalising the input internally.
     * @param selectBox active WebElement, already located
     * @param index index in order of display
     */
    public static void itemByIndex(WebElement selectBox, int index) {
        int normalisedIndex = index - 1;
        Select select = new Select(selectBox);
        select.selectByIndex(normalisedIndex);
    }

    /**
     * Selects an option by the text that is visible in the select box
     * @param selectBox active WebElement, already located
     * @param visibleText visible text in the select box (NOT the HTML value attribute)
     */
    public static void itemByVisibleText(WebElement selectBox, String visibleText) {
        Select select = new Select(selectBox);
        select.selectByVisibleText(visibleText);
    }
}
