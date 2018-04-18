package com.epam.utils;

import java.io.IOException;
import java.util.Properties;

import static com.epam.constants.CommonConsts.*;

public class PropertiesLoader {

    private Properties properties;

    public PropertiesLoader(String path) {
        properties = new Properties();
        try {
            properties.load(PropertiesLoader.class.getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBrowserName() {
        return properties.getProperty("browser");
    }

    public String getChromeDriverPath() {
        return properties.getProperty("chromeDriver");
    }

    public String getInternetExplorerDriver_32Path() {
        return properties.getProperty("internetExplorerDriver_32");
    }

    public String getLogType() {
        return properties.getProperty("logType");
    }

    public long getImplicitlyWaitTimeout() {
        return Long.valueOf(properties.getProperty("implicitlyWaitTimeout"));
    }

    public String getLogin() {
        return properties.getProperty("login");
    }

    public String getLogin(String role) {
        switch (role){
            case SUPER_USER:
                return properties.getProperty("login_superuser");
        }
        return getLogin();
    }

    public String getPassWord() {
        return properties.getProperty("password");

    }

    public String getPassWord(String role) {
        switch (role){
            case SUPER_USER:
                return properties.getProperty("password_superuser");
        }
        return getPassWord();

    }

    public String getLocalFilesDirectory() {
        return properties.getProperty("localDir");
    }
    public String getRemoteFilesDirectory() {
        return properties.getProperty("remoteDir");
    }
}
