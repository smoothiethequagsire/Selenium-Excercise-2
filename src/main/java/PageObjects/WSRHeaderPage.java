package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WSRHeaderPage {

	WebDriver driver;

	public WSRHeaderPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//span[text()='Headers']")
	WebElement headerTab;
	@FindBy(xpath = "//a[@title='New']/div")
	WebElement hNewButton;	
	@FindBy(xpath="//button[text()='Save']")
	WebElement hSaveButton;
	@FindBy(xpath="//span[text()='success']")
	WebElement creationMessage;
	@FindBy(xpath="//tbody/tr[1]")
	WebElement headerCreated;
	@FindBy(xpath="//div[@data-key='success']")
	WebElement successMessage;
	

	public WebElement HeaderTab() {
		return headerTab;
	}

		public WebElement HNewButton() {
		return hNewButton;
	}
	
	public WebElement HSaveButton() {
		return hSaveButton;
	}
		
	public WebElement CreationMessage() {
		return creationMessage;
	}
		
	public WebElement HeaderCreated() {
		return headerCreated;
	}
	
	public WebElement SuccessMessage() {
		return successMessage;
	}
	
}