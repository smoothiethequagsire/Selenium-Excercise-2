package stepsDefinition;

import java.io.IOException;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import Resources.Base;
import io.cucumber.java.en.And;

public class ValidateBodyRecordStepsDefinition extends Base {

	String[] dayHoursLabels = { "Monday Hours", "Tuesday Hours", "Wednesday Hours", "Thursday Hours", "Friday Hours" };
	String[] requiredDateFields = { "Start Date", "End Date" };
	String[] storiesFields = { "Assigned", "Completed", "In Progress", "Not Started" };

	// SCENARIO1- US4TC01

	@And("^completes the required fields with null day hours fields$")
	public void completes_the_required_fields_with_null_day_hours_fields() throws IOException {
		// Fill fields with first set of data
		CreateBody("src/main/java/Resources/Test cases Data.xlsx", 6);
		// Clear auto generated values for day hours fields
		for (String label : dayHoursLabels) {
			ut.getInputByLabelName(label, "input").clear();
		}
		wsrbp.BSaveButton().click();
	}

	@And("^error messages are displayed for day hours fields$")
	public void error_messages_are_displayed_for_day_hours_fields() {
		SoftAssert softAs = new SoftAssert();
		for (String label : dayHoursLabels) {
			try {
				softAs.assertTrue(ut.getMessageByLabelName(label).isDisplayed());
			} catch (Exception e) {
				softAs.assertTrue(false, "No error message found for the following field: " + label);
			} finally {
				softAs.assertAll();
			}
		}
	}

	// SCENARIO2- US4TC02
	@And("^completes the required fields with value greater than eight on day hours fields$")
	public void completes_the_required_fields_with_value_greater_than_eight_on_day_hours_fields() throws IOException {
		// Fill fields with first set of data
		CreateBody("src/main/java/Resources/Test cases Data.xlsx", 6);
		// Change auto generated data for day hours fields
		int counter = 14;
		for (String field : dayHoursLabels) {
			String data = ut.GetCellData("src/main/java/Resources/Test cases Data.xlsx", 1, 11, counter);
			ChangeFieldData(ut.getInputByLabelName(field, "input"), data);
			counter++;
		}
		wsrbp.BSaveButton().click();
	}

	@And("^an error message is displayed$")
	public void an_error_message_is_displayed() {
		try {
			Assert.assertTrue(wsrbp.errorMessage().isDisplayed());
		} catch (Exception e) {
			Assert.assertTrue(false, "No error message was displayed");
		}
	}

	// SCENARIO3- US4TC03

	@And("^completes the required fields with sum of stories different from total stories$")
	public void completes_the_required_fields_with_sum_of_stories_different_from_total_stories() throws IOException {
		// Fill fields with first set of data
		CreateBody("src/main/java/Resources/Test cases Data.xlsx", 13);
		wsrbp.BSaveButton().click();
		try {
			Assert.assertTrue(wsrbp.errorMessage().isDisplayed());
		} catch(Exception e) {
			Assert.assertTrue(false, "Error message was not displayed.");
		}
		// Change stories amount data for each field to validate each one behaves correctly
		int rowCounter= 14;
		for (int i=0; i<3; i++) {
			int cellCounter=5;
			for (String label : storiesFields) {
				String data= ut.GetCellData("src/main/java/Resources/Test cases Data.xlsx", 1, rowCounter, cellCounter);
				ChangeFieldData(ut.getInputByLabelName(label, "input"), data);
				cellCounter++;
				
			}
			wsrbp.BSaveButton().click();
			try {
				Assert.assertTrue(wsrbp.errorMessage().isDisplayed());
			} catch(Exception e) {
				Assert.assertTrue(false, "Error message was not displayed.");
			}
			rowCounter++;
		}
		
	}

	// SCENARIO4- US4TC04

	@And("^completes the required fields with sprint start date further than end date$")
	public void completes_the_required_fields_with_sprint_start_date_further_than_end_date() throws IOException {
		CreateBody("src/main/java/Resources/Test cases Data.xlsx", 12);
		wsrbp.BSaveButton().click();
	}

}
