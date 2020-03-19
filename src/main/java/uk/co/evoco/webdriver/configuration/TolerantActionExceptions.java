package uk.co.evoco.webdriver.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TolerantActionExceptions {

    private String waitTimeoutInSeconds;
    private List<String> exceptionsToHandle;

    /**
     *
     * @return the tolerant action wait time in seconds
     */
    public String getWaitTimeoutInSeconds() {
        return waitTimeoutInSeconds;
    }

    /**
     *
     * @param waitTimeoutInSeconds set the tolerant action wait time in seconds
     */
    @JsonProperty("waitTimeoutInSeconds")
    public void setWaitTimeoutInSeconds(String waitTimeoutInSeconds) {
        this.waitTimeoutInSeconds = waitTimeoutInSeconds;
    }

    /**
     *
     * @return Exceptions list that we will use to tolerate in tolerable action wrappers
     */
    public List<String> getExceptionsToHandle() {
        return exceptionsToHandle;
    }

    /**
     *
     * @param exceptionsToHandle sets the list of exceptions for WebDriver that we will retry
     *                                            on when using our tolerant wrapper
     */
    @JsonProperty("exceptionsToHandle")
    public void setExceptionsToHandle(List<String> exceptionsToHandle) {
        this.exceptionsToHandle = exceptionsToHandle;
    }
}
