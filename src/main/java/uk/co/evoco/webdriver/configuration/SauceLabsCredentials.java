package uk.co.evoco.webdriver.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.evoco.exceptions.SauceLabsCredentialsException;

public class SauceLabsCredentials {

    private static final Logger logger = LoggerFactory.getLogger(SauceLabsCredentials.class);
    private String username, accessKey;

    public SauceLabsCredentials(SauceLabsConfig sauceLabsConfig) throws SauceLabsCredentialsException {
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
