package com.epam.test;

import com.epam.bo.LoginBO;
import com.epam.core.driver.WebDriverFactory;
import com.epam.core.driver.WebDriverManager;
import com.epam.core.injector.AccessPoint;
import com.epam.core.injector.Injector;
import com.epam.model.User;
import com.epam.utils.PropertiesLoader;
import com.epam.utils.WaitManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.constants.CommonConsts.ESCAPE_PROPERTY;
import static com.epam.constants.CommonConsts.PATH_TO_CONFIGURATION_PROPERTIES;

@Listeners(TestListener.class)
public abstract class BaseTestClass {

    protected WaitManager waitManager = new WaitManager();

    protected PropertiesLoader propertiesLoader = new PropertiesLoader(PATH_TO_CONFIGURATION_PROPERTIES);

    protected TestLogger LOG;

    @BeforeClass
    public void browserInstantiate() {
        System.setProperty(ESCAPE_PROPERTY, "false");
    }

    public void step(String message) {
        LOG.info(message);
    }


    @BeforeMethod
    public void beforeMethod(Method method) {
        LOG = TestLogger.getLogger(method.getName(), method.getDeclaringClass().getSimpleName());
        createInstance();
        if (method.isAnnotationPresent(AccessPoint.class)) {
            String portal = method.getDeclaredAnnotation(AccessPoint.class).portal();
            String user_role = method.getDeclaredAnnotation(AccessPoint.class).credentials();
            if (!portal.isEmpty() && !user_role.isEmpty()) {
                LoginBO login = new LoginBO();
                login
                        .loginWithValidCredentials(portal, new User(propertiesLoader.getLogin(user_role), propertiesLoader.getPassWord(user_role)))
                        .isLoginSuccessful();
            }
            LOG.error("Missed parameters in annotation " + method.getDeclaredAnnotation(AccessPoint.class).toString());
            //TODO find a way how to skip test without failure. Using SkipException - will skipp all other tests in suite
        }
    }

    @AfterMethod
    public void dropDriver() {
        LOG.drop();
        WebDriverManager.stop();
    }


    public String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    private String getDirectory(String fileNameCommonPart) {
        String path;
        File localPath = new File(propertiesLoader.getLocalFilesDirectory());
        File remotePath = new File(propertiesLoader.getRemoteFilesDirectory());
        if (!(new WebDriverFactory().getHubURL() == null)) {
            path = remotePath.getPath();
        } else {
            path = localPath.getAbsolutePath();
        }
        return path + "\\" + fileNameCommonPart;
    }

    public String getFilePath(String fileNameCommonPart) {
        Pattern pattern = Pattern.compile("MD_([^[0-9]]*)");
        Matcher matcher = pattern.matcher(getMethodName());
        String ticketNumber = null;
        if (matcher.find()) {
            ticketNumber = matcher.group(1);
        }
        return getDirectory(fileNameCommonPart) + ticketNumber + ".xlsm";

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
