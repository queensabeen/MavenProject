#Author: Instructor
@apiWorkflow
Feature: Syntax HRMS API Workflow
  Description: This feature file tests Syntax HRMS API Workflow

  Background:
    Given a JWT is generated

  Scenario: Creating an Employee
    Given a request is prepared to create an employee
    When a POST call is made to create an employee
    Then the status code for creating an employee is 201
    And the employee is created contains key "Message" and value "Entry Created"
    And the employeeID "Employee[0].employee_id" is stored in the global variable to be used for other calls

  Scenario: Retrieving the created employee
    Given a request is prepared to retrieve the created employee
    When a GET call is made to retrieve the created employee
    Then the status code for retrieving the created employee is 200
    And the retrieved employeeID "employee[0].employee_id" matches the globally stored employeeID
    And the retrieved data matches the data used to create the employee

  Scenario: Update the created Employee
    Given a request is prepared to update the created employee
    When a PUT call is made to update the employee
    Then the status code is 201
    And the updated employee contains key "Message" and value "Entry updated"

  Scenario: Retrieving the updated employee
    Given a request is prepared to retrieve the updated employee
    When a GET call is made to retrieve the updated employee
    Then the status code for retrieving the updated employee is 200
    And the retrieved employee_middle_name "employee[0].emp_middle_name" matches the globally stored emp middle name

  Scenario: Partially updating the employee
    Given a request is prepared to partially update the employee
    When a PATCH call is made to partially update the employee
    Then the status code is 201
    And the partially updated employee contains key "Message" and value "Entry updated"
    And the partially updated employee "employee[0].emp_firstname" matches globally stored first name

  Scenario: Delete the created employee
    Given a request is prepared to delete the employee
    When a DELETE call in made
    Then the status code is 201
    And the employee is successfully deleted with the "Message" in response "Entry deleted"
    And the "employee[0].employee_id" is the same as the one stored in global

  Scenario: Retrieve all employees
    Given a request is prepared to retrieve all employees
    When a GET call is made to retrieve all employees
    Then the status code is 200
    And it contains key1 "Total Employees" and Key2 "Employees"

  Scenario: Retrieve all employee status
    Given a request is prepared to get all employee status
    When a GET call is made to retrieve the status of all employees
    Then it contains the value1 "Employee" and value2 "Worker"