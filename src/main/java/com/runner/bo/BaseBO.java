package com.runner.bo;

import com.runner.core.driver.WebDriverManager;
import com.runner.core.elements.Element;
import com.runner.pages.BasePO;
import com.runner.test.MyLogHolder;
import com.runner.utils.WaitManager;
import org.openqa.selenium.WebElement;

import static com.runner.test.MyLogHolder.info;

public abstract class BaseBO {

    protected WaitManager waitManager = new WaitManager();
    private BasePO basePO;

    public BaseBO() {
        basePO = new BasePO();
    }

    public void openPortal(String url) {
        WebDriverManager.getInstance().getUrl(url);
        WaitManager.waitForPageLoaded();
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

    public static void error(String message) {
        MyLogHolder.error(message);
    }

    public static void step(String message) {
        info(message);
    }
}
