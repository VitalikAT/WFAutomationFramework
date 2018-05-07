package com.epam.test;

import com.epam.core.driver.WebDriverManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.reflect.Method;

import static com.epam.test.MyLogHolder.removeLogger;

@Listeners({TestListener.class})
public abstract class BaseTestClass {

    private Logger LOG;

    public void step(String message) {
        LOG.info(message);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        LOG = MyLogHolder.getLoggerHolder(method.getName(), method.getDeclaringClass().getSimpleName());
    }

    @AfterMethod
    public void dropDriver() {
        WebDriverManager.getInstance().closeDriver();
        removeLogger();
    }

    @AfterClass
    void terminate () {
        WebDriverManager.getInstance().removeDriverFromDriverPool();
    }

}
