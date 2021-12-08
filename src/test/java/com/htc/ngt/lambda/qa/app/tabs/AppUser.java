package com.htc.ngt.lambda.qa.app.tabs;

import java.util.List;

import com.htc.ngt.lambda.qa.page.AngularPage;
import com.thoughtworks.gauge.Table;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppUser extends AngularPage {

    private static final int FIELD = 0;
    private static final int VALUE = 1;
    private static final By USERNAME = By.xpath("//span[text()='htcqa']");
    private static final By TABPROFILE = By.xpath("//li[text()='My Profile']");
    private static final By TABCONTENT = By.xpath("//li[text()='My Content']");
    private static final By TABACCOUNTSETTING = By.xpath("//li[text()='Account Settings']");
    private static final By TABBILLING = By.xpath("//li[text()='Billing']");
    private static final By TXTFULLNAME = By.name("full_name");
    private static final By BTNSAVE = By.xpath("//span[text()='Save']");

    public AppUser(WebDriver driver) {
        super(driver);
    }

    public AppUser getSelectionSubMenuUser(String value){
        waitForElement(USERNAME);
        click(USERNAME);
        waitForElement(TABPROFILE);
        switch(value){
            case "My Content": click(TABCONTENT); break;
            case "Account Settings": click(TABACCOUNTSETTING); break;
            case "Billing": click(TABBILLING); break;
            default: click(TABPROFILE); break;
        }
        return this;
    }

    public AppUser setAccountInformation(Table dataTable){
        waitForElement(TXTFULLNAME);
        dataTable.getTableRows().forEach(row -> {
			String field = row.getTableCells().get(FIELD).getValue();
            String value = row.getTableCells().get(VALUE).getValue();
			List<WebElement> inputs = els(By.xpath("//input[@name='" + field + "']"));       
            for (WebElement input : inputs) {
				if (input.isDisplayed()) {
                    click(input);
                    input.clear();
					input.sendKeys(value);
					break;
				}
			}
        });
        clickByAction(BTNSAVE);
        wt(MAX_TIME_WAIT);
        return this;
    }
}