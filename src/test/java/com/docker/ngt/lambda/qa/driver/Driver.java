package com.docker.ngt.lambda.qa.driver;

import java.time.Duration;
import java.net.MalformedURLException;
import java.net.URL;
import com.thoughtworks.gauge.AfterSuite;
import com.thoughtworks.gauge.BeforeSuite;
import com.thoughtworks.gauge.Gauge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Driver {

	// Holds the WebDriver instance
	public static WebDriver webDriver=null;

	// Initialize a webDriver instance of required browser
	// Since this does not have a significance in the application's business domain, the BeforeSuite hook is used to instantiate the webDriver
	@BeforeSuite
	public void init() throws MalformedURLException{
		getDriver();
	}

	public static WebDriver getDriver() throws MalformedURLException {
		return getDriver(false);
	}

	/**
	 * @return the webDriver
	 * @throws MalformedURLException 
	 */
	public static WebDriver getDriver(Boolean getNew) throws MalformedURLException {
		boolean headless = Boolean.parseBoolean(System.getenv("HEADLESS"));
		boolean remote = Boolean.parseBoolean(System.getenv("REMOTE"));
		String browser = System.getenv("BROWSER");
		String browserRemoteUrl = System.getenv("REMOTE_URL");
		long timeout = Long.parseLong(System.getenv("MAX_TIME_LOAD_PAGE_TIMEOUT"));
	
		// Validación básica
		if (browser == null || browser.isEmpty()) {
			throw new IllegalArgumentException("La variable de entorno BROWSER no está configurada.");
		}
	
		// Reinicia el WebDriver si se solicita uno nuevo
		if (webDriver == null || getNew) {
			if (webDriver != null) {
				webDriver.quit();
			}
	
			// Configurar navegador
			switch (browser.toLowerCase()) {
				case "firefox":
					webDriver = configureFirefoxDriver(headless, remote, browserRemoteUrl);
					break;
				case "chrome":
					webDriver = configureChromeDriver(headless, remote, browserRemoteUrl);
					break;
				default:
					throw new UnsupportedOperationException("Navegador no soportado: " + browser);
			}
	
			// Configuración común
			webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
			webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
			webDriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(timeout));
			webDriver.manage().deleteAllCookies();
		}
	
		return webDriver;
	}
	
	// Método para configurar FirefoxDriver
	@SuppressWarnings("deprecation")
	private static WebDriver configureFirefoxDriver(boolean headless, boolean remote, String remoteUrl) throws MalformedURLException {
		FirefoxOptions firefoxOptions = new FirefoxOptions();
		WebDriverManager.firefoxdriver().setup();
	
		if (headless) {
			firefoxOptions.addArguments("--headless", "--window-size=1200,600");
			firefoxOptions.addArguments("--no-sandbox", "--disable-setuid-sandbox");
			firefoxOptions.addArguments("--lang=en_US");
		} else {
			firefoxOptions.addArguments("--start-maximized");
		}
	
		return remote ? new RemoteWebDriver(new URL(remoteUrl), firefoxOptions) : new FirefoxDriver(firefoxOptions);
	}
	
	// Método para configurar ChromeDriver
	@SuppressWarnings("deprecation")
	private static WebDriver configureChromeDriver(boolean headless, boolean remote, String remoteUrl) throws MalformedURLException {
		ChromeOptions chromeOptions = new ChromeOptions();
		WebDriverManager.chromedriver().setup();
	
		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		chromeOptions.addArguments("--remote-allow-origins=*");
	
		if (headless) {
			chromeOptions.addArguments("--headless", "--window-size=1200,600");
			chromeOptions.addArguments("--no-sandbox", "--disable-setuid-sandbox");
			chromeOptions.addArguments("--lang=en_US");
		} else {
			chromeOptions.addArguments("--start-maximized");
		}
	
		if (remote) {
			chromeOptions.addArguments("--incognito", "--disable-dev-shm-usage");
			return new RemoteWebDriver(new URL(remoteUrl), chromeOptions);
		} else {
			return new ChromeDriver(chromeOptions);
		}
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
