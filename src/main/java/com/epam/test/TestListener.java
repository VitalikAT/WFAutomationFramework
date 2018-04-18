package com.epam.test;

import com.epam.core.driver.WebDriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestListener implements ITestListener {
    private TestLogger LOG;
    private String testName;
    private String className;

    @Override
    public void onTestStart(ITestResult iTestResult) {
        testName = iTestResult.getName().split(" ")[0];
        className = iTestResult.getTestClass().getRealClass().getSimpleName();
        LOG = TestLogger.getLogger(testName, className);
        LOG.info("Start...");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        LOG.info(" [PASSED]");
        File file = new File(String.format("%s/test-output/logs/%s/%s.log", System.getProperty("user.dir"), className, testName));
        attachLog(file);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        LOG.info("[FAILED]");
        captureScreenshot();
        File file = new File(String.format("%s/test-output/logs/%s/%s.log", System.getProperty("user.dir"), className, testName));
        attachLog(file);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        testName = iTestResult.getName().split(" ")[0];
        className = iTestResult.getTestClass().getRealClass().getSimpleName();
        LOG = TestLogger.getLogger(testName, className);
        LOG.info("[SKIPPED]");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        captureScreenshot();
        File file = new File(String.format("%s/test-output/logs/%s/%s.log", System.getProperty("user.dir"), className, testName));
        attachLog(file);
    }

    @Override
    public void onStart(ITestContext iTestContext) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        if (LOG != null) {
            LOG.drop();
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    private byte[] captureScreenshot() {
        return ((TakesScreenshot) WebDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Log", type = "text/plain")
    private byte[] attachLog(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
