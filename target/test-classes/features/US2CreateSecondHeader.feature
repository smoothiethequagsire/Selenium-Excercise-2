
Feature: Create a header

  @US2TC01
  Scenario: Create a second header
    Given user is logged into Salesforce and on header tab
    And a header was previously created
    When clicks New button 
   And completes the required fields using provided data and clicks on Save button
    Then An error message is displayed and header is not created