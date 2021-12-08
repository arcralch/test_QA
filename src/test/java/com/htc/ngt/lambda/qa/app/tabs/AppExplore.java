package com.htc.ngt.lambda.qa.app.tabs;

import com.htc.ngt.lambda.qa.page.AngularPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

public class AppExplore extends AngularPage {
    private static final By TXTSEARCH = By.xpath("//*[@placeholder='Search for great content (e.g., mysql)']");
    private static final By TXTLINKTITLE = By.className("styles__productNameLine___2QJsS");
    private static final Integer TIME_WAIT = 5;

    public AppExplore(WebDriver driver) {
        super(driver);
    }

    public AppExplore getSearchRepositorios(String value){
        waitForElement(TXTSEARCH);
        type(TXTSEARCH, value);
        Actions action = new Actions(driver());
        action.sendKeys(Keys.ENTER).build().perform();
        wait(TIME_WAIT);
        click(By.xpath("//div[text()='"+value+"']"));
        waitForElement(TXTLINKTITLE, this.MAX_TIME_WAIT);
        return this;
    }
}