package com.runner.bo;

import com.runner.model.User;
import com.runner.pages.LoginPO;
import com.runner.pages.MainPO;

import static com.runner.test.MyLogHolder.info;
import static org.testng.Assert.assertTrue;

public class LoginBO extends BaseBO {

    private LoginPO loginPage;
    private MainPO mainPage;

    public LoginBO() {
        loginPage = new LoginPO();
        mainPage = new MainPO();
    }

    public LoginBO loginWithValidCredentials(String url, User user) {
        openPortal(url);
        LoginPO loginPage = new LoginPO();
        step("Login to portal as " + user.getName() + " on Login page");
        loginPage
                .userNameInput(user.getName())
                .passwordInput(user.getPassword());
        info("Press Login button with valid credentials on Login page.");
        loginPage.act_clickLoginButton();
        waitManager.waitTillPageLoaded();
        return this;

    }

    public LoginBO isLoginSuccessful() {
        info("Verify if 'logout' link has appeared on Main page");
        waitManager.waitTillPageLoaded();
        assertTrue(mainPage.isLoginPresent(), "User is not logged in!");
        return this;

    }

    public MainBO clickOnLoginButton() {
        info("Click on login button on Login page.");
        waitManager.waitTillPageLoaded();
        loginPage.act_clickLoginButton();
        return new MainBO();

    }

    public void openPortalLogin(String url) {
        info("Open portal on Main page");
        openPortal(url);
    }
}
