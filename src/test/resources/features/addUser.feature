Feature: Test add new user into application

  Scenario:The customer must be able to add users into the application

    Given Customer  opens addUser modal

    When Customer introduces valid credentials

    Then Upon submit a new user is created