package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.saucelabs.saucebindings.*;
import uk.co.evoco.exceptions.SauceLabsCredentialsException;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabsConfig {

    private String seleniumVersion;
    private URL sauceRemoteUrl;
    private boolean acceptInsecureCerts;
    private boolean strictFileInteractability;
    private boolean capturePerformance;
    private boolean recordLogs;
    private boolean recordScreenshots;
    private boolean recordVideo;
    private boolean extendedDebugging;
    private SaucePlatform saucePlatform;
    private Browser browserName;
    private String browserVersion;
    private PageLoadStrategy pageLoadStrategy;
    private UnhandledPromptBehavior unhandledPromptBehavior;
    private SauceLabsCredentials sauceLabsCredentials;

    public String getSeleniumVersion() {
        return seleniumVersion;
    }

    @JsonProperty("seleniumVersion")
    public void setSeleniumVersion(String seleniumVersion) {
        this.seleniumVersion = seleniumVersion;
    }

    public SauceLabsCredentials getCredentials() throws SauceLabsCredentialsException {
        this.sauceLabsCredentials = new SauceLabsCredentials(this);
        return this.sauceLabsCredentials;
    }

    public URL getSauceRemoteUrl() {
        return sauceRemoteUrl;
    }

    @JsonProperty("sauceLabsRemoteGridUrl")
    public void setSauceRemoteUrl(String sauceRemoteUrl) throws MalformedURLException, SauceLabsCredentialsException {
        this.sauceRemoteUrl = new URL("https://" + getCredentials().getUsername() + ":" + getCredentials().getAccessKey() + "@" + sauceRemoteUrl);
        System.out.println(this.sauceRemoteUrl);
    }

    public boolean isAcceptInsecureCerts() {
        return acceptInsecureCerts;
    }

    @JsonProperty("acceptInsecureCerts")
    public void setAcceptInsecureCerts(boolean acceptInsecureCerts) {
        this.acceptInsecureCerts = acceptInsecureCerts;
    }

    public boolean isStrictFileInteractability() {
        return strictFileInteractability;
    }

    @JsonProperty("strictFileInteractability")
    public void setStrictFileInteractability(boolean strictFileInteractability) {
        this.strictFileInteractability = strictFileInteractability;
    }

    public boolean isCapturePerformance() {
        return capturePerformance;
    }

    @JsonProperty("capturePerformance")
    public void setCapturePerformance(boolean capturePerformance) {
        this.capturePerformance = capturePerformance;
    }

    public boolean isRecordLogs() {
        return recordLogs;
    }

    @JsonProperty("recordLogs")
    public void setRecordLogs(boolean recordLogs) {
        this.recordLogs = recordLogs;
    }

    public boolean isRecordScreenshots() {
        return recordScreenshots;
    }

    @JsonProperty("recordScreenshots")
    public void setRecordScreenshots(boolean recordScreenshots) {
        this.recordScreenshots = recordScreenshots;
    }

    public boolean isRecordVideo() {
        return recordVideo;
    }

    @JsonProperty("recordVideo")
    public void setRecordVideo(boolean recordVideo) {
        this.recordVideo = recordVideo;
    }

    public SaucePlatform getSaucePlatform() {
        return saucePlatform;
    }

    @JsonProperty("saucePlatform")
    public void setSaucePlatform(String saucePlatform) {
        this.saucePlatform = SaucePlatform.valueOf(saucePlatform);
    }

    public Browser getBrowserName() {
        return browserName;
    }

    @JsonProperty("browserName")
    public void setBrowserName(String browserName) {
        this.browserName = Browser.valueOf(browserName);
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    @JsonProperty("browserVersion")
    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion.toLowerCase();
    }

    public PageLoadStrategy getPageLoadStrategy() {
        return pageLoadStrategy;
    }

    @JsonProperty("pageLoadStrategy")
    public void setPageLoadStrategy(String pageLoadStrategy) {
        this.pageLoadStrategy = PageLoadStrategy.valueOf(pageLoadStrategy);
    }

    public UnhandledPromptBehavior getUnhandledPromptBehavior() {
        return unhandledPromptBehavior;
    }

    @JsonProperty("unhandledPromptBehaviour")
    public void setUnhandledPromptBehavior(String unhandledPromptBehavior) {
        this.unhandledPromptBehavior = UnhandledPromptBehavior.valueOf(unhandledPromptBehavior);
    }

    public boolean isExtendedDebugging() {
        return extendedDebugging;
    }

    @JsonProperty("extendedDebugging")
    public void setExtendedDebugging(boolean extendedDebugging) {
        this.extendedDebugging = extendedDebugging;
    }
}
