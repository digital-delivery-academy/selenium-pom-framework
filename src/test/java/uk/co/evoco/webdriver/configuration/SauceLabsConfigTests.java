package uk.co.evoco.webdriver.configuration;

import com.saucelabs.saucebindings.Browser;
import com.saucelabs.saucebindings.PageLoadStrategy;
import com.saucelabs.saucebindings.SaucePlatform;
import com.saucelabs.saucebindings.UnhandledPromptBehavior;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SauceLabsConfigTests {

    @Test
    public void testCanBindToConfigAndValuesCorrect() {
        SauceLabsConfig sauceLabsConfig = new SauceLabsConfigurationLoader().load("saucelabs.json");
        assertThat(sauceLabsConfig.isAcceptInsecureCerts(), is(true));
        assertThat(sauceLabsConfig.isStrictFileInteractability(), is(true));
        assertThat(sauceLabsConfig.isCapturePerformance(), is(true));
        assertThat(sauceLabsConfig.isExtendedDebugging(), is(false));
        assertThat(sauceLabsConfig.isRecordLogs(), is(true));
        assertThat(sauceLabsConfig.isRecordScreenshots(), is(true));
        assertThat(sauceLabsConfig.isRecordVideo(), is(true));
        assertThat(sauceLabsConfig.getSaucePlatform(), is(SaucePlatform.WINDOWS_10));
        assertThat(sauceLabsConfig.getBrowserName(), is(Browser.CHROME));
        assertThat(sauceLabsConfig.getBrowserVersion(), is("latest"));
        assertThat(sauceLabsConfig.getPageLoadStrategy(), is(PageLoadStrategy.NORMAL));
        assertThat(sauceLabsConfig.getSeleniumVersion(), is("3.7.1"));
        assertThat(sauceLabsConfig.getSauceRemoteUrl().toString(), containsString("ondemand.us-west-1.saucelabs.com/wd/hub"));
        assertThat(sauceLabsConfig.getUnhandledPromptBehavior(), is(UnhandledPromptBehavior.DISMISS));
    }
}
