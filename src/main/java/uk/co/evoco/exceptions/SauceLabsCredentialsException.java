package uk.co.evoco.exceptions;

public class SauceLabsCredentialsException extends Exception {

    public SauceLabsCredentialsException() {
        super("Tried to get credentials from system variable SAUCE_USERNAME and SAUCE_ACCESS_KEY but could not find them");
        throw new RuntimeException("Can't execute against SauceLabs without valid credentials being provided, see stack trace from SauceLabsCredentialsException");
    }
}
