package com.epam.core.elements;

import com.epam.core.driver.WebDriverManager;
import com.epam.enums.Drivers;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

import static com.epam.test.MyLogHolder.debug;

public class Element implements IElement {
    private WebElement webElement;

    public Element(WebElement webElement) {
        this.webElement = webElement;
    }

    public String getInfo() {
        return this.webElement.toString();
    }

    @Override
    public void clear() {
        debug("Clearing element with selector" + getSelector());
        getWebElement().clear();
    }

    @Override
    public void click() {
        if (((RemoteWebDriver) WebDriverManager.getDriver()).getCapabilities().getBrowserName().toLowerCase().equals(Drivers.IE.getDriverValue())) {
            ((JavascriptExecutor) WebDriverManager.getDriver())
                    .executeScript("arguments[0].click();", getWebElement());
        } else
            getWebElement().click();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        getWebElement().sendKeys(charSequences);
    }

    @Override
    public void sendKeys(String text) {
        debug("Entering text: " + text + " to element with selector" + getSelector());
        getWebElement().sendKeys(text);
    }

    @Override
    public String getText() {
        return getWebElement().getText();
    }

    @Override
    public WebElement getWebElement() {
        return webElement;
    }

    @Override
    public String getTagName() {
        return getWebElement().getTagName();
    }

    @Override
    public String getAttribute(String attribute) {
        return getWebElement().getAttribute(attribute);
    }

    @Override
    public boolean isSelected() {
        return getWebElement().isSelected();
    }

    @Override
    public boolean isEnabled() {
        return getWebElement().isEnabled();
    }

    @Override
    public void submit() {
        getWebElement().submit();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return getWebElement().findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return getWebElement().findElement(by);
    }

    @Override
    public boolean isDisplayed() {
        return getWebElement().isDisplayed();
    }

    @Override
    public Point getLocation() {
        return getWebElement().getLocation();
    }

    @Override
    public Dimension getSize() {
        return getWebElement().getSize();
    }

    @Override
    public Rectangle getRect() {
        return getWebElement().getRect();
    }

    @Override
    public String getCssValue(String cssValue) {
        return getWebElement().getCssValue(cssValue);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return getWrappedElement().getScreenshotAs(outputType);
    }

    @Override
    public Coordinates getCoordinates() {
        return ((Locatable) getWebElement()).getCoordinates();
    }

    @Override
    public WebElement getWrappedElement() {
        return getWebElement();
    }

    public String getSelector() {
        return this.webElement.toString().split("]")[1];
    }

}