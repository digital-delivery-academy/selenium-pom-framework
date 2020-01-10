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
     * @param selectBox
     * @param htmlValue
     */
    public static void itemByHtmlValueAttribute(WebElement selectBox, String htmlValue) {
        Select select = new Select(selectBox);
        select.selectByValue(htmlValue);
    }

    /**
     * Selects an option by the index of the option in the list
     * @param selectBox
     * @param index
     */
    public static void itemByIndex(WebElement selectBox, int index) {
        Select select = new Select(selectBox);
        select.selectByIndex(index);
    }

    /**
     * Selects an option by the text that is visible in the select box
     * @param selectBox
     * @param visibleText
     */
    public static void itemByVisibleText(WebElement selectBox, String visibleText) {
        Select select = new Select(selectBox);
        select.selectByVisibleText(visibleText);
    }
}
