Feature: Test login into application

  Scenario:The customer must be able to login after entering correct username and password
#  The customer must be directed to the homepage if the username and password entered are correct.

    Given Open Firefox and access http://localhost:4200/

    When Customer introduces valid username and password

    Then Upon click, customer is logged in successfully and redirected on http://localhost:4200/users