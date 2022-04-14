package com.docker.ngt.lambda.qa.app.tabs;

import com.docker.ngt.lambda.qa.page.AngularPage;

import org.openqa.selenium.WebDriver;

public class AppRepositories extends AngularPage{

    public AppRepositories(WebDriver driver){
        super(driver);
    }

    public AppRepositories init(){
        
        return this;
    }
}