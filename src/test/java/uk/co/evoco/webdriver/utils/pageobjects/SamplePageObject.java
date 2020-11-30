package uk.co.evoco.webdriver.utils.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.co.evoco.pageobjects.BasePageObject;

public class SamplePageObject extends BasePageObject {

    @FindBy(id = "clear-utils-text-box")
    private WebElement element;

    /**
     * Initiates page elements that are declared as fields annotated @FindBy and makes the WebDriver
     * instance available to child classes.
     *
     * @param webDriver active WebDriver instance
     */
    protected SamplePageObject(WebDriver webDriver) {
        super(webDriver);
    }


    public void fillTextBox() {
        this.element.sendKeys("Hello from sample page object");
    }

    public String getTextBoxContents() {
        return this.element.getAttribute("value");
    }
}
