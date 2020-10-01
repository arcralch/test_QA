package com.htc.ngt.lambda.qa.app.tabs;

import com.htc.ngt.lambda.qa.page.AngularPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AppGetHelp extends AngularPage{

    private static final By TABDOCUMENTATION = By.xpath("//li[text()='Documentation']");
    private static final By TABFORUMS = By.xpath("//li[text()='Forums']");
    private static final By TABWHATSNEW = By.xpath("//li[text()='What’s New']");
    private static final By TABSUPPORT = By.xpath("//li[text()='Support']");

    public AppGetHelp(WebDriver driver){
        super(driver);
    }

    public AppGetHelp getSelectionSubMenu(String value){
        waitForElement(TABDOCUMENTATION);
        switch(value){
            case "Documentation" : click(TABDOCUMENTATION); break;
            case "Forums" : click(TABFORUMS); break;
            case "Whats New" : click(TABWHATSNEW); break;
            default : click(TABSUPPORT); break;
        }
        wait(20);
        return this;
    }
}