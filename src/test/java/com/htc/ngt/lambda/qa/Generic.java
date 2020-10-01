package com.htc.ngt.lambda.qa;

import com.htc.ngt.lambda.qa.driver.Driver;
import com.thoughtworks.gauge.Step;

/**
 * Generic
 */
public class Generic{

    @Step("take screenshot")
    public void takeScreenshot(){
        Driver.takePictureScreen();
    }
}