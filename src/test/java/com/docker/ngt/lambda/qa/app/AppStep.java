package com.docker.ngt.lambda.qa.app;

import java.io.IOException;

import com.docker.ngt.lambda.qa.driver.Driver;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

public class AppStep {

    private AppPage app;

    @Step("Go to url APP")
    public void gotoUrlPage() throws IOException, Exception{
        this.app = new AppPage(Driver.getDriver(true)).goToPage(System.getenv("APP_URL")).init();
    }

    @Step("Login APP <table>")
    public void login(Table dataTable){
        this.app.login(dataTable);
    }

    @Step("Logout APP")
    public void logout(){
        this.app.logout();
    }

    @Step("Select image repositorios <value>")
    public void selectRepositorios(String value) throws Exception{
        this.app.getExploreMenu().getSearchRepositorios(value);
    }

    @Step("Select Submenu of UserAccount <value>")
    public void selectSubMenuAccountUser(String value) throws Exception{
        this.app.getUserMenu().getSelectionSubMenuUser(value);
    }
    
    @Step("Change Account Setting <dataTable>")
    public void changeAccountUser(Table dataTable) throws Exception{
        this.app.getUserMenu().setAccountInformation(dataTable);
    }
}
