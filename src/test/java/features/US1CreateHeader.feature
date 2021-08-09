
Feature: Create a header
	
	@US1TC01
  Scenario: Create a header completing the required fields
    Given User logs into Salesforce page
    And clicks the applauncher and search for WSR
    And clicks on WSR builder option
    When user clicks Header tab
		And clicks New button
    And completes the required fields using provided data and clicks on Save button
    Then Header is created and user redirected to its details page

    
  @US1TC02
  Scenario: Create a header without filling any fields
  	Given user is logged into Salesforce and on header tab
  	When clicks New button
  	And clicks Save button
  	Then error messages are displayed
  	
  @US1TC03
  Scenario: Create header using invalid email format
  	Given user is logged into Salesforce and on header tab
  	When clicks New button
  	And completes the required fields with invalid email format
  	Then error message for email input is displayed
  	
  @US1TC04
  Scenario: Create header using a non existant User Name
  Given user is logged into Salesforce and on header tab
  When clicks New button
  And completes the required fields with invalid User Name
  Then error message for User Name lookup field is displayed
  	