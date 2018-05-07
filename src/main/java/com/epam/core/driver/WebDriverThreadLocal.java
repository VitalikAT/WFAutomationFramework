package com.epam.core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class WebDriverThreadLocal {

    private static WebDriverFactory webDriverFactory = new WebDriverFactory();
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public void setTLDriver() {
        driver = ThreadLocal.withInitial(() -> webDriverFactory.getDriverInstance());
    }

    public ThreadLocal<WebDriver> getTLDriver() {
        return driver;
    }

    public WebDriver getDriver() {
        return getTLDriver().get();
    }

    public void openUrlInBrowser(String url) {
        getDriver().get(url);
    }

    public void closeDriver() {
        driver.get().quit();
        driver.remove();
    }

    public void getUrl(String url) {
        Reporter.log("Opening page with url - " + url + "</br>");
        getDriver().get(url);
    }

    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }
}
