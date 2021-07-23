Feature: Test edit a user's data

  Scenario:The customer must be able to edit a certain user's data

    Given Customer  opens ediUser modal

    When Customer introduces new valid credentials

    Then Upon submit the new data is displayed for that user