Feature: Add Employee Functionality

  Background:
    When enter valid credentials
    And click on login button
    Then verify dashboard is displayed
    When click on PIM
    And click on add employee button

  @addEmployeeWithoutLogin
  Scenario: Add employee without login details
    Then enter first and last name
    And click on save button
    Then verify employee is added succesfully

  @addEmployeeWithLogin
  Scenario: Add employee with login credentials
    Then enter first and last name and middle name
    When click on login details checkbox
    Then enter login information
    And click on save button
    Then verify employee is added succesfully

  @parameter
  Scenario: Add employee without login details but with middle name
    Then enter first name "Marta", middle name "Mary" and last name "Ostash"
    And click on save button
    Then verify that "Marta Mary Ostash" is added successfully

  @examples
  Scenario Outline: Adding multiple employees without login details
    When enter "<FirstName>", "<MiddleName>" and "<LastName>"
    And click on save button
    Then verify "<FirstName>", "<MiddleName>" and "<LastName>" is added successfully

    Examples:
      | FirstName | MiddleName | LastName |
      | Mark      | J          | Smith    |
      | John      | K          | Wick     |

  @dtWithHeader
  Scenario: Adding multiple employees at one execution
    When add multiple employees and verify they are added successfully
      | FirstName | MiddleName | LastName | EmployeeId |
      | Jack      | J          | Toronto  | 1111111111 |
      | David     | K          | Wick     | 2222222222 |

    @excelTask
  Scenario: Adding multiple employees from excel
    When add multiple employees from excel "EmployeeData" sheet and verify they are added








