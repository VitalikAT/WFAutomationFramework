//package com.epam.test;
//
//import com.epam.core.driver.WebDriverListener;
//import com.epam.core.injector.Injector;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.Listeners;
//
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.net.MalformedURLException;
//
//@Listeners({TestListener.class, WebDriverListener.class})
//public class TestBase {
//
//    //Declare ThreadLocal Driver (ThreadLocalMap) for ThreadSafe Tests
//    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
//    private Logger LOG;
//
//    public void step(String message) {
//        LOG.info(message);
//    }
//
//    @BeforeMethod
//    public void setupTest(Method method) throws MalformedURLException {
//        LOG = MyLogHolder.getLoggerHolder(method.getName(), method.getDeclaringClass().getSimpleName());
//        createInstance();
//        //Set Browser to ThreadLocalMap
//        driver.set(new ChromeDriver());
//    }
//
//    public WebDriver getDriver() {
//        //Get driver from ThreadLocalMap
//        return driver.get();
//    }
//
//    @AfterMethod
//    public void tearDown() throws Exception {
//        getDriver().quit();
//    }
//
//    @AfterClass
//    void terminate() {
//        //Remove the ThreadLocalMap element
//        driver.remove();
//    }
//
//    private void createInstance() {
//        Class<?> clazz = this.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//        for (Field field : fields) {
//            Annotation[] annotations = field.getAnnotations();
//            for (Annotation annotation : annotations) {
//                if (annotation instanceof Injector) {
//                    try {
//                        LOG.debug("Trying to create instance " + field.getType());
//                        Object object = field.getType().newInstance();
//                        field.setAccessible(true);
//                        field.set(this, object);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
//}