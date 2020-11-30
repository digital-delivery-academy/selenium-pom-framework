package uk.co.evoco.webdriver.configuration.driver;

import com.saucelabs.saucebindings.SauceOptions;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import uk.co.evoco.exceptions.SauceLabsCredentialsException;
import uk.co.evoco.webdriver.configuration.SauceLabsConfig;
import uk.co.evoco.webdriver.configuration.SauceLabsConfigurationLoader;

import java.io.IOException;

public class ConfiguredSauceLabsGridDriver implements ConfiguredDriver {

    private SauceLabsConfig sauceLabsConfig;

    public ConfiguredSauceLabsGridDriver() throws SauceLabsCredentialsException {
        super();
        this.sauceLabsConfig = new SauceLabsConfigurationLoader().load("saucelabs.json");
    }

    @Override
    public WebDriver getLocalDriver() throws IOException {
        throw new RuntimeException("Local running with SauceLabs is not available");
    }

    @Override
    public WebDriver getRemoteDriver() {
        SauceOptions sauceOptions = new SauceOptions();
        sauceOptions.setAcceptInsecureCerts(this.sauceLabsConfig.isAcceptInsecureCerts());
        sauceOptions.setStrictFileInteractability(this.sauceLabsConfig.isStrictFileInteractability());
        sauceOptions.setCapturePerformance(this.sauceLabsConfig.isCapturePerformance());
        sauceOptions.setExtendedDebugging(this.sauceLabsConfig.isExtendedDebugging());
        sauceOptions.setPageLoadStrategy(this.sauceLabsConfig.getPageLoadStrategy());
        sauceOptions.setRecordLogs(this.sauceLabsConfig.isRecordLogs());
        sauceOptions.setRecordScreenshots(this.sauceLabsConfig.isRecordScreenshots());
        sauceOptions.setRecordVideo(this.sauceLabsConfig.isRecordVideo());
        sauceOptions.setUnhandledPromptBehavior(this.sauceLabsConfig.getUnhandledPromptBehavior());

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", this.sauceLabsConfig.getBrowserName());
        capabilities.setCapability("browserVersion", this.sauceLabsConfig.getBrowserVersion());
        capabilities.setCapability("platformName", this.sauceLabsConfig.getSaucePlatform());

        return new RemoteWebDriver(this.sauceLabsConfig.getSauceRemoteUrl(), capabilities);
    }

    @Override
    public <T> T getOptions() {
        return null;
    }
}
