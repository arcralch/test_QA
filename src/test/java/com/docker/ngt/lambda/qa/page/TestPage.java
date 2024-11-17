package com.docker.ngt.lambda.qa.page;

import java.io.File;
import java.time.Duration;
import java.util.List;

import com.docker.ngt.lambda.qa.util.RandomInteger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * A test page
 */
public abstract class TestPage{

    protected final int MAX_TIME_WAIT;
    protected final int MAX_TIME_WAIT_MODAL;
    protected final int MIN_TIME_WAIT;
    protected final String BROWSER;
    //private static final int FIELD = 0;
    private static final int VALUE = 1;

    private WebDriver driver;
    
    public TestPage(WebDriver driver){
        this.MAX_TIME_WAIT = Integer.parseInt(System.getenv("MAX_TIME_WAIT"));
        this.MAX_TIME_WAIT_MODAL = Integer.parseInt(System.getenv("MAX_TIME_WAIT_MODAL"));
        this.MIN_TIME_WAIT = Integer.parseInt(System.getenv("MIN_TIME_WAIT"));
        this.BROWSER = System.getenv("BROWSER");
        this.driver = driver;
    }

    protected void executeScript(String script){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script);
    }

    protected void executeScript(String script, Object arguments) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, arguments);
    }

    protected WebElement el(By by){
        return driver.findElement(by);
    }

    protected WebDriver switchFrame(By by){
        driver = driver().switchTo().frame(el(by));
        return driver;
    }

    protected boolean isElementPresent(By by){
        try{
            driver.findElement(by);
            return true;
        }catch (org.openqa.selenium.NoSuchElementException e){
            return false;
        }
    }

    protected List<WebElement> els(By by){
        return driver.findElements(by);
    }

    protected void click(By by){
        waitElement(by);
        el(by).click();
    }

    protected void click(WebElement element){
        element.click();
    }

    protected void clickElementNotVisible(By el1, By el2){
        wait(5);
        WebElement we = driver().findElement(el1);
        Actions builder = new Actions(driver());
        builder.moveToElement(we).perform();
        click(el2);
        wait(4);
    }

    protected void type(By by, String str){
        el(by).sendKeys(str);
    }

    protected void typeJS(By field, String value){
        StringBuilder sb = new StringBuilder();
        sb.append("document.getElementsByName('");
        sb.append(field);
        sb.append("')[0].value=");
        sb.append("'");
        sb.append(value);
        sb.append("';");
        executeScript(sb.toString());
    }

    protected void clearElement(By by){
        el(by).clear();
    }

    protected void select(By by, String str){
        Select objSelect = new Select(el(by));
        objSelect.selectByVisibleText(str);
    }

    protected String getRandomOption(By by){
        Select objSelect = new Select(el(by));
        List<WebElement> lstElements = objSelect.getOptions();
        return lstElements.get(RandomInteger.getRandomNumberInRange(1, lstElements.size() -1)).getText();
    }

    protected void waitForElement(By element, Integer timeOutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(timeOutInSeconds));
        wait.until(ExpectedConditions.or(ExpectedConditions.elementToBeClickable(element),
                ExpectedConditions.presenceOfElementLocated(element), ExpectedConditions.elementToBeSelected(element)));
    }

    protected void waitForElement(By element) {
        WebDriverWait wait = new WebDriverWait(driver(), Duration.ofSeconds(MAX_TIME_WAIT));
        wait.until(ExpectedConditions.or(ExpectedConditions.elementToBeClickable(element),
                ExpectedConditions.presenceOfElementLocated(element), ExpectedConditions.elementToBeSelected(element)));
    }

    protected void waitElement(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(MAX_TIME_WAIT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    protected void waitIfElementVisible(By element, long timeOutInSeconds) throws InterruptedException {
        long i = VALUE;
        long iTime = VALUE;
        while(els(element).size() >= VALUE){
            if(el(element).isDisplayed()){
                wt(i*1000);
                iTime++;
                if(iTime > timeOutInSeconds){
                    throw new InterruptedException("Time Exception");
                }
            }
        }
    }

    protected void wt(long millis){
        try{
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    protected void wait(int seconds){
        wt(1000 * seconds);
    }

    public void waitDownloadFile(String fullPathToFile){
        try{
            File file = new File(fullPathToFile);
            while(!file.exists()){
                Thread.sleep(1000);
            }
        }catch (Exception e){
        }
    }

    public void clickByAction(By by){
        WebElement element = driver.findElement(by);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
    }

    protected WebDriver driver() {
        return this.driver;
    }

    protected boolean existText(By element){
        WebDriver driver = driver();
        try{
            driver.findElement(element);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    protected String getTextElement(By by){
        return el(by).getText();
    }

    protected void windowForScroll(int x, int y){
        executeScript("window.scrollBy("+ x +","+ y +")");
    }

    public By createBy(String typeSelector, String query) throws Exception {
        By byObject = null;
        switch (typeSelector) {
            case "cssSelector":
                byObject = new By.ByCssSelector(query);
                break;
            case "xpath":
                byObject = new By.ByXPath(query);
                break;
            case "id":
                byObject = new By.ById(query);
                break;
            case "className":
                byObject = new By.ByClassName(query);
                break;
            case "name":
                byObject = new By.ByName(query);
                break;
            case "linkText":
                byObject = new By.ByLinkText(query);
                break;
            case "partialLinkText":
                byObject = new By.ByPartialLinkText(query);
                break;
            case "tagName":
                byObject = new By.ByTagName(query);
                break;
            default:
                throw new Exception("Option is not available");
        }

        return byObject;
    }
}