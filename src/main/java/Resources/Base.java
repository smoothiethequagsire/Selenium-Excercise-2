package Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageObjects.LoginPage;
import PageObjects.WSRBodyPage;
import PageObjects.WSRHeaderPage;

public class Base {
	public static JavascriptExecutor js;
	public static WebDriver driver;
	public static WebDriverWait w;
	public static ChromeOptions opt;
	public static Properties prop;
	public static String devSpaceId;
	public static WSRHeaderPage wsrhp;
	public static WSRBodyPage wsrbp;
	public static Utility ut;
	public static String testsDataFile = "src/main/java/Resources/Test cases Data.xlsx";

	// Set driver options and initialize it for each class
	public WebDriver InitializeDriver() throws IOException {

		opt = new ChromeOptions();
		opt.addArguments("--disable-notifications");
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/java/resources/data.properties");
		prop.load(fis);
		String browserName = prop.getProperty("browser");

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\Leandro\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver(opt);
		} else {
			System.out.println("Unable to find correct browser.");
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		w = new WebDriverWait(driver, 20);
		js = (JavascriptExecutor) driver;
		
		return driver;
	}

	// Login to page using xlsx data
	public void LogIn() throws IOException {
		LoginPage lp = new LoginPage(driver);
		String username= ut.GetCellData(testsDataFile, 0, 1, 0);
		String password= ut.GetCellData(testsDataFile, 0, 1, 1);
		lp.Username().sendKeys(username);
		lp.Password().sendKeys(password);
		lp.LoginBttn().click();

		w.until(ExpectedConditions.urlContains("dev-ed"));

	}

	// Gets the individual developer id after first Login to later create the
	// dynamic urls for each section
	public void SetEnvironmentUrl() throws IOException {
		String Url = driver.getCurrentUrl();
		String[] UrlParts = Url.split("-", 2);
		devSpaceId = UrlParts[0];
	}

	// Combines the previously set developer Id and the rest of the url (saved in
	// data.properties) for each section
	public String GetDynamicUrl(String propUrlKey) {
		return devSpaceId + prop.getProperty(propUrlKey);
	}

	// Creates basic header completing every required field
	public void CreateHeader(String filePath, int row) throws IOException {

		ut.getInputByLabelName("Name", "input").sendKeys(ut.GetCellData(filePath, 1, row, 1));
		ut.getInputByLabelName("Email", "input").sendKeys(ut.GetCellData(filePath, 1, row, 2));
		ut.getInputByLabelName("Vision", "textarea").sendKeys(ut.GetCellData(filePath, 1, row, 3));
		ut.getInputByLabelName("Measures", "textarea").sendKeys(ut.GetCellData(filePath, 1, row, 4));
		ut.getInputByLabelName("Methods", "textarea").sendKeys(ut.GetCellData(filePath, 1, row, 5));
		ut.getInputByLabelName("Obstacles", "textarea").sendKeys(ut.GetCellData(filePath, 1, row, 6));
	}

	// Creates basic body record completing every required field
	public void CreateBody(String filePath, int row) throws IOException {
		// Search for user and header options and clicks on them
		String[] lookUpFields = { "Header", "User" };
		for (String field : lookUpFields) {
			ut.getInputByLabelName(field, "input").click();
			w.until(ExpectedConditions.visibilityOf(wsrbp.LookUpOption()));
			wsrbp.LookUpOption().click();
		}

		// fills required data from xslx file
		ut.getInputByLabelName("Name", "input").sendKeys(ut.GetCellData(filePath, 1, row, 1));
		String sprintDeliverables = ut.GetCellData(filePath, 1, row, 2);
		ut.getInputByLabelName("Deliverables", "input").click();
		ut.getDropDwOption(sprintDeliverables).click();
		ut.getInputByLabelName("Start Date", "input").sendKeys(ut.GetCellData(filePath, 1, row, 3));
		ut.getInputByLabelName("End Date", "input").sendKeys(ut.GetCellData(filePath, 1, row, 4));
		ut.getInputByLabelName("Stories Assigned", "input").sendKeys(ut.GetCellData(filePath, 1, row, 5));
		ut.getInputByLabelName("Stories Completed", "input").sendKeys(ut.GetCellData(filePath, 1, row, 6));
		ut.getInputByLabelName("In Progress", "input").sendKeys(ut.GetCellData(filePath, 1, row, 7));
		ut.getInputByLabelName("Not Started", "input").sendKeys(ut.GetCellData(filePath, 1, row, 8));

		// Fills each day field using xslx file data
		String[] DaysFields = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
		int counter = 9;
		for (String field : DaysFields) {
			js.executeScript("arguments[0].scrollIntoView();", ut.getDayFieldToClick(field));
			String dataToSend = ut.GetCellData(filePath, 1, row, counter);
			ut.getDayFieldToClick(field).click();
			ut.getDayFieldToSendData(field).sendKeys(dataToSend);
			counter++;
		}
	}

	// Change specific field value to test different values
	public void ChangeFieldData (WebElement field, String data) {
		field.clear();
		field.sendKeys(data);
	}
	
}