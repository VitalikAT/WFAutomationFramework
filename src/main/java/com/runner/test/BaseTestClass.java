package com.runner.test;

import com.runner.core.driver.WebDriverManager;
import com.runner.core.injector.Injector;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.runner.test.MyLogHolder.removeLogger;

@Listeners({TestListener.class})
public abstract class BaseTestClass {

    private Logger LOG;

//    @BeforeClass
//    public void browserInstantiate() {
//        System.setProperty(ESCAPE_PROPERTY, "false");
//    }

    public void step(String message) {
        LOG.info(message);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        LOG = MyLogHolder.getLoggerHolder(method.getName(), method.getDeclaringClass().getSimpleName());
        createInstance();
//        getDriver();
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

    private void createInstance() {
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Injector) {
                    try {
                        LOG.debug("Trying to create instance " + field.getType());
                        Object object = field.getType().newInstance();
                        field.setAccessible(true);
                        field.set(this, object);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
