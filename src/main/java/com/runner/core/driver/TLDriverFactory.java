package com.runner.core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;


public class TLDriverFactory {
    private static WebDriverFactory webDriverFactory = new WebDriverFactory();
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static synchronized void setTLDriver() {
        tlDriver = ThreadLocal.withInitial(() -> webDriverFactory.getDriverInstance());
    }

    public static WebDriver getDriver() {
        return getTLDriver().get();
    }

    public static synchronized ThreadLocal<WebDriver> getTLDriver() {
        return tlDriver;
    }

    public static void closeDriver() {
        tlDriver.get().close();
        tlDriver.get().quit();
    }

    public static void removeDriverFromDriverPool() {
        if (getDriver() != null) {
            getTLDriver().remove();
        }
    }

    public static void getUrl(String url) {
        Reporter.log("Opening page with url - " + url + "</br>");
        getDriver().get(url);
    }

    public static Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }
}
