package com.docker.ngt.lambda.qa.app.tabs;

import com.docker.ngt.lambda.qa.page.AngularPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AppGetHelp extends AngularPage{

    private static By TABDOCUMENTATION;
    private static By TABFORUMS;
    private static By TABWHATSNEW;
    private static By TABSUPPORT;

    public AppGetHelp(WebDriver driver){
        super(driver);
    }

    public AppGetHelp init() throws Exception{
        TABDOCUMENTATION = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_DOCUMENTATION"));
        TABFORUMS = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_FORUMS"));
        TABWHATSNEW = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_WHATSNEW"));
        TABSUPPORT = createBy(System.getenv("BY_XPATH"), System.getenv("TAB_SUPPORT"));
        return this;
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