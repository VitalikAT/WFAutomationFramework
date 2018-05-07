package com.epam.pages;

import com.epam.core.driver.WebDriverManager;
import com.epam.core.elements.Element;
import com.epam.core.elements.FieldDecorator;
import com.epam.test.MyLogHolder;
import com.epam.utils.WaitManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;


public class BasePO {

    protected WaitManager waitManager = new WaitManager();

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
//        actions().doubleClick(element).build().perform();
        waitManager.waitTillPageLoaded();
    }

    protected void typeInto(Element element, String typeText) {
        waitManager.waitElement(element);
        element.sendKeys(typeText);
        waitManager.waitTillPageLoaded();
    }

    public void error(String message) {
        MyLogHolder.error(message);
    }
}
