package com.runner.core.driver;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class WebDriverListener implements IInvokedMethodListener {

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            TLDriverFactory.setDriver();
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        if (iInvokedMethod.isTestMethod()) {
            WebDriver driver = TLDriverFactory.getDriver();
            if (driver != null) {
                driver.close();
                driver.quit();
            }
        }
    }
}
