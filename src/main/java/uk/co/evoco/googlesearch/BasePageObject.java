package uk.co.evoco.googlesearch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * This class is a base that all page objects can inherit from.  It ensures that we have
 * a WebDriver object at the very least, and also takes care of initiating all of the annotated (@FindBy)
 * WebElements that are declared in page objects as fields.
 *
 * The constructor of this class is called in every page objects constructor that inherits from this class.
 */
public abstract class BasePageObject {

    protected WebDriver webDriver;

    public BasePageObject(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.webDriver = webDriver;
     }
}
