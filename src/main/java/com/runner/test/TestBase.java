package com.runner.test;

import com.runner.core.injector.Injector;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.runner.core.driver.TLDriverFactory.closeDriver;
import static com.runner.core.driver.TLDriverFactory.setTLDriver;

public class TestBase {

//    protected WebDriver driver;
//    protected WebDriverWait wait;
//    private TLDriverFactory tLDriverFactory = new TLDriverFactory();
    private Logger LOG;


    @BeforeMethod
    public void setupTest(Method method) {
        LOG = MyLogHolder.getLoggerHolder(method.getName(), method.getDeclaringClass().getSimpleName());
        setTLDriver();
        createInstance();
    }

    @AfterMethod
    public synchronized void tearDown() throws Exception {
        closeDriver();
    }

    public void step(String message) {
        LOG.info(message);
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
