package methods;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.asserts.SoftAssert;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.*;

public class StepDefinitions {
    protected static WebDriver driver;
    private WebElement elem;

    private Map<String, String> storeCapturedValue = new HashMap<>();
    private List<WebElement> elementList = new ArrayList<>();
    private String mainWindow = "";
    ReusableMethods predef = new ReusableMethods();

    SoftAssert anAssert = new SoftAssert();

    public StepDefinitions() {
        this.driver = DriverUtil.getDefaultDriver();
    }

    //Step to navigate to url
    @Given("^I navigate to Home page$")
    public void navigate_to() throws FileNotFoundException {
        String url = "";
        String environment = GlobalProperties.getConfigProperties().getProperty("environment");
        String filePath = Constants.ENVIRONMENT;
        Map<String, String> envData = new YAMLReader(filePath).getYmlData();
        url = envData.get(environment);
        driver.get(url);
        predef.waitPageLoad();
    }

    @When("^I navigate to next$")
    public void navigate_next() {
        driver.navigate().forward();
        predef.waitPageLoad();
    }

    @When("^I navigate to previous$")
    public void navigate_prev() {
        driver.navigate().back();
        predef.waitPageLoad();
    }

    @When("^I refresh the page$")
    public void refresh() {
        driver.navigate().refresh();
        predef.waitPageLoad();
    }


    @Then("^I land on (.+) page$")
    public void landOnPage(String page) throws Exception {
        String actualTitle = "";
        String expectedTitle = "";

        expectedTitle = PageObjectGenerator.pageObjects.get(page + ".title_expected");
        actualTitle = driver.getTitle();
        if (!actualTitle.contains(expectedTitle))
            throw new Exception("\nExpected Page title:" + expectedTitle + "\nActual Page title:" + actualTitle + "\n");
    }

    @When("^I click on element (.+)$")
    public void clickElement(String element) throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        elem = predef.findElement(element);
        DriverUtil.waitForJqueryJsToLoad();
        wait.until(ExpectedConditions.elementToBeClickable(elem));
        elem.click();
        DriverUtil.waitForJqueryJsToLoad();
        predef.waitPageLoad();
    }

    @And("^I wait for ajax call to complete$")
    public void waitForAjaxCall()
    {
        while(true)
        {
            if(DriverUtil.waitForJqueryJsToLoad())
                break;
        }
    }
    @When("^I double click on element (.+)$")
    public void doubleClick(String element) {
        try {
            elem = predef.findElement(element);
            Actions actions = new Actions(driver);
            actions.doubleClick(elem).build().perform();
            predef.waitPageLoad();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^I right click on element (.+)$")
    public void rightClick(String element) {
        try {
            elem = predef.findElement(element);
            Actions actions = new Actions(driver);
            actions.contextClick(elem).build().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^I hover on element (.+)$")
    public void hoverElement(String element) {
        try {
            elem = predef.findElement(element);
            Actions actions = new Actions(driver);
            actions.moveToElement(elem).build().perform();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^I scroll to element (.+)$")
    public void scrollToElement(String element) {
        try {
            elem = predef.findElement(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("^I switch to frame by element (.+)$")
    public void swithToFrameByElement(String element) throws Exception {
        elem = predef.findElement(element);
        List<WebElement> frames = driver.findElements(By.xpath("//iframe"));
        driver.switchTo().frame(elem);
    }

    @And("^I switch to frame by name or id (.+)$")
    public void switchToFrameByName(String frame) {
        try {
            driver.switchTo().frame(frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("^I switch to child window (.+)$")
    public void switchToChildWindow(int n) {
        int count = 0;
        int flag = 0;
        mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> windowIterator = windows.iterator();
        windowIterator.next();
        while (windowIterator.hasNext()) {
            String childWindow = windowIterator.next();
            if (!childWindow.equalsIgnoreCase(mainWindow)) {
                count++;
                if (count == n) {
                    driver.switchTo().window(childWindow);
                    flag = 1;
                    break;
                }
            }
        }
        if (flag == 0)
            System.out.println("Child window " + n + " not found");
    }

    @And("^I close current window$")
    public void closeCurrentWindow() {
        try {
            driver.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("^I switch to main window$")
    public void swithToMainWindow() {
        if (mainWindow.equals(""))
            System.out.println("No main window found");
        else
            driver.switchTo().window(mainWindow);
    }

    @And("^I switch to default frame$")
    public void swithToDefaultFrame() {
        driver.switchTo().defaultContent();
    }

    @And("^I switch to tab by name (.+)$")
    public void switchToTabByName(String tabName) {
        boolean tabFound = false;
        String tabTitle = "";
        mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> windowIterator = windows.iterator();
        windowIterator.next();
        while (windowIterator.hasNext()) {
            String childWindow = windowIterator.next();
            driver.switchTo().window(childWindow);
            tabTitle = driver.getTitle();
            if (tabTitle.equals(tabName)) {
                tabFound = true;
                break;
            }
        }
        if (!tabFound) {
            System.out.println(tabName + " tab not found");
            swithToMainWindow();
        }
    }

    @When("^I click element (.+) if displayed")
    public void clickIfDisplayed(String element) {
        try {
            DriverUtil.waitForJqueryJsToLoad();
            elem = predef.findElement(element);
            if (elem.isEnabled())
                elem.click();
        } catch (Exception e) {
        }
    }

    @When("^I force click on element (.+)$")
    public void forceClick(String element) {
        try {
            elem = predef.findElement(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click;", elem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("^I validate text of (.+) element is (.+)$")
    public void validateText(String element, String expectedText) throws Exception {
        String actualText = "";
        elem = predef.findElement(element);
        actualText = elem.getText();
        if (!actualText.equals(expectedText))
            throw new Exception("\nExpected Text:" + expectedText + "\nActual Text:" + actualText);
    }

    @Then("^I validate element (.+)$")
    public void validateElement(String element) throws Exception {
        elem = predef.findElement(element);
        if (!elem.isEnabled())
            throw new Exception("Element not found");
    }

    @Then("^I validate attribute (.+) of element (.+) is (.+)")
    public void validateElementAttr(String attribute, String element, String expectedVal) throws Exception {
        String actualVal = "";
        elem = predef.findElement(element);
        actualVal = elem.getAttribute(attribute);
        if (!actualVal.equals(expectedVal))
            throw new Exception("\nExpected value:" + expectedVal + "\nActual value:" + actualVal);
    }

    @Then("^I validate (.+) element contains text (.+)$")
    public void elemContainsText(String element, String partialText) throws Exception {
        String actualText = "";
        elem = predef.findElement(element);
        actualText = elem.getText();
        if (!actualText.contains(partialText))
            throw new Exception("\nExpected text cotains:" + partialText + "\nActual text:" + actualText);
    }

    @When("^I click item (.+) in the (.+) element list$")
    public void clickNItem(int n, String element) throws Exception {
        elementList = predef.findElements(element);
        if (n < 1)
            throw new Exception("Invalid element number");
        else if (n <= elementList.size()) {
            n--;
            elem = elementList.get(n);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click", elem);
        } else
            throw new Exception("Element list has " + elementList.size() + " items, but driver tried to click " + n + 1 + " item");
    }

    @And("^I wait for (.+) secs$")
    public void waitForSec(int t) throws InterruptedException {
        t = t * 1000;
        Thread.sleep(t);
    }

    @When("^I select (.*?) option by (index|value|text) from dropdown (.+)$")
    public void selectDropdownValue(String option, String optionBy, String element) throws Exception {
        elem = predef.findElement(element);
        if (elem.isEnabled()) {
            Select dropdown = new Select(elem);
            if (optionBy.equals("index"))
                dropdown.selectByIndex(Integer.parseInt(option));
            else if (optionBy.equals("value"))
                dropdown.selectByValue(option);
            else if (optionBy.equals("text"))
                dropdown.selectByVisibleText(option);
        } else
            System.out.println("Unable to select from dropdown " + element);
    }

    @When("^I select option by text contains (.+) from dropdown (.+)$")
    public void selectDropDownReact(String option, String element) throws Exception {
        elem = predef.findElement(element);
        scrollToElement(element);
        if (elem.isEnabled()) {
            Actions actions = new Actions(driver);
            try {
                actions.moveToElement(elem).click().build().perform();
                Thread.sleep(1000);
                elem = driver.findElement(By.xpath("//*[contains(text(),'" + option + "')]"));
                actions.moveToElement(elem).click().build().perform();
            } catch (Exception e) {
                throw new Exception(option + " option not found");
            }
        } else {
            throw new Exception("Option can not be selected from dropdown " + element);
        }
    }

    @When("^I drag (.+) element to (.+)$")
    public void dragAndDropElement(String element, String dest) throws Exception {
        elem = predef.findElement(element);
        WebElement to = predef.findElement(dest);
        Actions actions = new Actions(driver);
        actions.dragAndDrop(elem, to).perform();
    }

    @When("^I enter text (.+) to element (.+)$")
    public void enterText(String inputString, String element) throws Exception {
        elem = predef.findElement(element);
        if (elem.isEnabled()) {
            scrollToElement(element);
            elem.sendKeys(inputString);
            DriverUtil.waitForJqueryJsToLoad();
        } else
            throw new Exception("Element " + element + " is not enabled");
    }

    @And("^I capture text from (.+) element and store it as (.+)^")
    public void captureTextAndStore(String element, String key) throws Exception {
        if (elem.isEnabled()) {
            elem = predef.findElement(element);
            String textVal = elem.getText();
            storeCapturedValue.put(key, textVal);
        } else
            throw new Exception("Element " + element + " is not enabled");
    }

    @When("^I enter (.+) data from (.+) file$")
    public void enterDataIntoFields(String dataKey, String pageName) throws Exception {
        Map<String, String> inputValues = predef.fetchData(dataKey, pageName);
        for (Map.Entry<String, String> entry : inputValues.entrySet()) {
            String key = pageName + "." + entry.getKey();
            String value = entry.getValue();
            if (!key.contains("PressKey")) {
                scrollToElement(key);
                elem = predef.findElement(key);
                if (value.equalsIgnoreCase("click")) {
                    Actions actions = new Actions(driver);
                    actions.moveToElement(elem).click().build().perform();
                } else if (value.contains("Radio:")) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].checked=true", elem);
                } else if (value.contains("Date:")) {
                    value = value.replace("Date:", "");
                    String[] dateValue = value.split(" ");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(elem).click().perform();
                    actions.sendKeys(Keys.ENTER);
                    elem = predef.findElement(pageName + ".month_picker");
                    Select select = new Select(elem);
                    select.selectByVisibleText(dateValue[1]);
                    elem = predef.findElement(pageName + ".year_picker");
                    select = new Select(elem);
                    select.selectByVisibleText(dateValue[2]);
                    WebElement dateElem = driver.findElement(By.xpath("//div[text()='" + dateValue[0] + "']"));
                    actions.moveToElement(dateElem).click().perform();
                } else if (value.contains("::By")) {
                    if (value.contains("::ByText")) {
                        value = value.replace("::ByText::", "");
                        selectDropdownValue(value, "text", key);
                    } else if (value.contains("::ByIndex")) {
                        value = value.replace("::ByIndex::", "");
                        selectDropdownValue(value, "index", key);
                    } else if (value.contains("::ByValue")) {
                        value = value.replace("::ByValue::", "");
                        selectDropdownValue(value, "value", key);
                    }
                } else if (value.contains("ForceText:")) {
                    value = value.replace("ForceText:", "");
                    Actions actions = new Actions(driver);
                    actions.moveToElement(elem).click().sendKeys(value).perform();
                } else if (value.contains("ForceSelect:")) {
                    value = value.replace("ForceSelect:", "");
                    selectDropDownReact(value, key);
                } else {
                    elem.clear();
                    elem.sendKeys(value);
                }
                Thread.sleep(1000);
            } else {
                pressKeyboardKey(value);
            }
        }
    }

    @When("^I select (.+) radio button value$")
    public void selectRadio(String element) throws Exception {
        elem = predef.findElement(element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked=true;", elem);
    }

    @When("^I select (.+) date from (.+) datepicker in (.+) page$")
    public void selectFromDatePicker(String date, String element, String pageName) throws Exception {
        elem = predef.findElement(element);
        String[] dateValue = date.split(" ");
        Actions actions = new Actions(driver);
        actions.moveToElement(elem).click().perform();
        actions.sendKeys(Keys.ENTER);
        elem = predef.findElement(pageName + ".month_picker");
        Select select = new Select(elem);
        select.selectByVisibleText(dateValue[1]);
        elem = predef.findElement(pageName + ".year_picker");
        select = new Select(elem);
        select.selectByVisibleText(dateValue[2]);
        WebElement dateElem = driver.findElement(By.xpath("//div[text()='" + dateValue[0] + "']"));
        actions.moveToElement(dateElem).click().perform();
    }

    @When("^I press (.+) key in keyboard$")
    public void pressKeyboardKey(String key) throws Exception {
        key = key.toUpperCase();
        Actions actions = new Actions(driver);
        switch (key) {
            case "ENTER": {
                actions.sendKeys(Keys.ENTER).build().perform();
                break;
            }
            case "ADD": {
                actions.sendKeys(Keys.ADD).build().perform();
                break;
            }
            case "ALT": {
                actions.sendKeys(Keys.ALT).build().perform();
                break;
            }
            case "ARROW_DOWN": {
                actions.sendKeys(Keys.ARROW_DOWN).build().perform();
                break;
            }
            case "ARROW_UP": {
                actions.sendKeys(Keys.ARROW_UP).build().perform();
                break;
            }
            case "ARROW_LEFT": {
                actions.sendKeys(Keys.ARROW_LEFT).build().perform();
                break;
            }
            case "BACK_SPACE": {
                actions.sendKeys(Keys.BACK_SPACE).build().perform();
                break;
            }
            case "CLEAR": {
                actions.sendKeys(Keys.CLEAR).build().perform();
                break;
            }
            case "COMMAND": {
                actions.sendKeys(Keys.COMMAND).build().perform();
                break;
            }
            case "CONTROL": {
                actions.sendKeys(Keys.CONTROL).build().perform();
                break;
            }
            case "DECIMAL": {
                actions.sendKeys(Keys.DECIMAL).build().perform();
                break;
            }
            case "DELETE": {
                actions.sendKeys(Keys.DELETE).build().perform();
                break;
            }
            case "DIVIDE": {
                actions.sendKeys(Keys.DIVIDE).build().perform();
                break;
            }
            case "DOWN": {
                actions.sendKeys(Keys.DOWN).build().perform();
                break;
            }
            case "END": {
                actions.sendKeys(Keys.END).build().perform();
                break;
            }
            case "EQUALS": {
                actions.sendKeys(Keys.EQUALS).build().perform();
                break;
            }
            case "ESCAPE": {
                actions.sendKeys(Keys.ESCAPE).build().perform();
                break;
            }
            case "F1": {
                actions.sendKeys(Keys.F1).build().perform();
                break;
            }
            case "F2": {
                actions.sendKeys(Keys.F2).build().perform();
                break;
            }
            case "F3": {
                actions.sendKeys(Keys.F3).build().perform();
                break;
            }
            case "F4": {
                actions.sendKeys(Keys.F4).build().perform();
                break;
            }
            case "F5": {
                actions.sendKeys(Keys.F5).build().perform();
                break;
            }
            case "F6": {
                actions.sendKeys(Keys.F6).build().perform();
                break;
            }
            case "F7": {
                actions.sendKeys(Keys.F7).build().perform();
                break;
            }
            case "F8": {
                actions.sendKeys(Keys.F8).build().perform();
                break;
            }
            case "F9": {
                actions.sendKeys(Keys.F9).build().perform();
                break;
            }
            case "F10": {
                actions.sendKeys(Keys.F10).build().perform();
                break;
            }
            case "F11": {
                actions.sendKeys(Keys.F11).build().perform();
                break;
            }
            case "F12": {
                actions.sendKeys(Keys.F11).build().perform();
                break;
            }
            case "HELP": {
                actions.sendKeys(Keys.HELP).build().perform();
                break;
            }
            case "HOME": {
                actions.sendKeys(Keys.HOME).build().perform();
                break;
            }
            case "INSERT": {
                actions.sendKeys(Keys.INSERT).build().perform();
                break;
            }
            case "LEFT": {
                actions.sendKeys(Keys.LEFT).build().perform();
                break;
            }
            case "LEFT_CONTROL": {
                actions.sendKeys(Keys.LEFT_CONTROL).build().perform();
                break;
            }
            case "LEFT_ALT": {
                actions.sendKeys(Keys.LEFT_ALT).build().perform();
                break;
            }
            case "LEFT_SHIFT": {
                actions.sendKeys(Keys.LEFT_SHIFT).build().perform();
                break;
            }
            case "META": {
                actions.sendKeys(Keys.META).build().perform();
                break;
            }
            case "MULTIPLY": {
                actions.sendKeys(Keys.MULTIPLY).build().perform();
                break;
            }
            case "NULL": {
                actions.sendKeys(Keys.NULL).build().perform();
                break;
            }
            case "NUMPAD0": {
                actions.sendKeys(Keys.NUMPAD0).build().perform();
                break;
            }
            case "NUMPAD1": {
                actions.sendKeys(Keys.NUMPAD1).build().perform();
                break;
            }
            case "NUMPAD2": {
                actions.sendKeys(Keys.NUMPAD2).build().perform();
                break;
            }
            case "NUMPAD3": {
                actions.sendKeys(Keys.NUMPAD3).build().perform();
                break;
            }
            case "NUMPAD4": {
                actions.sendKeys(Keys.NUMPAD4).build().perform();
                break;
            }
            case "NUMPAD5": {
                actions.sendKeys(Keys.NUMPAD5).build().perform();
                break;
            }
            case "NUMPAD6": {
                actions.sendKeys(Keys.NUMPAD6).build().perform();
                break;
            }
            case "NUMPAD7": {
                actions.sendKeys(Keys.NUMPAD7).build().perform();
                break;
            }
            case "NUMPAD8": {
                actions.sendKeys(Keys.NUMPAD8).build().perform();
                break;
            }
            case "NUMPAD9": {
                actions.sendKeys(Keys.NUMPAD9).build().perform();
                break;
            }
            case "PAGE_DOWN": {
                actions.sendKeys(Keys.PAGE_DOWN).build().perform();
                break;
            }
            case "PAGE_UP": {
                actions.sendKeys(Keys.PAGE_UP).build().perform();
                break;
            }
            case "PAUSE": {
                actions.sendKeys(Keys.PAUSE).build().perform();
                break;
            }
            case "RETURN": {
                actions.sendKeys(Keys.RETURN).build().perform();
                break;
            }
            case "RIGHT": {
                actions.sendKeys(Keys.RIGHT).build().perform();
                break;
            }
            case "SEMICOLON": {
                actions.sendKeys(Keys.SEMICOLON).build().perform();
                break;
            }
            case "SEPARATOR": {
                actions.sendKeys(Keys.SEPARATOR).build().perform();
                break;
            }
            case "SHIFT": {
                actions.sendKeys(Keys.SHIFT).build().perform();
                break;
            }
            case "SPACE": {
                actions.sendKeys(Keys.SPACE).build().perform();
                break;
            }
            case "SUBTRACT": {
                actions.sendKeys(Keys.SUBTRACT).build().perform();
                break;
            }
            case "TAB": {
                actions.sendKeys(Keys.TAB).build().perform();
                break;
            }
            case "UP": {
                actions.sendKeys(Keys.UP).build().perform();
                break;
            }
            default: {
                throw new Exception("\nInvalid key " + key + " tried to press\n");
            }
        }
    }

    @Then("^I validate height of (.+) element is (.+)$")
    public void validateHeight(int expectedHeight, String element) throws Exception {
        elem = predef.findElement(element);
        int actualHeight = elem.getRect().height;
        if (!(actualHeight == expectedHeight)) {
            throw new Exception("\nExpected Height:" + expectedHeight + "\nActual Height:" + actualHeight + "\n");
        }
    }

    @Then("^I validate width of (.+) element is (.+)$")
    public void validateWidth(int expectedWidth, String element) throws Exception {
        elem = predef.findElement(element);
        int actualWidth = elem.getRect().width;
        if (!(actualWidth == expectedWidth)) {
            throw new Exception("\nExpected Width:" + expectedWidth + "\nActual Width:" + actualWidth + "\n");
        }
    }

}
