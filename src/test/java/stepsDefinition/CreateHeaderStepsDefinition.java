package stepsDefinition;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import PageObjects.HomePage;
import PageObjects.WSRBodyPage;
import PageObjects.WSRHeaderPage;
import Resources.Base;
import Resources.Utility;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateHeaderStepsDefinition extends Base {

	HomePage hp;

	@Before
	public void StartUp() throws IOException {
		InitializeDriver();
		ut = new Utility(driver);
		wsrhp = new WSRHeaderPage(driver);
		wsrbp = new WSRBodyPage(driver);
	}

	// SCENARIO1- US1TC01
	@Given("^User logs into Salesforce page$")
	public void user_logs_into_salesforce_page() throws IOException {
		driver.get(prop.getProperty("loginUrl"));
		LogIn();

		// Get the personal developer environment id to build dynamic urls later
		SetEnvironmentUrl();
	}

	@And("^clicks the applauncher and search for WSR$")
	public void clicks_the_applauncher_and_search_for_wsr() {
		hp = new HomePage(driver);
		w.until(ExpectedConditions.visibilityOf(hp.AppLauncher()));
		hp.AppLauncher().click();
		w.until(ExpectedConditions.visibilityOf(hp.AppLauncherSearch()));
		hp.AppLauncherSearch().sendKeys("WSR");
	}

	@And("^clicks on WSR builder option$")
	public void clicks_on_wsr_builder_option() {
		hp.WSRBuilderOpt().click();
	}

	@When("^user clicks Header tab$")
	public void user_clicks_header_tab() {
		w.until(ExpectedConditions.urlContains("home"));
		ut.JSClick(wsrhp.HeaderTab());
	}

	@And("^clicks New button$")
	public void clicks_new_button() {
		w.until(ExpectedConditions.visibilityOf(wsrhp.HNewButton()));
		wsrhp.HNewButton().click();
	}

	@And("^completes the required fields using provided data and clicks on Save button$")
	public void completes_the_required_fields_using_provided_data_and_clicks_on_save_button() throws IOException {
		w.until(ExpectedConditions.visibilityOf(ut.getInputByLabelName("Name", "input")));
		CreateHeader("src/main/java/Resources/Test cases Data.xlsx", 1);
		wsrhp.HSaveButton().click();
	}

	@Then("^Header is created and user redirected to its details page$")
	public void header_is_created_and_user_redirected_to_its_details_page() {
		Assert.assertTrue(wsrhp.CreationMessage().isDisplayed(), "Successful creation message is not displayed");
		Assert.assertTrue(driver.getCurrentUrl().contains("view"), "User is not redirected to new header details page");
	}

	// SCENARIO2- US1TC02
	@Given("^user is logged into Salesforce and on header tab$")
	public void user_is_logged_into_salesforce_and_on_header_tab() throws IOException {
		driver.get(devSpaceId + prop.getProperty("wsrHeaderUrl"));
		LogIn();
		w.until(ExpectedConditions.urlContains("Recent"));
	}

	@And("^clicks Save button$")
	public void clicks_save_button() {
		w.until(ExpectedConditions.visibilityOf(wsrhp.HSaveButton()));
		wsrhp.HSaveButton().click();
	}

	@Then("^error messages are displayed$")
	public void error_messages_are_displayed() {

		// Check whether error message for the required fields are displayed or not
		String[] requiredFields = { "Header Name", "Manager Email", "Vision & Values", "Measures", "Methods",
				"Obstacles" };
		SoftAssert softAs = new SoftAssert();
		for (String label : requiredFields) {
			try {
				WebElement errorMessage = ut.getMessageByLabelName(label);
				softAs.assertTrue(errorMessage.isDisplayed());
			} catch (Exception e) {
				softAs.assertTrue(false, "No error message found for the following empty field: " + label);
			} finally {
				softAs.assertAll();
			}
		}
	}

	// SCENARIO3- US01TC03

	@And("^completes the required fields with invalid email format$")
	public void completes_the_required_fields_with_invalid_email_format() throws IOException {
		// using first invalid format (test.test)
		CreateHeader(testsDataFile, 2);
		wsrhp.HSaveButton().click();
		try {
			Assert.assertTrue(ut.getMessageByLabelName("Email").isDisplayed(),
					"Message under Manager Email field is not displayed.");
		} catch (Exception e) {
			Assert.assertTrue(false, "Message under Manager Email field is not displayed.");
		}

		// using second invalid format (test@test)
		
		String invalidMail2 = ut.GetCellData(testsDataFile, 1, 3, 2);
		
		ut.getInputByLabelName("Email", "input").clear();
		ut.getInputByLabelName("Email", "input").sendKeys(invalidMail2);
		wsrhp.HSaveButton().click();
	}

	@Then("^error message for email input is displayed$")
	public void error_message_for_email_input_is_displayed() throws Throwable {
		try {
			Assert.assertTrue(ut.getMessageByLabelName("Email").isDisplayed(),
					"Message under Manager Email field is not displayed.");
		} catch (Exception e) {
			Assert.assertTrue(false, "Message under Manager Email field is not displayed.");
		}
	}
	
	
	
	//SCENARIO4- US01TC04
	@And("^completes the required fields with invalid User Name$")
	public void completes_the_required_fields_with_invalid_user_name() throws IOException, InterruptedException {
		CreateHeader(testsDataFile, 4);
		String invalidUser= ut.GetCellData(testsDataFile, 1, 4, 7);
		ut.getInputByLabelName("User", "input").click();
		ut.getInputByLabelName("User", "input").sendKeys(invalidUser);
		wsrhp.HSaveButton().click();
		
	}
	
	@Then("^error message for User Name lookup field is displayed$")
	public void error_message_for_user_name_lookup_field_is_displayed() {
		try {
			Assert.assertTrue(ut.getMessageByLabelName("User").isDisplayed());
		} catch(Exception e) {
			Assert.assertTrue(false, "No error message is displayed");
		}
		
		String currentURL= driver.getCurrentUrl();
		try {
			Assert.assertTrue(currentURL.contains("Header__c/new"));
		} catch(Exception e) {
			Assert.assertTrue(false, "User was redirected to new Header details page.");
		}
	}

	

}
