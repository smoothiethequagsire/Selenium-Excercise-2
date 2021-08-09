package stepsDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import Resources.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class SubmitBodyStepsDefinition extends Base {

	// SCENARIO1- US5TC01

	@And("^clicks Submit to Manager button and confirms submit$")
	public void clicks_submit_to_manager_button_and_confirms_submit() throws InterruptedException {
		w.until(ExpectedConditions.urlContains("/view"));
		w.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Submit to Manager']")));
		WebElement submitbutton= wsrbp.submitButton();
		w.until(ExpectedConditions.visibilityOf(submitbutton));
		ut.JSClick(submitbutton);
		w.until(ExpectedConditions.visibilityOf(wsrbp.submitSaveButton()));
		wsrbp.submitSaveButton().click();
	}

	@Then("^body is succesfully submitted and success message is shown$")
	public void body_is_succesfully_submitted_and_success_message_is_shown() {
		try {
			w.until(ExpectedConditions.visibilityOf(wsrbp.SuccessMessage()));
			Assert.assertTrue(wsrbp.SuccessMessage().isDisplayed());
		} catch (Exception e) {
			Assert.assertTrue(false, "No success message was shown.");
		}
	}
	
	// SCENARIO2- US5TC02
	
	@And("^a body record was previously created and submitted$")
    public void a_body_record_was_previously_created_and_submitted() {
        try {
        	Assert.assertTrue(wsrbp.bodyRecordLink().isDisplayed());
        }
        catch(Exception e) {
        	Assert.assertTrue(false, "No body record found.");
        }
        
        wsrbp.bodyRecordLink().click();
        w.until(ExpectedConditions.visibilityOf(wsrbp.submittedStatus())); 
        try {
        	Assert.assertTrue(wsrbp.submittedStatus().getText().equalsIgnoreCase("submitted"));
        }
        catch(Exception e) {
        	Assert.assertTrue(false, "A body record was created, but has not been submitted previously.");
        }
    }
	
	@Then("^success message is not displayed and body is not submitted$")
    public void success_message_is_not_displayed_and_body_is_not_submitted() {
		 try {
	        	Assert.assertFalse(wsrbp.SuccessMessage().isDisplayed(), "Success message is displayed.");
	        }
	        catch(Exception e) {
	        	Assert.assertFalse(false);
	        }
    }
	
}
