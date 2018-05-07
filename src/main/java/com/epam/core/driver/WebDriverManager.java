package com.epam.core.driver;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;

public class WebDriverManager {
    private static WebDriverFactory webDriverFactory = new WebDriverFactory();
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
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


    public WebDriver getDriver() {
        if (webDriver.get() == null) {
            setDriver();
        }
        return webDriver.get();
    }

    private void setDriver() {
        webDriver = ThreadLocal.withInitial(() -> webDriverFactory.getDriverInstance());
    }

    public void closeDriver() {
        webDriver.get().close();
        webDriver.get().quit();
    }

    public void removeDriverFromDriverPool() {
        if (webDriver.get() != null) {
            webDriver.remove();
        }
    }

    public void getUrl(String url) {
        Reporter.log("Opening page with url - " + url + "</br>");
        getDriver().get(url);
    }

    public Object executeScript(String script, Object... args) {
        return ((JavascriptExecutor) getDriver()).executeScript(script, args);
    }
}