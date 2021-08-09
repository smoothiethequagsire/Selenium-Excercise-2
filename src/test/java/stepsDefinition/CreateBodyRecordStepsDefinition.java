package stepsDefinition;

import java.io.IOException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import Resources.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class CreateBodyRecordStepsDefinition extends Base {

	String[] requiredFields = { "Body Name", "Sprint Deliverables", "Assigned", "Completed", "In Progress",
			"Not Started" };
	String[] dayHoursLabels = { "Monday Hours", "Tuesday Hours", "Wednesday Hours", "Thursday Hours", "Friday Hours" };
	String[] requiredDateFields = { "Start Date", "End Date" };

	// SCENARIO1- US3TC01

	@Given("^user is logged into Salesforce and on wsr body tab$")
	public void user_is_logged_into_salesforce_and_on_wsr_body_tab() throws IOException {
		driver.get(devSpaceId + prop.getProperty("wsrBodyUrl"));
		LogIn();
		w.until(ExpectedConditions.visibilityOf(wsrbp.BNewButton()));
	}

	@And("^a header was created$")
	public void a_header_was_created() {
		wsrbp.BNewButton().click();
		w.until(ExpectedConditions.visibilityOf(ut.getInputByLabelName("Header", "input")));
		ut.getInputByLabelName("Header", "input").click();
		try {
			w.until(ExpectedConditions.visibilityOf(wsrbp.LookUpOption()));
			Assert.assertTrue(wsrbp.LookUpOption().isDisplayed());
		} catch (Exception e) {
			Assert.assertTrue(false, "No header is created.");
		}
		wsrbp.CancelButton().click();
		w.until(ExpectedConditions.visibilityOf(wsrbp.BNewButton()));
	}

	@And("^completes the required fields using provided data$")
	public void completes_the_required_fields_using_provided_data() throws IOException, InterruptedException {
		CreateBody("src/main/java/Resources/Test cases Data.xlsx", 6);
	}

	@Then("^Body record is created and user redirected to its details page$")
	public void body_record_is_created_and_user_redirected_to_its_details_page() {
		try {
			w.until(ExpectedConditions.visibilityOf(wsrbp.SuccessMessage()));
			Assert.assertTrue(wsrbp.SuccessMessage().isDisplayed());
		} catch (Exception e) {
			Assert.assertTrue(false, "No succesful body creation message is displayed.");
		}

		Assert.assertTrue(driver.getCurrentUrl().contains("view"));
	}

	// SCENARIO2- US3TC02

	@Then("^Body record is not created$")
	public void body_record_is_not_created() {
		try {
			Assert.assertFalse(wsrbp.SuccessMessage().isDisplayed(),
					"The body record was created when it should not have.");
		} catch (Exception e) {
			Assert.assertFalse(false);
		}

	}

	@And("^error messages are displayed for body fields$")
	public void error_messages_are_displayed_for_body_fields() {

		SoftAssert softAs = new SoftAssert();
		for (String label : requiredFields) {
			try {
				WebElement errorMessage = ut.getMessageByLabelName(label);
				softAs.assertTrue(errorMessage.isDisplayed());
			} catch (Exception e) {
				softAs.assertTrue(false, "No error message found for the following empty field: " + label);
			}
		}

		// for some reason, "Sprint Start Date" and "Sprint End Date" error messages
		// have a different path

		for (String label : requiredDateFields) {
			try {
				WebElement errorDateMessage = ut.getSprintDateErrorMsg(label);
				softAs.assertTrue(errorDateMessage.isDisplayed());
			} catch (Exception e) {
				softAs.assertTrue(false, "No error message found for the following empty field: " + label);
			} finally {
				softAs.assertAll();
			}
		}
	}

	// SCENARIO3- US3TC03

	@And("^fill required data with invaid numeric values and click save$")
	public void fill_required_data_with_invaid_numeric_values_and_click_save() throws IOException {

		// Fill fields with first set of data
		CreateBody("src/main/java/Resources/Test cases Data.xlsx", 7);
		// replace pre set hours with first invalid values
		int counter = 14;
		for (String field : dayHoursLabels) {
			String data = ut.GetCellData("src/main/java/Resources/Test cases Data.xlsx", 1, 7, counter);
			ChangeFieldData(ut.getInputByLabelName(field, "input"), data);
			counter++;
		}
		wsrbp.BSaveButton().click();
		// replace pre set hours with first invalid values
		counter = 14;
		for (String field : dayHoursLabels) {
			String data = ut.GetCellData("src/main/java/Resources/Test cases Data.xlsx", 1, 8, counter);
			ChangeFieldData(ut.getInputByLabelName(field, "input"), data);
			counter++;
		}
		wsrbp.BSaveButton().click();
		counter = 14;
		for (String field : dayHoursLabels) {
			String data = ut.GetCellData("src/main/java/Resources/Test cases Data.xlsx", 1, 9, counter);
			ChangeFieldData(ut.getInputByLabelName(field, "input"), data);
			counter++;
		}
		wsrbp.BSaveButton().click();
	}

	@And("^error messages are displayed for numeric fields$")
	public void error_messages_are_displayed_for_numeric_fields() {
		SoftAssert softAs = new SoftAssert();
		for (String field : dayHoursLabels) {
			try {
				softAs.assertTrue(ut.getMessageByLabelName(field).isDisplayed());
			} catch (Exception e) {
				softAs.assertTrue(false, "No error message found under" + field + "field");
			} finally {
				softAs.assertAll();
			}
		}
	}

	// SCENARIO 4- @US3TC04

	@And("^fill required data with sprint less than five days$")
	public void fill_required_data_with_sprint_less_than_five_days() throws IOException {
		CreateBody("src/main/java/Resources/Test cases Data.xlsx", 10);
		wsrbp.BSaveButton().click();
	}

	@And("^error messages are displayed for spring date fields$")
	public void error_messages_are_displayed_for_spring_date_fields() {
		SoftAssert softAs = new SoftAssert();
		for (String label : requiredDateFields) {
			try {
				WebElement errorDateMessage = ut.getSprintDateErrorMsg(label);
				softAs.assertTrue(errorDateMessage.isDisplayed());
			} catch (Exception e) {
				softAs.assertTrue(false, "No error message found for the following field: " + label);
			} finally {
				softAs.assertAll();
			}
		}
	}

}
