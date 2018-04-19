package com.epam.bo;

import com.epam.core.driver.WebDriverManager;
import com.epam.core.elements.Element;
import com.epam.pages.BasePO;
import com.epam.test.TestLogger;
import com.epam.utils.WaitManager;
import org.openqa.selenium.WebElement;

public abstract class BaseBO {

    protected WaitManager waitManager = new WaitManager();
    protected TestLogger LOG = TestLogger.getLogger();
    private BasePO basePO;

    public BaseBO() {
        basePO = new BasePO();
    }

    public void openPortal(String url) {
        WebDriverManager.getUrl(url);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            error(e.getMessage());
        }
    }

    public BaseBO clickOn(Element element) {
        basePO.clickOn(element);
        return this;
    }

    public BaseBO clickOnWebElement(WebElement element) {
        basePO.clickOnWebElement(element);
        return this;
    }

    public BaseBO doubleClickOn(Element element) {
        basePO.doubleClickOn(element);
        return this;
    }

    public void error(String message) {
        LOG.error(message);
    }

    public void step(String message) {
        LOG.info(message);
    }

    public void info(String message) {
        LOG.info(message);
    }


}
