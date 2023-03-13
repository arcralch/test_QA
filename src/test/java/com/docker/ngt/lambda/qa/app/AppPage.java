package com.docker.ngt.lambda.qa.app;

import java.io.IOException;
import java.util.List;

import com.docker.ngt.lambda.qa.app.tabs.AppExplore;
import com.docker.ngt.lambda.qa.app.tabs.AppGetHelp;
import com.docker.ngt.lambda.qa.app.tabs.AppOrganizations;
import com.docker.ngt.lambda.qa.app.tabs.AppRepositories;
import com.docker.ngt.lambda.qa.app.tabs.AppUser;
import com.docker.ngt.lambda.qa.driver.Driver;
import com.docker.ngt.lambda.qa.page.AngularPage;
import com.thoughtworks.gauge.Table;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AppPage extends AngularPage {

    private static final int FIELD = 0;
	private static final int VALUE = 1;

    private static By LOGOAPP;
    private static By LOGINAPP;
    private static By USERNAME;
    private static By LOGOUT;
    private static By TABEXPLORE;
    private static By TABREPOSITORIES;
    private static By TABORGANIZATIONS;
    private static By TABGETHELP;
    

    public AppPage init() throws Exception,IOException {
        //loading selector from properties
        LOGOAPP = createBy(System.getenv("BY_XPATH"), System.getenv("LBL_LOGOAPP"));
        LOGINAPP = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_SIGNIN"));
        USERNAME = createBy(System.getenv("BY_XPATH"), System.getenv("LBL_USERNAME"));
        LOGOUT = createBy(System.getenv("BY_XPATH"), System.getenv("LBL_LOGOUT"));

        TABEXPLORE = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_EXPLORE"));
        TABREPOSITORIES = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_REPOSITORIES"));
        TABORGANIZATIONS = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_ORGANIZATIONS"));
        TABGETHELP = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_GETHELP"));
        return this;
    }

    public AppPage(WebDriver driver){
        super(driver);
    }

    public AppPage goToPage(String url){
        driver().navigate().to(url);
        return this;
    }

    public AppPage login(Table dataTable){
        if(this.BROWSER.equals("chrome")){
            waitForElement(LOGINAPP);
			WebElement buttom = Driver.webDriver.findElement(LOGINAPP);
			if (buttom.isDisplayed()) {
				click(buttom);
			}
		}
		waitForElement(LOGOAPP);
		dataTable.getTableRows().forEach(row -> {
			String field = row.getTableCells().get(FIELD).getValue();
            String value = row.getTableCells().get(VALUE).getValue();
			List<WebElement> inputs = els(By.xpath("//*[@id='" + field + "']"));       
            for (WebElement input : inputs) {
				if (input.isDisplayed()) {
					click(input);
					input.sendKeys(value);
                    Actions action = new Actions(driver());
                    action.sendKeys(Keys.ENTER).build().perform();
					break;
				}
			}
            
			/*
			 * List<WebElement> buttons =
			 * driver().findElements(By.xpath("//*[contains(@type, 'button')]")); Click: for
			 * (WebElement button : buttons) { if (button.isDisplayed()) {
			 * action.moveToElement(button); click(button); break Click; } }
			 */	
        });
        wait(5);
        return this;
    }

    public void logout(){
        waitForElement(USERNAME);
        click(USERNAME);
        waitForElement(LOGOUT);
        click(LOGOUT);
    }

    public AppExplore getExploreMenu() throws Exception{
        waitForElement(TABEXPLORE);
        click(TABEXPLORE);
        return new AppExplore(driver()).init();
    }

    public AppRepositories getRepositoriesMenu() {
        waitForElement(TABREPOSITORIES);
        click(TABREPOSITORIES);
        return new AppRepositories(driver()).init();
    }

    public AppOrganizations getOrganizationsMenu(){
        waitForElement(TABORGANIZATIONS);
        click(TABORGANIZATIONS);
        return new AppOrganizations(driver()).init();
    }

    public AppGetHelp getGetHelpMenu() throws Exception{
        waitForElement(TABGETHELP);
        click(TABGETHELP);
        return new AppGetHelp(driver()).init();
    }

    public AppUser getUserMenu() throws Exception{
        return new AppUser(driver()).init();
    }

}