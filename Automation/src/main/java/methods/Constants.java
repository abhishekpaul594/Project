package methods;

/* It is to store all constant values in the project */
public class Constants {
    public static final String CONFIGPATH = "src/main/resources/cucumber.properties";
    public static final String BROWSER = "browser";
    public static final String HEADLESS = "headless";

    public static final String SCREENSHOTPATH="src/sreenshots";
    public static final String ENVIRONMENT="src/test/resources/inputdata/environments.yaml";
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    public static final String DRIVERPATH = System.getProperty("user.dir") + FILE_SEPARATOR + "webdrivers";
    public static final String CHROMEDRIVER_PATH = DRIVERPATH + FILE_SEPARATOR + "chromedriver.exe";
    public static final String EDGEDRIVER_PATH =DRIVERPATH+FILE_SEPARATOR+"msedgedriver.exe";
    public static final String PHANTOMJSDRIVER_PATH =DRIVERPATH+FILE_SEPARATOR+"phantomjs.exe";

    public static final String PAGEOBJECT_PATH="src/test/resources/pageobject";

    public static final String INPUTDATA_PATH="src/test/resources/inputdata";

    public static final String EXTENSION_PATH="src/main/resources/Extensions/adb.crx";
}
