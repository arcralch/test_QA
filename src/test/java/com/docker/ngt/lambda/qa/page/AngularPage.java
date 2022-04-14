package com.docker.ngt.lambda.qa.page;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.docker.ngt.lambda.qa.util.SearchResult;
import com.paulhammant.ngwebdriver.ByAngular;
import com.paulhammant.ngwebdriver.NgWebDriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * AngularPage
 */
public abstract class AngularPage extends TestPage {

    final NgWebDriver ngWebDriver;
    protected int DEFAULT_SECONDS_WAIT_A;

    public AngularPage(WebDriver driver){
        super(driver);
        this.DEFAULT_SECONDS_WAIT_A = Integer.parseInt(System.getenv("DEFAULT_SECONDS_WAIT_A"));
        this.ngWebDriver = new NgWebDriver((JavascriptExecutor) driver);
        this.ngWebDriver.waitForAngularRequestsToFinish();
    }

    protected NgWebDriver angular(){
        this.DEFAULT_SECONDS_WAIT_A = Integer.parseInt(System.getenv("DEFAULT_SECONDS_WAIT_A"));
        return this.ngWebDriver;
    }

    protected void waitForAngular(){
        waitForAngular(DEFAULT_SECONDS_WAIT_A);
    }

    protected void waitForAngular(int secondsTimeout){
        driver().manage().timeouts().setScriptTimeout(secondsTimeout, TimeUnit.SECONDS);
        ngWebDriver.waitForAngularRequestsToFinish();
    }

    protected String jsonModel(WebElement el, String model){
        return angular().retrieveJson(el, model);
    }

    protected SearchResult repeaterResult(String repeaterExpr, String object){
        By repeater = ByAngular.repeater(repeaterExpr);
        List<Object> result = new ArrayList<Object>();
        els(repeater).forEach(item -> {
            result.add(angular().retrieve(item, object));
        });
        return new SearchResult(result);
    }
}