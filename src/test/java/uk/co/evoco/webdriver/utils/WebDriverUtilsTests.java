package uk.co.evoco.webdriver.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import uk.co.evoco.tests.BaseAbstractTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebDriverUtilsTests extends BaseAbstractTest {

    private static String baseUrl;
    private static EmbeddedJetty embeddedJetty;

    @BeforeAll
    public static void webDriverSetup() throws Exception {
        embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start();
        baseUrl = "http://localhost:" + embeddedJetty.getPort();
    }

    @AfterAll
    public static void webDriverTearDown() throws Exception {
        embeddedJetty.stop();
    }

    @BeforeEach
    public void setUpWebApp() {
        webDriver.get(baseUrl + "/index.html");
    }

    @Test
    public void testServerIsAvailable() {
        assertThat(webDriver.getCurrentUrl(), is(baseUrl + "/index.html"));
    }

    @Test
    public void testFindByUtilsMultipleMatchesToLocatorClickOnDisplayed() {
        FindByUtils.multipleLocatorMatchGetDisplayed(webDriver, By.className("findbyutils-button")).click();
        assertThat(webDriver.getCurrentUrl(), is("http://localhost:4442/hello-passed.html"));
    }

    @Test
    public void testRadioButtonUtilsSelectByLabel() {
        RadioButtonUtils.selectByLabel(webDriver.findElements(
                By.xpath("//div[@data-test-id='radio-button-group']/label")), "Value 2");
        assertThat(webDriver.findElement(By.id("radio-button-value2")).isSelected(), is(true));
        assertThat(webDriver.findElement(By.id("radio-button-value1")).isSelected(), is(false));
        assertThat(webDriver.findElement(By.id("radio-button-value3")).isSelected(), is(false));
    }

    @Test
    public void testSelectBoxUtilsWithItemByHtmlValueAttribute() {
        SelectBoxUtils.itemByHtmlValueAttribute(webDriver.findElement(By.id("select-box")), "option2");
        assertThat(webDriver.findElement(By.id("select-box-option2")).isSelected(), is(true));
        assertThat(webDriver.findElement(By.id("select-box-option1")).isSelected(), is(false));
        assertThat(webDriver.findElement(By.id("select-box-option3")).isSelected(), is(false));
    }

    @Test
    public void testSelectBoxUtilsWithItemByIndex() {
        SelectBoxUtils.itemByIndex(webDriver.findElement(By.id("select-box")), 3);
        assertThat(webDriver.findElement(By.id("select-box-option3")).isSelected(), is(true));
        assertThat(webDriver.findElement(By.id("select-box-option2")).isSelected(), is(false));
        assertThat(webDriver.findElement(By.id("select-box-option1")).isSelected(), is(false));
    }

    @Test
    public void testWindowUtilsScrollIntoView() {
        WebElement elementOutOfView = webDriver.findElement(By.id("out-of-viewport-link"));
        WindowUtils.scrollIntoView(webDriver, elementOutOfView);
        elementOutOfView.click();
        assertThat(webDriver.getCurrentUrl(), is("http://localhost:4442/hello-passed.html"));
    }

    @Test
    public void testCanClickOnHrefWithTolerantPollingWaitBeforeClickingHelperWhenElementStartsDisabled() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clickutils-href-disabled"));
        ClickUtils.tolerantClick(element, 30);
        assertThat(webDriver.getCurrentUrl(), is("http://localhost:4442/hello-passed.html"));
    }

    @Test
    public void testCanClickOnHrefWithTolerantPollingWaitBeforeClickingHelperWhenElementStartsEnabled() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clickutils-href"));
        ClickUtils.tolerantClick(element, 30);
        assertThat(webDriver.getCurrentUrl(), is("http://localhost:4442/hello-passed.html"));
    }

    @Test
    public void testCanClickOnHrefWithTolerantPollingWaitBeforeClickingHelperWhenElementIsDisabledAllTheTime() {
        assertThrows(TimeoutException.class, () -> {
            WebElement element = webDriver.findElement(By.id("clickutils-href-always-disabled"));
            ClickUtils.tolerantClick(element, 5);
        });
    }

    @Test
    public void testCanClickOnHrefWithTolerantPollingWaitTimeGetFromConfigBeforeClickingHelperWhenElementIsDisabledAllTheTime() {
        assertThrows(TimeoutException.class, () -> {
            WebElement element = webDriver.findElement(By.id("clickutils-href-always-disabled"));
            ClickUtils.tolerantClick(element);
        });
    }

    @Test
    public void testCanSelectValueByVisibleTextUsingTolerantMethodsAfterStaleElementException() throws Throwable {
        webDriver.get(baseUrl + "/stale-element.html");
        Thread.sleep(7000);
        SelectBoxUtils.tolerantItemByVisibleText(webDriver, By.id("select-box"), "Option 2");
        Thread.sleep(5000);
    }

    @Test
    public void testCanClearField() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clear-utils-text-box"));
        element.sendKeys("Hello world");
        assertThat(element.getAttribute("value"), is("Hello world"));
        ClearUtils.tolerantClear(element);
        assertThat(element.getText(), is(""));
    }

    @Test
    public void testCanClearFieldWithTimeout() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clear-utils-text-box"));
        element.sendKeys("Hello world");
        assertThat(element.getAttribute("value"), is("Hello world"));
        ClearUtils.tolerantClear(element, 10);
        assertThat(element.getText(), is(""));
    }

    @Test
    public void testCanGetAttributes() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clear-utils-text-box"));
        element.sendKeys("Hello world");
        assertThat(GetAttributeUtils.tolerantGetAttribute(element, "value"), is("Hello world"));
    }

    @Test
    public void testCanGetAttributesWithTimeout() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clear-utils-text-box"));
        element.sendKeys("Hello world");
        assertThat(GetAttributeUtils.tolerantGetAttribute(element, "value", 10), is("Hello world"));
    }

    @Test
    public void testCanGetText() throws Throwable {
        WebElement element = webDriver.findElement(By.id("get-text-element"));
        assertThat(GetTextUtils.tolerantGetText(element), is("Hello there!"));
    }

    @Test
    public void testCanGetTextWithTimeout() throws Throwable {
        WebElement element = webDriver.findElement(By.id("get-text-element"));
        assertThat(GetTextUtils.tolerantGetText(element, 10), is("Hello there!"));
    }

    @Test
    public void testCanSendKeys() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clear-utils-text-box"));
        SendKeysUtils.tolerantType(element, "Hello world");
        assertThat(element.getAttribute("value"), is("Hello world"));
    }

    @Test
    public void testCanSendKeysWithTimeout() throws Throwable {
        WebElement element = webDriver.findElement(By.id("clear-utils-text-box"));
        SendKeysUtils.tolerantType(element, "Hello world", 10);
        assertThat(element.getAttribute("value"), is("Hello world"));
    }
}
