Feature: Account Registration
  Scenario: Adding new address
    Given the user visits login page
    When the user type email and password
    And the user click button sign in
    And the user click [Addresses]
    When the user click Create new address
    And the user fill form data:
      | Alias        | Address          | City     | Postal Code | Country       | Phone     |
      | Bear         | ul. Test 126     | Poznań   | 61-000      | United Kingdom| 129956111 |
    And the user submit form
    Then user confirmation message
    And the user delete new address