package stepsDefinition;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import PageObjects.HomePage;
import Resources.Base;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CreateSecondHeaderStepsDefinition extends Base {
	
	HomePage hp;
	
	// SCENARIO1- US1TC01
	@And("^a header was previously created$")
    public void a_header_was_previously_created() throws Throwable {
		
		w.until(ExpectedConditions.visibilityOf(wsrhp.HNewButton()));
		
		//Searches for at least one previous Header object created
		try {
			Assert.assertTrue(wsrhp.HeaderCreated().isDisplayed());
		} 
		catch(Exception e){
			Assert.assertTrue(false, "There is no previous header created.");
		}
    }
    
    @Then("^An error message is displayed and header is not created$")
    public void an_error_message_is_displayed_and_header_is_not_created() throws Throwable {
    	String currentURL= driver.getCurrentUrl();
    	//Checks whether a successful creation message is shown
        Assert.assertTrue(!(wsrhp.SuccessMessage().isDisplayed()), "Message indicating the creation of the header is displayed.");
        //Checks if the url of the creation form changes to another after attempted creation (which should not happen)
        Assert.assertTrue(currentURL.contains("Header__c/new?"), "User was redirected to new header details page.");
    }
}
