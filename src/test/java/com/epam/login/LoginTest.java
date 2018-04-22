package com.epam.login;

import com.epam.bo.LoginBO;
import com.epam.bo.MainBO;
import com.epam.core.injector.Injector;
import com.epam.test.BaseTestClass;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.epam.constants.CommonConsts.PORTAL;

public class LoginTest extends BaseTestClass {
    private static final String HOME_MENU_HEADER_TEXT = "Test a website's performance";
    private static final String ABOUT_MENU_HEADER_TEXT = "About WebPagetest.org";

    @Injector
    private LoginBO loginBO;
    @Injector
    private MainBO mainBO;
    //This method will be run a total of 5 times using 4 threads
    @Test(description = "login Check Main Page", threadPoolSize = 4, invocationCount = 5,  timeOut = 1000)
    public void loginCheckMainPage() {
        step("Open portal");
        loginBO.openPortalLogin(PORTAL);
        step("Verify if 'login' link has appeared on Main Page");
        Assert.assertTrue(mainBO.isLoginPresent(), "'login' link has not appeared on Main Page");
        step("Click on 'About' link on on Main page");
        mainBO.act_clickOnAboutMenuLink();
        Assert.assertTrue(mainBO.isAboutHeaderPresent(), "About Menu Header is not Present on Main Page");
        step("Check About Menu Header Text on Main page");
        Assert.assertEquals(mainBO.getAboutMenuHeaderText(), ABOUT_MENU_HEADER_TEXT, "About Menu Header text is not equals on Main Page");
        step("Click on 'Home' link on on Main page");
        mainBO.act_clickOnHomeMenuLink();
        Assert.assertTrue(mainBO.isHomeMenuHeaderPresent(), "Home Menu Header is not Present on Main Page");
        step("Check Home Menu Header Text on Main page");
        Assert.assertEquals(mainBO.getHomeMenuHeaderText(), HOME_MENU_HEADER_TEXT, "Home Menu Header text is not equals on Main Page");
    }

    @Test(description = "login Check Main Page")
    public void loginCheckMainPageTwo() {
        step("Open portal");
        loginBO.openPortalLogin(PORTAL);
        step("Verify if 'login' link has appeared on Main Page");
        Assert.assertTrue(mainBO.isLoginPresent(), "'login' link has not appeared on Main Page");
        step("Click on 'About' link on on Main page");
        mainBO.act_clickOnAboutMenuLink();
        Assert.assertTrue(mainBO.isAboutHeaderPresent(), "About Menu Header is not Present on Main Page");
        step("Check About Menu Header Text on Main page");
        Assert.assertEquals(mainBO.getAboutMenuHeaderText(), ABOUT_MENU_HEADER_TEXT, "About Menu Header text is not equals on Main Page");
        step("Click on 'Home' link on on Main page");
        mainBO.act_clickOnHomeMenuLink();
        Assert.assertTrue(mainBO.isHomeMenuHeaderPresent(), "Home Menu Header is not Present on Main Page");
        step("Check Home Menu Header Text on Main page");
        Assert.assertEquals(mainBO.getHomeMenuHeaderText(), HOME_MENU_HEADER_TEXT, "Home Menu Header text is not equals on Main Page");
    }

    @Test(description = "login Check Main Page")
    public void loginCheckMainPageThree() {
        step("Open portal");
        loginBO.openPortalLogin(PORTAL);
        step("Verify if 'login' link has appeared on Main Page");
        Assert.assertTrue(mainBO.isLoginPresent(), "'login' link has not appeared on Main Page");
        step("Click on 'About' link on on Main page");
        mainBO.act_clickOnAboutMenuLink();
        Assert.assertTrue(mainBO.isAboutHeaderPresent(), "About Menu Header is not Present on Main Page");
        step("Check About Menu Header Text on Main page");
        Assert.assertEquals(mainBO.getAboutMenuHeaderText(), ABOUT_MENU_HEADER_TEXT, "About Menu Header text is not equals on Main Page");
        step("Click on 'Home' link on on Main page");
        mainBO.act_clickOnHomeMenuLink();
        Assert.assertTrue(mainBO.isHomeMenuHeaderPresent(), "Home Menu Header is not Present on Main Page");
        step("Check Home Menu Header Text on Main page");
        Assert.assertEquals(mainBO.getHomeMenuHeaderText(), HOME_MENU_HEADER_TEXT, "Home Menu Header text is not equals on Main Page");
    }
}
