package com.epam.pages;

import com.epam.core.driver.WebDriverManager;
import com.epam.core.elements.Element;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;

public class MainPO extends BasePO {

    @FindBy(xpath = "//*[contains(@class, 'logo')]/a")
    private Element mainLogo;

    @FindBy(xpath = "//*[@class='page']/a[text()='Login']")
    private Element loginLink;

    @FindBy(xpath = "//*[contains(@title, 'Home')]")
    private Element homeMenuLink;

    @FindBy(xpath = "//*[contains(@name, 'urlEntry')]/h2")
    private Element homeMenuHeader;

    @FindBy(xpath = "//*[contains(@title, 'About')]")
    private Element aboutMenuLink;

    @FindBy(xpath = "//*[contains(@class, 'translucent')]/h2")
    private Element aboutHeader;

    public MainPO() {
        super();
    }

    public MainPO waitForPageToBeReady() {
        JavascriptExecutor js = (JavascriptExecutor) WebDriverManager.getInstance().getDriver();
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

    public MainPO act_clickHomeMenuLink() {
        clickOn(homeMenuLink);
        return this;
    }

    public MainPO act_clickAboutMenuLink() {
        clickOn(aboutMenuLink);
        return this;
    }

    public boolean isLoginPresent() {
        return loginLink.isDisplayed();
    }

    public boolean isHomeMenuHeaderPresent() {
        return homeMenuHeader.isDisplayed();
    }

    public boolean isAboutHeaderPresent() {
        return loginLink.isDisplayed();
    }

    public String getMainLogoText() {
        return mainLogo.getText();
    }

    public String getHomeMenuHeaderText() {
        return homeMenuHeader.getText();
    }

    public String getAboutMenuHeaderText() {
        return aboutHeader.getText();
    }
}
