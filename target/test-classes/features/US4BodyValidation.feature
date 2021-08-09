
Feature: WSR Body record creation validations

  @US4TC01
  Scenario: Create a WSR body record without values on day hours
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And completes the required fields with null day hours fields
    Then Body record is not created
    And error messages are displayed for day hours fields
 
  @US4TC02
  Scenario: Create a WSR body record with extra hours per day
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And completes the required fields with value greater than eight on day hours fields
    Then Body record is not created
    And an error message is displayed
    
  @US4TC03
  Scenario: Create a WSR body record with wrong sum of total stories
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And completes the required fields with sum of stories different from total stories
    Then Body record is not created
    And an error message is displayed
    
   @US4TC04
  Scenario: Create a WSR body record with sprint start date further than end date
    Given user is logged into Salesforce and on wsr body tab
    And a header was created
    When clicks New button
    And completes the required fields with sprint start date further than end date
    Then Body record is not created
    And an error message is displayed