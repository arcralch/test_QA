package com.docker.ngt.lambda.qa.app.tabs;

import com.docker.ngt.lambda.qa.page.AngularPage;

import org.openqa.selenium.WebDriver;

public class AppOrganizations extends AngularPage {

    public AppOrganizations(WebDriver driver) {
        super(driver);
    }

    public AppOrganizations init(){

        return this;
    }
}