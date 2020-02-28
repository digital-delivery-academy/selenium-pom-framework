package uk.co.evoco.webdriver.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import uk.co.evoco.tests.BaseAbstractTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebDriverUtilsIT extends BaseAbstractTest {

    private static String baseUrl;
    private static EmbeddedJetty embeddedJetty;

    @BeforeAll
    public static void webDriverSetup() throws Exception {
        embeddedJetty = new EmbeddedJetty();
        embeddedJetty.start();
        baseUrl = "http://localhost:" + embeddedJetty.getPort() + "/index.html";
    }

    @AfterAll
    public static void webDriverTearDown() throws Exception {
        embeddedJetty.stop();
    }

    @BeforeEach
    public void setUpWebApp() {
        webDriver.get(baseUrl);
    }

    @Test
    public void testServerIsAvailable() {
        assertThat(webDriver.getCurrentUrl(), is(baseUrl));
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
}
