package runner;


import io.cucumber.core.gherkin.Step;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import methods.Constants;
import methods.DriverUtil;
import methods.InputDataGenerator;
import methods.PageObjectGenerator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static methods.Constants.SCREENSHOTPATH;

@CucumberOptions(tags = "@test",
        features = {"src/test/resources/features"},
        glue = {"methods"},
        plugin = {"pretty", "html:target/cucumberHtmlReport.html"}
)

@Test
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void setup() throws FileNotFoundException {
        PageObjectGenerator.getPageObject();
        InputDataGenerator.getInputData();
    }

    @AfterClass
    public static void destroy() {
        DriverUtil.closeDriver();
    }

    @After
    public void TakeScreenshot(Scenario scenario) throws IOException {
        // Take a screenshot...
        WebDriver driver = DriverUtil.getDefaultDriver();
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = SCREENSHOTPATH + "/timeStamp.png";
        FileUtils.copyFile(file, new File(fileName));
        final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        // embed it in the report.
//        scenario.embed(screenshot);
        scenario.attach(screenshot, "image/png", fileName);
    }
}
