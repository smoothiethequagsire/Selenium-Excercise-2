
Feature: Submit body to manager mail

  @US5TC01
  Scenario: Create a WSR body record and submit it
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And completes the required fields using provided data
    And clicks Save button
    And Body record is created and user redirected to its details page
    And clicks Submit to Manager button and confirms submit
 		Then body is succesfully submitted and success message is shown
  
  @US5TC02
  Scenario: Submit an already submitted Body record
    Given user is logged into Salesforce and on wsr body tab
    And a body record was previously created and submitted
    When clicks Submit to Manager button and confirms submit
    Then success message is not displayed and body is not submitted
    