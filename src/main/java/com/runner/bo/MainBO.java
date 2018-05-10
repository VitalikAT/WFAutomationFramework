package com.runner.bo;

import com.runner.pages.MainPO;

import static com.runner.test.MyLogHolder.info;

public class MainBO extends BaseBO {
    private MainPO mainPO;

    public MainBO() {
        mainPO = new MainPO();
    }

    public void openPortal(String url) {
        info("Open portal on Main page");
        openPortal(url);
    }

    public MainBO act_clickOnHomeMenuLink() {
        info("Click on 'Home' link on on Main page");
        mainPO.act_clickHomeMenuLink();
        return this;
    }

    public MainBO act_clickOnAboutMenuLink() {
        info("Click on 'About' link on on Main page");
        mainPO.act_clickAboutMenuLink();
        return this;
    }

    public boolean isLoginPresent() {
        info("Check is login link present on Main page");
        return mainPO.isLoginPresent();
    }

    public boolean isHomeMenuHeaderPresent() {
        info("Check is Home Menu Header present on Main page");
        return mainPO.isHomeMenuHeaderPresent();
    }

    public boolean isAboutHeaderPresent() {
        info("Check is About Menu Header present on Main page");
        return mainPO.isAboutHeaderPresent();
    }

    public String getMainLogoText() {
        info("Get Main Logo Text on Main page");
        return mainPO.getMainLogoText();
    }

    public String getHomeMenuHeaderText() {
        info("Get Home Menu Header Text on Main page");
        return mainPO.getHomeMenuHeaderText();
    }

    public String getAboutMenuHeaderText() {
        info("Get About Menu Header Text on Main page");
        return mainPO.getAboutMenuHeaderText();
    }
}