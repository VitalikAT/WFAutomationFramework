package com.epam.core.driver;


import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class MyChromeDriver {

    private static MyChromeDriver instance = new MyChromeDriver();
    private static WebDriverFactory webDriverFactory = new WebDriverFactory();

    private MyChromeDriver() {
    }

    public static MyChromeDriver getInstance() {
        return instance;
    }

    public WebDriver getDriver() {
        driver.get().manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
        return driver.get();
    }

    ThreadLocal<WebDriver> driver = ThreadLocal.withInitial(() -> webDriverFactory.getDriverInstance());

    public void openUrlInBrowser(String url) {
        getDriver().get(url);
    }

    public void closeDriver() {
        driver.get().quit();
        driver.remove();
    }
}
