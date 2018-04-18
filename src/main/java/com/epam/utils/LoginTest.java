package com.epam.utils;

import com.epam.bo.LoginBO;
import com.epam.core.injector.AccessPoint;
import com.epam.core.injector.Injector;
import com.epam.test.BaseTestClass;
import org.testng.annotations.Test;

import static com.epam.constants.CommonConsts.PORTAL;
import static com.epam.constants.CommonConsts.USER_CTR_MSA;

public class LoginTest extends BaseTestClass {

    @Injector
    private LoginBO loginBO;

    @Test(description = "Check is login Successful")
    @AccessPoint(portal = PORTAL, credentials = USER_CTR_MSA)
    public void loginCheck() {
        loginBO.isLoginSuccessful();
    }
}
