package com.htc.ngt.lambda.qa.driver;

import java.util.concurrent.TimeUnit;

import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import com.thoughtworks.gauge.Gauge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver {

    // Holds the WebDriver instance
    public static WebDriver webDriver=null;

    // Initialize a webDriver instance of required browser
    // Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver
    @BeforeSuite
    public void init(){
        getDriver();
    }

    public static WebDriver getDriver() {
        return getDriver(false);
    }

    /**
	 * @return the webDriver
	 */
	public static WebDriver getDriver(Boolean getNew) {
		if (null == webDriver || getNew == true) {
			if(webDriver  != null && getNew ==  true){
				webDriver.quit();
			}

			if(System.getenv("BROWSER").equals("firefox")){
				final FirefoxOptions firefoxOptions = new FirefoxOptions();
				WebDriverManager.firefoxdriver().setup();
				firefoxOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				firefoxOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				boolean headless = Boolean.valueOf(System.getenv("HEADLESS"));
				// All
				
				if (headless) {
					firefoxOptions.addArguments("headless", "window-size=1200,600");
					firefoxOptions.addArguments("no-sandbox");
					firefoxOptions.addArguments("disable-setuid-sandbox");
					firefoxOptions.addArguments("--lang=en_US");
					webDriver = new FirefoxDriver(firefoxOptions);
					
				} else {
					webDriver = new FirefoxDriver(firefoxOptions);
				}
				webDriver.manage().deleteAllCookies();
			}else{
				final ChromeOptions chromeOptions = new ChromeOptions();
				WebDriverManager.chromedriver().version(System.getenv("VERSION")).setup();
				chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
				boolean headless = Boolean.valueOf(System.getenv("HEADLESS"));
				// All
				
				if (headless) {
					chromeOptions.addArguments("headless", "window-size=1200,600");
					chromeOptions.addArguments("no-sandbox");
					chromeOptions.addArguments("disable-setuid-sandbox");
					chromeOptions.addArguments("--lang=en_US");
					webDriver = new ChromeDriver(chromeOptions);
				} else {
					chromeOptions.addArguments("--start-maximized");
					webDriver = new ChromeDriver(chromeOptions);
				}
				webDriver.manage().timeouts().pageLoadTimeout(Long.valueOf(System.getenv("MAX_TIME_LOAD_PAGE_TIMEOUT")), TimeUnit.SECONDS);
				webDriver.manage().deleteAllCookies();
			}
			
		}
		return webDriver;
	}

	public static void takePictureScreen(){
		Gauge.captureScreenshot();
	}

    // Close the webDriver instance
    @AfterSuite
    public void closeDriver(){
        webDriver.quit();
    }

}
