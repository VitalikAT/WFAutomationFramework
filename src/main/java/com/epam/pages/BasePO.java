package com.epam.pages;

import com.epam.core.driver.WebDriverManager;
import com.epam.core.elements.Element;
import com.epam.core.elements.FieldDecorator;
import com.epam.test.TestLogger;
import com.epam.utils.WaitManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import static com.epam.core.driver.WebDriverManager.actions;

public class BasePO {

    protected WaitManager waitManager = new WaitManager();
    protected TestLogger LOG = TestLogger.getLogger();

    public BasePO() {
        PageFactory.initElements(new FieldDecorator(WebDriverManager.getDriver()), this);
    }

    public void clickOn(Element element) {
        waitManager.untilClickable(element);
        element.click();
        waitManager.waitTillPageLoaded();
    }

    public void clickOnWebElement(WebElement element) {
        waitManager.untilClickable(element);
        new Element(element).click();
        waitManager.waitTillPageLoaded();
    }

    public void doubleClickOn(Element element) {
        waitManager.untilClickable(element);
        actions().doubleClick(element).build().perform();
        waitManager.waitTillPageLoaded();
    }

    protected void typeInto(Element element, String typeText) {
        waitManager.waitElement(element);
        element.sendKeys(typeText);
        waitManager.waitTillPageLoaded();
    }

    public void error(String message) {
        LOG.error(message);
    }
}
