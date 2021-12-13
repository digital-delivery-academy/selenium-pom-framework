package uk.co.evoco.webdriver.configuration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import uk.co.evoco.exceptions.SauceLabsCredentialsException;

public class SauceLabsCredentials {

    private static final Logger logger = LogManager.getLogger(SauceLabsCredentials.class);
    private String username;
    private String accessKey;

    public SauceLabsCredentials() throws SauceLabsCredentialsException {
        this.username = System.getenv("SAUCE_USERNAME");
        this.accessKey = System.getenv("SAUCE_ACCESS_KEY");

        if(!areCredentialsValid()) {
            logger.error("One or both of SauceLabs credentials are null - ensure environment variables for SAUCE_USERNAME and SAUCE_ACCESS_KEY are set");
            throw new SauceLabsCredentialsException();
        }
    }

    private boolean areCredentialsValid() {
        return username != null || accessKey != null;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAccessKey() {
        return this.accessKey;
    }
}
