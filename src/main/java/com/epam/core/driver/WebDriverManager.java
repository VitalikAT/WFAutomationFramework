package com.epam.core.driver;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class WebDriverManager {

    private static WebDriverFactory webDriverFactory = new WebDriverFactory();
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (webDriver.get() == null) {
            setDriver();
        }
        return webDriver.get();
    }

    public static void setWebDriver(WebDriver driver) {
        webDriver.set(driver);
    }

    public static void setDriver() {
        webDriver.set(webDriverFactory.getDriverInstance());
//        driver = ThreadLocal.withInitial(() -> webDriverFactory.getDriverInstance());
    }

    public static void closeDriver() {
        webDriver.get().close();
        webDriver.get().quit();
        webDriver.remove();
    }

    public static void getUrl(String url) {
        Reporter.log("Opening page with url - " + url + "</br>");
        getDriver().get(url);
    }

    public static Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }
}