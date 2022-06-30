package methods;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Collections;

import static methods.Constants.*;
import static methods.GlobalProperties.getConfigProperties;

public class DriverUtil {
    private static WebDriver driver;

    private static GlobalProperties configProperties = getConfigProperties();

    public static WebDriver getDefaultDriver() {
        if (driver != null && ((RemoteWebDriver) driver).getSessionId() != null)
            return driver;
        driver = chooseDriver();
        return driver;
    }

    private static WebDriver chooseDriver() {
        String browser = configProperties.getProperty(BROWSER);
        RemoteWebDriver driver = null;
        System.out.println("========== Before Driver Created ========");
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                if (configProperties.getProperty(HEADLESS).equalsIgnoreCase("true")) {
////                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--headless");
//                    chromeOptions.addArguments("--no-sandbox");
//                    chromeOptions.addArguments("disable-gpu");
//                    chromeOptions.addArguments("window-size=1920X1080");
                }
//                chromeOptions.addExtensions(new File(EXTENSION_PATH));
                chromeOptions.addArguments("start-maximized");
                chromeOptions.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
//                chromeOptions.setExperimentalOption("useAutomationExtension", false);
                chromeOptions.setExperimentalOption("excludeSwitches",
                        Collections.singletonList("enable-automation"));
                System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
                driver = new ChromeDriver(chromeOptions);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                try {
                    driver.switchTo().window("data:,");
                } catch (Exception e) {
                }
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
//                if (configProperties.getProperty(HEADLESS).equalsIgnoreCase("true")) {
//                    edgeOptions.addArguments("--headless");
//                    edgeOptions.addArguments("window-size=1920X1080");
//                }
                edgeOptions.addArguments("start-maximized");
//                edgeOptions.addExtensions(new File(EXTENSION_PATH));
                edgeOptions.setExperimentalOption("useAutomationExtension", false);
                edgeOptions.setExperimentalOption("excludeSwitches",
                        Collections.singletonList("enable-automation"));
                System.setProperty("webdriver.edge.driver", EDGEDRIVER_PATH);
                driver = new EdgeDriver(edgeOptions);
                try {
                    driver.switchTo().window("data:,");
                } catch (Exception e) {
                }
                break;
        }
        System.out.println("########## After driver created ########");
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static boolean waitForJqueryJsToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        ExpectedCondition<Boolean> jqueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    return ((boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0;"));
                } catch (Exception e) {
                    return true;
                } finally {

                }
            }
        };
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                try {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                } catch (Exception e) {
                    return true;
                } finally {

                }
            }
        };
        return wait.until(jqueryLoad) && wait.until(jsLoad);
    }

}
