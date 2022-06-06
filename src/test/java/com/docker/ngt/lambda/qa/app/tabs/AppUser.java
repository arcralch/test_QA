package com.docker.ngt.lambda.qa.app.tabs;

import java.util.List;

import com.docker.ngt.lambda.qa.page.AngularPage;
import com.thoughtworks.gauge.Table;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AppUser extends AngularPage {

    private static final int FIELD = 0;
    private static final int VALUE = 1;
    private static By USERNAME;
    private static By TABPROFILE;
    private static By TABCONTENT;
    private static By TABACCOUNTSETTING;
    private static By TABBILLING;
    private static By TXTFULLNAME;
    private static By BTNSAVE;

    public AppUser(WebDriver driver) {
        super(driver);
    }

    public AppUser init() throws Exception{
        USERNAME = createBy(System.getenv("BY_XPATH"), System.getenv("LBL_USERNAME"));
        TABPROFILE = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_PROFILE"));
        TABCONTENT = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_CONTENT"));
        TABACCOUNTSETTING = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_ACCOUNTSETTING"));
        TABBILLING = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_BILLING"));
        BTNSAVE = createBy(System.getenv("BY_XPATH"), System.getenv("BTN_SAVE"));
        TXTFULLNAME = createBy(System.getenv("BY_NAME"), System.getenv("TXT_FULLNAME"));
        return this;
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