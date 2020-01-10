package uk.co.evoco.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import uk.co.evoco.webdriver.utils.FindByUtils;

/**
 * This class is responsible for modeling the Google.com landing page and exposing its functionality
 * to callers.  This class follows the Page Object Model design pattern.
 */
public class GoogleLandingPage extends BasePageObject {
    @FindBy(name = "q")
    private WebElement searchField;

    /**
     * The constructor takes an argument that represents the instance of WebDriver that will be used
     * for the test and passes it out to the "super" class (which is the abstract BasePageObject class that
     * we are inheriting from).
     * @param webDriver
     */
    public GoogleLandingPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * Type the given generic search term into the Google.com landing page search field
     * Returns itself as we aren't moving anywhere
     * @param searchTerm
     * @return GoogleLandingPage
     */
    public GoogleLandingPage enterSearchTerm(String searchTerm) {
        searchField.sendKeys(searchTerm);
        return this;
    }

    /**
     * Click the 'Google Search' field
     *
     * This method handles the fact that there are two 'Google Search' buttons in the DOM.
     * One button is used when search suggestions are displayed in a drop down, and one button is
     * used when there are no search suggestions provided.  We use a little (static) utility method to
     * work out which button we should use.  This utility is generic and could be used in any situation where there
     * are multiple elements with the same locator and one is hidden and one is displayed.
     *
     * Note that this method changes the page that we are on, and therefore returns, under the Page Object Model
     * pattern the page object class that represents the page that we are going too, which is the Search Results Page.
     * @return GoogleSearchResultsPage
     */
    public GoogleSearchResultsPage clickGoogleSearchButton() {
        FindByUtils.multipleLocatorMatchGetDisplayed(this.webDriver, By.name("btnK")).click();
        return new GoogleSearchResultsPage(this.webDriver);
    }
}
