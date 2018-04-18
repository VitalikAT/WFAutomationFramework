package com.epam.core.elements;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.internal.WrapsElement;

import java.util.List;

public interface IElement extends WebElement, WrapsElement, Locatable {
    String getInfo();

    void clear();

    void click();

    void sendKeys(String text);

    String getText();

    WebElement getWebElement();

    String getTagName();

    String getAttribute(String attribute);

    boolean isSelected();

    boolean isEnabled();

    void submit();

    void sendKeys(CharSequence... charSequences);

    List<WebElement> findElements(By by);

    WebElement findElement(By by);

    boolean isDisplayed();

    Point getLocation();

    Dimension getSize();

    Rectangle getRect();

    String getCssValue(String cssValue);

    <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException;

    Coordinates getCoordinates();

    WebElement getWrappedElement();
}
