package com.runner.core.driver;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class WebDriverManager {

    private static WebDriverManager instance;

    private WebDriverManager() {
    }

    public static WebDriverManager getInstance() {
        if (instance == null) {
            synchronized (WebDriverManager.class) {
                if (instance == null) {
                    instance = new WebDriverManager();
                }
            }
        }
        return instance;
    }

    private static WebDriverFactory webDriverFactory = new WebDriverFactory();
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (webDriver.get() == null) {
            setDriver();
        }
        return webDriver.get();
    }

    private static void setDriver() {
        webDriver = ThreadLocal.withInitial(() -> webDriverFactory.getDriverInstance());
    }

    public static void closeDriver() {
        webDriver.get().close();
        webDriver.get().quit();
    }

    public static void removeDriverFromDriverPool() {
        if (webDriver.get() != null) {
            webDriver.remove();
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