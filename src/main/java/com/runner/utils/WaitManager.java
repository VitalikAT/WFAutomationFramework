package com.runner.utils;

import com.runner.core.driver.WebDriverManager;
import com.runner.core.elements.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


public class WaitManager {
    public static final int DEFAULT_TIME_OUT = 60;// Seconds
    private static final long DEFAULT_POLLING = 1L;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.SECONDS;
    private static final String ELEMENT_NOT_CLICKABLE = "Element wasn't clickable in %s sec";
    private static final String ERROR_WAITING = "Some problem occurs during waiting";
    private static final int COUNT_RETRY = 5;
    private static final int DURATION = 3;
    private static final Logger LOG = LogManager.getLogger(WaitManager.class);
    private long timeOut;
    private TimeUnit timeUnitForTimeOut;
    private long polling;
    private TimeUnit timeUnitForPolling;

    public WaitManager(int timeOut, TimeUnit unit) {
        this.timeOut = timeOut;
        this.timeUnitForTimeOut = unit;
        this.polling = DEFAULT_POLLING;
        this.timeUnitForPolling = DEFAULT_TIME_UNIT;
    }

    public WaitManager() {
        this.timeOut = DEFAULT_TIME_OUT;
        this.timeUnitForTimeOut = DEFAULT_TIME_UNIT;
        this.polling = DEFAULT_POLLING;
        this.timeUnitForPolling = DEFAULT_TIME_UNIT;
    }

    //    WaitManager.waitForCondition(() -> isContainerVisible, false);
    public static void waitForCondition(Callable<Boolean> callable, boolean stopWaitIfExceptionOccurred) {
        long beginTime = System.currentTimeMillis();
        boolean continueWaiting = true;
        while (continueWaiting && ((System.currentTimeMillis() - beginTime) < DEFAULT_TIME_OUT * 1000)) {
            try {
                if (callable.call()) {
                    continueWaiting = false;
                }
            } catch (Exception e) {
                if (stopWaitIfExceptionOccurred) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = driver -> {
            if (driver != null) {
                return WebDriverManager.getInstance().executeScript("return document.readyState").toString().equals("complete");
            }
            throw new NullPointerException("Exception occurred while 'waitForPageLoaded', driver=null");
        };

        WebDriverWait wait = new WebDriverWait(WebDriverManager.getInstance().getDriver(), 60);
        wait.until(expectation);
    }

    public static void waitForCondition(ExpectedCondition expectedCondition) {
        WebDriverWait webDriverWait = new WebDriverWait(WebDriverManager.getInstance().getDriver(), 60);
        webDriverWait.until(expectedCondition);
    }

    public static void waitTimeOut(int time) {
        WebDriverManager.getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public <T extends WebElement> void untilClickable(T element) {
        untilClickable(element, COUNT_RETRY);
    }

    public <T extends WebElement> void untilClickable(T element, final int retryCount) {
        int retry = 0;
        while (retry < retryCount) {
            try {
                if (isClickable(element)) {
                    return;
                } else {
                    Sleeper.SYSTEM_SLEEPER.sleep(new Duration(DURATION, DEFAULT_TIME_UNIT));
                }
            } catch (InterruptedException e) {
                error(ERROR_WAITING);
            }
            retry++;
        }
        error(String.format(ELEMENT_NOT_CLICKABLE, retryCount * DURATION));
    }

    //    new WaitManager(5, TimeUnit.SECONDS).waitConstTime();
    public void waitConstTime() {
        try {
            Sleeper.SYSTEM_SLEEPER.sleep(new Duration(timeOut, timeUnitForTimeOut));
        } catch (InterruptedException e) {
            error(ERROR_WAITING);
        }
    }

    public <T extends WebElement> boolean isClickable(T webElement) {
        return webElement.isDisplayed() && webElement.isEnabled();
    }

    public void waitElement(Element element) {
        WebDriverWait waitDriver = new WebDriverWait(WebDriverManager.getInstance().getDriver(), 30);
        waitDriver.until(
                ExpectedConditions.elementToBeClickable(element.getWebElement()));
    }

    private void error(String message) {
        LOG.error(message);
    }

    private void debug(String message) {
        LOG.debug(message);
    }

    private void waitForPageToBeReady() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();

        //This loop will rotate for 100 times to check If page Is ready after every 1 second.
        //You can replace your if you wants to Increase or decrease wait time.
        for (int i = 0; i < 400; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error(e.getMessage());
            }
            //To check page ready state.

            if (js.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
        }
    }

    public void waitTillPageLoaded() {
        waitForPageToBeReady();
        for (int i = 0; i < 10; i++) {
            debug("Try number [" + i + "] to detect if page is loading.");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error(e.getMessage());
            }
            String status = "style";
            if (status.contains("display: none")) {
                debug("Waiter element not shown. Page looks as loaded.");
                //Element needed to garantthat page loaded
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    error(e.getMessage());
                }
                return;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error(e.getMessage());
            }
            break;
        }
        debug("End of page waiter");
    }
}