package com.runner.pages;

import com.runner.core.elements.Button;
import com.runner.core.elements.TextBox;
import org.openqa.selenium.support.FindBy;

public class LoginPO extends BasePO {
    @FindBy(xpath = "//input[contains(@class, 'login-form-input') and @id='username-input']")
    private TextBox userName;

    @FindBy(xpath = "//input[contains(@class, 'login-form-input') and @id='password-input']")
    private TextBox password;

    @FindBy(xpath = "//button[contains(@class,'login-form-login-button') and @id='login-button']")
    private Button loginButton;

    public LoginPO() {
        super();
    }

    public LoginPO userNameInput(String name) {
        waitManager.waitElement(userName);
        userName.clear();
        userName.sendKeys(name);
        return this;
    }

    public LoginPO passwordInput(String passWord) {
        waitManager.waitElement(password);
        password.clear();
        password.sendKeys(passWord);
        return this;
    }

    public LoginPO act_clickLoginButton() {
        waitManager.waitElement(loginButton);
        clickOn(loginButton);
        return this;
    }
}
