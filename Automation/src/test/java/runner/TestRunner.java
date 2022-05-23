package runner;


import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import methods.DriverUtil;
import methods.InputDataGenerator;
import methods.PageObjectGenerator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;

@CucumberOptions(tags = "@test",
        features = {"src/test/resources/features"},
        glue = {"methods"},
        plugin = {"pretty","html:target/cucumberHtmlReport.html"}
)

@Test
public class TestRunner extends AbstractTestNGCucumberTests {

    @BeforeClass
    public static void setup() throws FileNotFoundException {
        PageObjectGenerator.getPageObject();
        InputDataGenerator.getInputData();
    }

    @AfterClass
    public static void destroy()
    {
        DriverUtil.closeDriver();
    }

}
