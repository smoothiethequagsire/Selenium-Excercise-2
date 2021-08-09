package Resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utility {

	WebDriver driver;

	public Utility(WebDriver driver) {
		this.driver = driver;
	}

	// Strings to use as dynamic xpaths to get the specified field after specified
	// label
	public static final String labelXpath = "//label[contains(text(),'$labelName')]";
	public static final String dataFieldXpath = "/following-sibling::div/descendant::$fieldType";
	// String to use with labelXPath as xpath to get the error message for the field of specified label
	public static final String errorMsgXpath = "/following-sibling::div[2]";
	// String to use as xpath to get the dropdown option with specified text
	public static final String drDwnOptXpath = "//span[contains(@title, '$value')]";
	// String to use as xpath to get the lookUp option
		public static final String lookUpOptXpath = "//*[@title='$LookUpOption']";
	// String to use as xpath to get the field of specified day (needs to be clicked
	// to create new webelement wich will receive data)
	public static final String dayFieldToClickXpath = "//span[text()='$day']/parent::span/following-sibling::div/div/div[3]";
	// String to use as xpath to send day data to specified day field
	public static final String dayFieldToSendKeysXPath = "//span[text()='$day']/parent::span/following-sibling::div/div/div[2]/div/p";
	// String to use with labelXPath as xpath to get the error message for the sprint Start and End date fields
	public static final String errorMsgForDateXpath = "/parent::div/following-sibling::div";
	
	public void JSClick(WebElement element) {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
	}

	// Extracts cell data from passed xlsx file
	public String GetCellData(String FilePath, int sheetIndx, int rowIndx, int cellIndx) throws IOException {

		FileInputStream file = new FileInputStream(FilePath);
		XSSFWorkbook wkb = new XSSFWorkbook(file);
		XSSFSheet sheet = wkb.getSheetAt(sheetIndx);

		Iterator<Row> rows = sheet.iterator();
		Row wantedRow = rows.next();
		int i = 0;
		while (i < rowIndx) {
			wantedRow = rows.next();
			i++;
		}

		Iterator<Cell> cells = wantedRow.cellIterator();
		Cell wantedCell = cells.next();
		int j = 0;
		while (j < cellIndx) {
			wantedCell = cells.next();
			j++;
		}

		CellType dataType = wantedCell.getCellType();
		String data = "";
		if (dataType == CellType.STRING) {
			data = wantedCell.getStringCellValue();
		}
		if (dataType == CellType.NUMERIC) {
			data = NumberToTextConverter.toText(wantedCell.getNumericCellValue());
		}

		wkb.close();
		return data;
	}

	// Method that retrieves the element after the specified label (works for many
	// elements in Header and Body)
	public WebElement getInputByLabelName(String labelName, String tag) {
		WebElement neededElement = driver.findElement(
				By.xpath(labelXpath.replace("$labelName", labelName) + dataFieldXpath.replace("$fieldType", tag)));
		return neededElement;
	}

	// Returns webElement from user dropdown with passed text
	public WebElement getUserOption(String optionOnDropDw) {
		WebElement neededOpt = driver.findElement(By.xpath(lookUpOptXpath.replace("$LookUpOption", optionOnDropDw)));
		return neededOpt;
	}

	// Returns webElement that holds error message displayed after the specified
	// field label
	public WebElement getMessageByLabelName(String labelName) {
		WebElement neededElement = driver
				.findElement(By.xpath(labelXpath.replace("$labelName", labelName) + errorMsgXpath));
		return neededElement;
	}

	// returns dropdown option with passed value
	public WebElement getDropDwOption(String value) {
		WebElement neededElement = driver.findElement(By.xpath(drDwnOptXpath.replace("$value", value)));
		return neededElement;
	}

	// returns day field to click before sending keys
	public WebElement getDayFieldToClick(String day) {
		WebElement neededElement = driver.findElement(By.xpath(dayFieldToClickXpath.replace("$day", day)));
		return neededElement;
	}
	
	// returns day field to send keys to
	public WebElement getDayFieldToSendData(String day) {
		WebElement neededElement = driver.findElement(By.xpath(dayFieldToSendKeysXPath.replace("$day", day)));
		return neededElement;
		}
	
	// returns day field to send keys to
	public WebElement getSprintDateErrorMsg(String label) {
		WebElement neededElement = driver.findElement(By.xpath(labelXpath.replace("$labelName", label)+ errorMsgForDateXpath));
		return neededElement;
		}
}
