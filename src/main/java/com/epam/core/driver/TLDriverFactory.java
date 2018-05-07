package com.epam.core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public class TLDriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private static WebDriverFactory webDriverFactory = new WebDriverFactory();

    public static synchronized void setDriver() {
        tlDriver = ThreadLocal.withInitial(() -> webDriverFactory.getDriverInstance());
    }

    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void getUrl(String url) {
        Reporter.log("Opening page with url - " + url + "</br>");
        getDriver().get(url);
    }

    public static Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }
}