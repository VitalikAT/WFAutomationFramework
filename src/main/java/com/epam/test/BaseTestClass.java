package com.epam.test;

import com.epam.core.driver.WebDriverManager;
import com.epam.core.driver.WebDriverThreadLocal;
import com.epam.core.injector.Injector;
import com.epam.utils.PropertiesLoader;
import com.epam.utils.WaitManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.epam.constants.CommonConsts.ESCAPE_PROPERTY;
import static com.epam.constants.CommonConsts.PATH_TO_CONFIGURATION_PROPERTIES;
import static com.epam.test.MyLogHolder.removeLogger;

@Listeners({TestListener.class})
public abstract class BaseTestClass {

    protected WaitManager waitManager = new WaitManager();
//    private static WebDriverThreadLocal webDriverThreadLocal = new WebDriverThreadLocal();
    protected PropertiesLoader propertiesLoader = new PropertiesLoader(PATH_TO_CONFIGURATION_PROPERTIES);

    protected Logger LOG;

    @BeforeClass
    public void browserInstantiate() {
        System.setProperty(ESCAPE_PROPERTY, "false");
    }

    public void step(String message) {
        LOG.info(message);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        LOG = MyLogHolder.getLoggerHolder(method.getName(), method.getDeclaringClass().getSimpleName());
        WebDriverManager.setDriver();
        createInstance();
    }

    @AfterMethod
    public void dropDriver() {
        WebDriverManager.closeDriver();
        removeLogger();
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
