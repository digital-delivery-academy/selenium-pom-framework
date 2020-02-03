package uk.co.evoco.webdriver.utils;

import org.eclipse.jetty.util.log.Logger;

public class NoRequestLogger implements Logger {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void warn(String s, Object... objects) {
        // do nothing
    }

    @Override
    public void warn(Throwable throwable) {
        // do nothing
    }

    @Override
    public void warn(String s, Throwable throwable) {
        // do nothing
    }

    @Override
    public void info(String s, Object... objects) {
        // do nothing
    }

    @Override
    public void info(Throwable throwable) {
        // do nothing
    }

    @Override
    public void info(String s, Throwable throwable) {
        // do nothing
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public void setDebugEnabled(boolean b) {
        // do nothing
    }

    @Override
    public void debug(String s, Object... objects) {
        // do nothing
    }

    @Override
    public void debug(String s, long l) {
        // do nothing
    }

    @Override
    public void debug(Throwable throwable) {
        // do nothing
    }

    @Override
    public void debug(String s, Throwable throwable) {
        // do nothing
    }

    @Override
    public Logger getLogger(String s) {
        return null;
    }

    @Override
    public void ignore(Throwable throwable) {
        // do nothing
    }
}
