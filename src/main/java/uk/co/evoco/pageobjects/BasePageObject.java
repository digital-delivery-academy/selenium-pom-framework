package uk.co.evoco.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import uk.co.evoco.webdriver.configuration.TestConfigManager;

/**
 * This class is a base that all page objects can inherit from.  It ensures that we have
 * a WebDriver object at the very least, and also takes care of initiating all of the annotated (@FindBy)
 * WebElements that are declared in page objects as fields.
 *
 * The constructor of this class is called in every page objects constructor that inherits from this class.
 */
public abstract class BasePageObject {

    protected WebDriver webDriver;
    protected WebDriverWait wait;

    /**
     * Initiates page elements that are declared as fields annotated @FindBy and makes the WebDriver
     * instance available to child classes.
     * @param webDriver active WebDriver instance
     */
    public BasePageObject(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(this.webDriver, TestConfigManager.get().getWebDriverWaitTimeout());
     }
}
