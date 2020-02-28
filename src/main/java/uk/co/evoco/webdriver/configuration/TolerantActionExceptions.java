package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TolerantActionExceptions {

    private String tolerantActionWaitTimeoutInSeconds;
    private List<String> exceptionsToHandleOnTolerantActions;

    /**
     *
     * @return the tolerant action wait time in seconds
     */
    String getTolerantActionWaitTimeoutInSeconds() {
        return tolerantActionWaitTimeoutInSeconds;
    }

    /**
     *
     * @param tolerantActionWaitTimeoutInSeconds set the tolerant action wait time in seconds
     */
    @JsonProperty("tolerantActionWaitTimeoutInSeconds")
    public void setTolerantActionWaitTimeoutInSeconds(String tolerantActionWaitTimeoutInSeconds) {
        this.tolerantActionWaitTimeoutInSeconds = tolerantActionWaitTimeoutInSeconds;
    }

    /**
     *
     * @return Exceptions list that we will use to tolerate in tolerable action wrappers
     */
    public List<String> getExceptionsToHandleOnTolerantActions() {
        return exceptionsToHandleOnTolerantActions;
    }

    /**
     *
     * @param exceptionsToHandleOnTolerantActions sets the list of exceptions for WebDriver that we will retry
     *                                            on when using our tolerant wrapper
     */
    @JsonProperty("exceptionsToHandleOnTolerantActions")
    public void setExceptionsToHandleOnTolerantActions(List<String> exceptionsToHandleOnTolerantActions) {
        this.exceptionsToHandleOnTolerantActions = exceptionsToHandleOnTolerantActions;
    }
}
