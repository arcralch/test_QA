package com.htc.ngt.lambda.qa.app.tabs;

import java.util.List;

import com.htc.ngt.lambda.qa.page.AngularPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AppExplore extends AngularPage {
    private static final By TXTSEARCH = By.xpath("//*[@id='app']/div/div/header/nav/div[1]/div/div/input");
    private static final By TXTTITLE = By.className("styles__name___2198b");
    private static final By TXTLINKTITLE = By.className("styles__productName___1dnBS");
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
        List<WebElement> ls = els(TXTTITLE);
        for(WebElement lst:ls){
            if(lst.getText().equals(value)){
                lst.click();
                break;
            }
        }
        waitForElement(TXTLINKTITLE, this.MAX_TIME_WAIT);
        return this;
    }
}