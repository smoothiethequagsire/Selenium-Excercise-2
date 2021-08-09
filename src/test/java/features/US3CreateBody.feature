
Feature: WSR Body record creation

  @US3TC01
  Scenario: Create a WSR body record
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And completes the required fields using provided data
    And clicks Save button
    Then Body record is created and user redirected to its details page
 
 	@US3TC02
  Scenario: Create a WSR body record with null fields
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And clicks Save button
    Then Body record is not created
    And error messages are displayed for body fields
    
  @US3TC03
  Scenario: Validate WSR Body record number fields 
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And fill required data with invaid numeric values and click save
    Then Body record is not created
    And error messages are displayed for numeric fields
    
    @US3TC04
  Scenario: Validate sprint duration
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And fill required data with sprint less than five days
    Then Body record is not created
    And error messages are displayed for spring date fields