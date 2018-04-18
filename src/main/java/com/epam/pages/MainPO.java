package com.epam.pages;

import com.epam.core.driver.WebDriverManager;
import com.epam.core.elements.Element;
import com.epam.core.elements.Image;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;

public class MainPO extends BasePO {

    @FindBy(xpath = "//a[@class='logo logoScaled']")
    private Image mainLogo;

    @FindBy(xpath = "//*[contains(@class, 'stb-MiniBar-Right')]")
    private Element logoutLink;

    public MainPO() {
        super();
    }

    public MainPO waitForPageToBeReady() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getDriver();

        //This loop will rotate for 100 times to check If page Is ready after every 1 second.
        for (int i = 0; i < 400; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            //To check page ready state.

            if (js.executeScript("return document.readyState").toString().equals("complete")) {
                break;
            }
        }
        return this;
    }

    public boolean isLogoutPresent() {
        return logoutLink.isDisplayed();
    }

}
