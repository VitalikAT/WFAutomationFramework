package com.epam.core.driver;

import com.epam.enums.Drivers;
import com.epam.utils.PropertiesLoader;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.epam.constants.CommonConsts.PATH_TO_CONFIGURATION_PROPERTIES;

public class WebDriverFactory {

    protected PropertiesLoader propertiesLoader = new PropertiesLoader(PATH_TO_CONFIGURATION_PROPERTIES);
    private String hubURL = getHubURLSystemProperty();

    private static String getHubURLSystemProperty() {
        return System.getProperty("hubURL");
    }

    private static String getRemoteDriverNameSystemProperty() {
        return System.getProperty("remoteDriverName");
    }

    public WebDriver getDriverInstance() {
        Drivers driverType = Drivers.getDriverType(propertiesLoader.getBrowserName());
        String hubURLSystemProperty = getHubURLSystemProperty();

        if (hubURLSystemProperty != null && !hubURLSystemProperty.isEmpty()) {
            driverType = Drivers.REMOTE_WEB_DRIVER;
        }

        return getDriverInstance(driverType);
    }

    public WebDriver getDriverInstance(Drivers driverType) {

        switch (driverType) {
            case CHROME:
                System.setProperty("webdriver.chrome.driver",
                        propertiesLoader.getChromeDriverPath());
                ChromeDriverService service = new ChromeDriverService.Builder()
                        .usingDriverExecutable(new File("/usr/local/chromedriver"))
                        .usingAnyFreePort()
                        .build();
                WebDriver chrome = new ChromeDriver(service,getChromeCapabilities());
                chrome.manage().timeouts().implicitlyWait(propertiesLoader.getImplicitlyWaitTimeout(), TimeUnit.SECONDS);
                chrome.manage().window().maximize();
                return chrome;

            case IE:
                System.setProperty("webdriver.ie.driver",
                        propertiesLoader.getInternetExplorerDriver_32Path());
                WebDriver ieDriver = new InternetExplorerDriver(getInternetExplorerCapabilities());
                ieDriver.manage().timeouts().implicitlyWait(propertiesLoader.getImplicitlyWaitTimeout(), TimeUnit.SECONDS);
                ieDriver.manage().window().maximize();
                return ieDriver;

            case REMOTE_WEB_DRIVER:
                WebDriver driver = null;
                String setBrowserName = getRemoteDriverNameSystemProperty();
                URL url = null;
                try {
                    String hubURL = getHubURLSystemProperty();
                    url = new URL(hubURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if (setBrowserName.contains(Drivers.CHROME.getDriverValue())) {
                    driver = new RemoteWebDriver(url, getChromeCapabilities());
                } else {
                    driver = new RemoteWebDriver(url, getInternetExplorerCapabilities());
                }
                driver.manage().timeouts().implicitlyWait(propertiesLoader.getImplicitlyWaitTimeout(), TimeUnit.SECONDS);
                driver.manage().window().maximize();
                return driver;
        }
        throw new RuntimeException("Unsupported driver type");
    }

    private DesiredCapabilities getChromeCapabilities() {
        DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome();
        chromeCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        chromeCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
                UnexpectedAlertBehaviour.ACCEPT);
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return chromeCapabilities;
    }

    private DesiredCapabilities getInternetExplorerCapabilities() {
        DesiredCapabilities internetExplorerCapabilities = DesiredCapabilities.internetExplorer();
        internetExplorerCapabilities.setCapability("webdriver.ie.version", "11");
        internetExplorerCapabilities.setCapability("ignoreZoomSetting", true);
        internetExplorerCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        internetExplorerCapabilities.setCapability("ignoreProtectedModeSettings", true);
        return internetExplorerCapabilities;
    }

    public String getHubURL() {
        return hubURL;
    }
}