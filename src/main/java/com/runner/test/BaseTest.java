package com.runner.test;


import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;


@Listeners(TestListener.class)
public class BaseTest {

    protected static final String searchMessage = "Your inquiry has been submitted! You will receive an email with the results within few minutes.";
    private Logger LOG;

    public void step(String message) {
        LOG.info(message);
    }

    @BeforeMethod
    public void setupTest (Method method) {
        LOG = MyLogHolder.getLoggerHolder(method.getName(), method.getDeclaringClass().getSimpleName());
//        setDriver();
    }

//    public WebDriver getDriver() {
//        return WebDriverManager.getDriver();
//    }

    @AfterMethod
    public void tearDown() {
//        WebDriverManager.getDriver().quit();
//        WebDriverManager.getTLDriver().remove();
    }

    @AfterTest
    public void checkInboxMessages() {
//        WebDriver webDriver = getDriver();
//        webDriver.manage().window().maximize();
//        webDriver.navigate().to(MAIL_URL);
//        MailHomePO mailHomePage = new MailHomePO(webDriver);
//        mailHomePage.login(EMAIL_LOGIN, PASSWORD);
//        MailInboxPO inbox = new MailInboxPO(webDriver);
//        List<String> inboxMessages = inbox.getInboxMessages(this.getClass().getSimpleName());
//        for(String inboxMessage: inboxMessages ){
//            LOG.info(inboxMessage);
//        }
//        getDriver().quit();
    }
}
