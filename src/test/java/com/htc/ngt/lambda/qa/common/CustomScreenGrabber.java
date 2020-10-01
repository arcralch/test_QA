package com.htc.ngt.lambda.qa.common;

import com.htc.ngt.lambda.qa.driver.Driver;
import com.thoughtworks.gauge.screenshot.ICustomScreenshotGrabber;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CustomScreenGrabber implements ICustomScreenshotGrabber {
    public byte[] takeScreenshot(){
        WebDriver driver = Driver.getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}