package uk.co.evoco.googlesearch;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Our test here inherits the @BeforeAll and @AfterAll methods as annotated in the AbstractTest class
 * This gives us a really clean an really lean testing class.  Each @Test method represents one
 * self contained test.
 *
 * Each @Test method will have its own browser instance that is created just for the test, and completed
 * destroyed at the end of the test.  All of the browser launching and initial navigation to the application
 * under test can be found in the AbstractTest class.
 *
 * Each @Test method has at least one assertion.  It's important that assertions are NOT abstracted away from
 * the @Test method as assertions are unlikely too (or shouldn't be) generic.
 */
public class SearchTests extends AbstractTest {

    @Test
    public void testEvocoIsTopOfSearchResultsOnGoogleWithSearchTermForDigitalServices() {
        GoogleLandingPage googleLandingPage = new GoogleLandingPage(this.webDriver);
        googleLandingPage.enterSearchTerm("evoco digital services");
        GoogleSearchResultsPage googleSearchResultsPage = googleLandingPage.clickGoogleSearchButton();
        googleSearchResultsPage.clickLinkBySearchResultIndex(1);
        assertThat(this.webDriver.getCurrentUrl(), is("https://www.evoco.co.uk/"));
    }

    @Test
    public void testEvocoIsTopOfSearchResultsOnGoogleWithSearchTermIncludingAcademies() {
        GoogleLandingPage googleLandingPage = new GoogleLandingPage(this.webDriver);
        googleLandingPage.enterSearchTerm("evoco academy");
        GoogleSearchResultsPage googleSearchResultsPage = googleLandingPage.clickGoogleSearchButton();
        googleSearchResultsPage.clickLinkBySearchResultIndex(1);
        assertThat(this.webDriver.getCurrentUrl(), is("https://www.evoco.co.uk/"));
    }
}
