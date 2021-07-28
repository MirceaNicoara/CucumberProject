Feature: Test all user features into the application

  Scenario Outline:The customer must be able to log in, add, edit and delete users

    Given Customer logs into the application

    Then Customer is able to add a new user using these values "<username>", "<email>", "<fullName>" and "<password>"

    And Customer is able to edit an existing user

    And Customer is able to delete a user

    Examples:
      | username   | email                 | fullName           | password       |
      | ParamUser1 | ParamEmail1@gmail.com | ParamUser1FullName | ParamUser1Pass |
      | ParamUser2 | ParamEmail2@gmail.com | ParamUser2FullName | ParamUser2Pass |
      | ParamUser3 | ParamEmail3@gmail.com | ParamUser3FullName | ParamUser3Pass |