package uk.co.evoco.googlesearch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * This class is responsible for modeling the Google.com search results page and exposing its functionality
 * to callers.  This class follows the Page Object Model design pattern.
 */
public class GoogleSearchResultsPage extends BasePageObject {

    /**
     * The constructor takes an argument that represents the instance of WebDriver that will be used
     * for the test and passes it out to the "super" class (which is the abstract BasePageObject class that
     * we are inheriting from).
     * @param webDriver
     */
    public GoogleSearchResultsPage(WebDriver webDriver) {
        super(webDriver);
    }

    /**
     * This method will click on the search result that is in the position of the given index on the
     * Google search results page.  The result is "normalised" as the List Collections index is zero based (so we
     * add one to the index argument so that the caller can use a naturalised number)
     * @param index
     */
    public void clickLinkBySearchResultIndex(int index) {
        List<WebElement> searchResults = this.webDriver.findElements(By.tagName("h3"));
        searchResults.get(index - 1).click();
    }
}
