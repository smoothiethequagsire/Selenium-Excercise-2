package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
	WebDriver driver;
	
	public HomePage (WebDriver driver) {
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath= "//div[contains (@class,'appLauncher')]/button")
	WebElement appLauncher;
	@FindBy(xpath= "//input[contains(@placeholder,'Search apps')]")
	WebElement appLauncherSearch;
	@FindBy(xpath= "//span/p[contains(text(),'builder')]")
	WebElement wsrBuilderOption;
	
	
	public WebElement AppLauncher() {
		return appLauncher;
	}
	
	public WebElement AppLauncherSearch() {
		return appLauncherSearch;
	}
	
	public WebElement WSRBuilderOpt() {
		return wsrBuilderOption;
	}
}
