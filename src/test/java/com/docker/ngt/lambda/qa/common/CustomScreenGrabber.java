package com.docker.ngt.lambda.qa.common;

import com.docker.ngt.lambda.qa.driver.Driver;

import java.net.MalformedURLException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CustomScreenGrabber{
    public byte[] takeScreenshot() throws MalformedURLException{
        WebDriver driver = Driver.getDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}