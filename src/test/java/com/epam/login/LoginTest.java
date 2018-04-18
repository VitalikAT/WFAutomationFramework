package com.epam.login;

import com.epam.bo.LoginBO;
import com.epam.core.injector.AccessPoint;
import com.epam.core.injector.Injector;
import com.epam.test.BaseTestClass;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

import static com.epam.constants.CommonConsts.PORTAL;
import static com.epam.constants.CommonConsts.USER_CTR_MSA;

public class LoginTest extends BaseTestClass {

    @Injector
    private LoginBO loginBO;

    @Test(description = "Check is login Successful")
//    @AccessPoint(portal = PORTAL, credentials = USER_CTR_MSA)
    public void loginCheck() {
        step("Verify if 'logout' link has appeared on Main page loginCheck");
       throw  new NoSuchElementException();
//        loginBO.isLoginSuccessful();
    }

    @Test(description = "Check is login Successful")
//    @AccessPoint(portal = PORTAL, credentials = USER_CTR_MSA)
    public void loginCheckPartOne() {
        step("Verify if 'logout' link has appeared on Main page loginCheckPartOne");
//        loginBO.isLoginSuccessful();
        throw  new NoSuchElementException();
    }

    @Test(description = "Check is login Successful")
//    @AccessPoint(portal = PORTAL, credentials = USER_CTR_MSA)
    public void loginCheckPartTwo() {
        step("Verify if 'logout' link has appeared on Main page loginCheckPartTwo");
//        loginBO.isLoginSuccessful();
        throw  new NoSuchElementException();
    }
}
