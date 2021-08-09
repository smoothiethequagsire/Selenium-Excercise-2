package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WSRBodyPage {
	
	WebDriver driver;
	
	public WSRBodyPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//a[@title='New']/div")
	WebElement bNewButton;
	@FindBy(xpath="//button[text()='Save']")
	WebElement bSaveButton;	
	@FindBy(xpath="//button[text()='Cancel']")
	WebElement cancelButton;
	@FindBy(xpath="//li/descendant::span/span/span")
	WebElement headerOption;
	@FindBy(xpath="//div[@data-key='success']")
	WebElement successMessage;
	@FindBy(xpath="//h2[@title='We hit a snag.']")
	WebElement errorMessage;
	@FindBy(xpath="//button[text()='Submit to Manager']")
	WebElement submitButton;
	@FindBy(xpath="//footer/button/span[contains(text(), 'Save')]")
	WebElement submitSaveButton;
	@FindBy(xpath="//tr[1]/th/span/a")
	WebElement bodyRecordLink;
	@FindBy(xpath="//span[text()='Status']/parent::div/following-sibling::div/descendant::*[text()='Submitted']")
	WebElement submittedStatus;
	
	public WebElement BNewButton() {
		return bNewButton;
	}
	
	public WebElement BSaveButton() {
		return bSaveButton;
	}
	
	public WebElement CancelButton() {
		return cancelButton;
	}
	
	public WebElement LookUpOption() {
		return headerOption;
	}
	
	public WebElement SuccessMessage() {
		return successMessage;
	}
	
	public WebElement errorMessage() {
		return errorMessage;
	}
	
	public WebElement submitButton() {
		return submitButton;
	}
	
	public WebElement submitSaveButton() {
		return submitSaveButton;
	}
	
	public WebElement bodyRecordLink() {
		return bodyRecordLink;
	}
	
	public WebElement submittedStatus() {
		return submittedStatus;
	}
}
