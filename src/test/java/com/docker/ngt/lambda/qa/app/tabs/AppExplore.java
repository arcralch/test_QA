package com.docker.ngt.lambda.qa.app.tabs;

import com.docker.ngt.lambda.qa.page.AngularPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class AppExplore extends AngularPage {
    private static By TXTSEARCH, TXTLINKTITLE;
    private static final Integer TIME_WAIT = 5;

    public AppExplore(WebDriver driver) {
        super(driver);
    }

    public AppExplore init() throws Exception{
        TXTSEARCH = createBy(System.getenv("BY_XPATH"), System.getenv("TXT_SEARCH"));
        return this;
    }

    public AppExplore getSearchRepositorios(String value){
        waitForElement(TXTSEARCH);
        type(TXTSEARCH, value);
        wait(TIME_WAIT);
        Actions action = new Actions(driver());
        action.sendKeys(Keys.ENTER).build().perform();
        wait(TIME_WAIT);
        click(By.xpath("//div[text()='"+value+"']"));
        waitForElement(TXTLINKTITLE, MAX_TIME_WAIT);
        return this;
    }
}