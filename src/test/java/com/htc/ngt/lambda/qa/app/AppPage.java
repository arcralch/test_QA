package com.htc.ngt.lambda.qa.app;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.htc.ngt.lambda.qa.app.tabs.AppExplore;
import com.htc.ngt.lambda.qa.app.tabs.AppGetHelp;
import com.htc.ngt.lambda.qa.app.tabs.AppOrganizations;
import com.htc.ngt.lambda.qa.app.tabs.AppRepositories;
import com.htc.ngt.lambda.qa.app.tabs.AppUser;
import com.htc.ngt.lambda.qa.driver.Driver;
import com.htc.ngt.lambda.qa.page.AngularPage;
import com.thoughtworks.gauge.Table;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AppPage extends AngularPage {

    private static final int FIELD = 0;
	private static final int VALUE = 1;
    private static final By LOGOAPP = By.xpath("//div[text()='Welcome Back']");
    private static final By LOGINAPP = By.xpath("//div[text()='Sign In']");
    private static final By USERNAME = By.xpath("//span[text()='htcqa']");
    private static final By LOGOUT = By.id("logoutButton");
    private static final Map<Tab, By> tabs = new EnumMap<>(Tab.class);
    private static final By TABEXPLORE = By.xpath("//a[text()='Explore']");
    private static final By TABREPOSITORIES = By.xpath("//a[text()='Repositories']");
    private static final By TABORGANIZATIONS = By.xpath("//a[text()='Organizations']");
    private static final By TABGETHELP = By.xpath("//div[text()='Get Help']");


    public AppPage(WebDriver driver){
        super(driver);
    }

    public enum Tab {
        EXPLORE ,REPOSITORIES, ORGANIZATIONS, GETHELP
    }

    static{
        //Tabs Mapping
        tabs.put(Tab.EXPLORE, TABEXPLORE);
        tabs.put(Tab.REPOSITORIES, TABREPOSITORIES);
        tabs.put(Tab.ORGANIZATIONS, TABORGANIZATIONS);
        tabs.put(Tab.GETHELP, TABGETHELP);
    }

    public AppPage(WebDriver driver, String url){
        super(driver);
        driver().navigate().to(url);
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
			List<WebElement> inputs = els(By.xpath("//input[@id='" + field + "']"));       
            for (WebElement input : inputs) {
				if (input.isDisplayed()) {
					click(input);
					input.sendKeys(value);
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
        Actions action = new Actions(driver());
        action.sendKeys(Keys.ENTER).build().perform();
        waitForElement(tabs.get(Tab.REPOSITORIES), this.MAX_TIME_WAIT);
        return this;
    }

    public void logout(){
        waitForElement(USERNAME);
        click(USERNAME);
        waitForElement(LOGOUT);
        click(LOGOUT);
    }

    public AppExplore getExploreMenu(){
        waitForElement(tabs.get(Tab.EXPLORE));
        click(tabs.get(Tab.EXPLORE));
        return new AppExplore(driver());
    }

    public AppRepositories getRepositoriesMenu() {
        waitForElement(tabs.get(Tab.REPOSITORIES));
        click(tabs.get(Tab.REPOSITORIES));
        return new AppRepositories(driver());
    }

    public AppOrganizations getOrganizationsMenu(){
        waitForElement(tabs.get(Tab.ORGANIZATIONS));
        click(tabs.get(Tab.ORGANIZATIONS));
        return new AppOrganizations(driver());
    }

    public AppGetHelp getGetHelpMenu(){
        waitForElement(tabs.get(Tab.GETHELP));
        click(tabs.get(Tab.GETHELP));
        return new AppGetHelp(driver());
    }

    public AppUser getUserMenu(){
        return new AppUser(driver());
    }

}