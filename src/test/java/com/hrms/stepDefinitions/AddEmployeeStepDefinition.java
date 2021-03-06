package com.hrms.stepDefinitions;

import com.hrms.pages.DashboardPage;
import com.hrms.utils.Constants;
import com.hrms.utils.ExcelReading;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class AddEmployeeStepDefinition extends DashboardPage{
    @When("click on PIM")
    public void click_on_PIM() {
        dashboardPage.clickOnPIM();
    }

    @When("click on add employee button")
    public void click_on_add_employee_button() {
        dashboardPage.clickOnAddEmployeeBtn();
    }

    @Then("enter first and last name")
    public void enter_first_and_last_name() {
        addEmployeePage.enterFirstAndLastName("Thor", "Potter");
    }

    @Then("click on save button")
    public void click_on_save_button() {
        addEmployeePage.clickOnSaveBtn();
    }

    @Then("verify employee is added succesfully")
    public void verify_employee_is_added_succesfully() {
        String actualProfileName = personalDetailPage.getUserProfileName();
        Assert.assertEquals("Verifying profile name", "Thor Potter", actualProfileName);
    }

    @Then("enter first name {string}, middle name {string} and last name {string}")
    public void enterFirstNameMiddleNameAndLastName(String firstName, String middleName, String lastName) {
        addEmployeePage.enterFirstMiddleAndLastName(firstName, middleName, lastName);
    }

    @Then("verify that {string} is added successfully")
    public void verifyThatIsAddedSuccessfully(String fullName) {
        String actualProfileName = personalDetailPage.getUserProfileName();
        Assert.assertEquals("Verifying profile name", fullName, actualProfileName );
    }

    @When("enter {string}, {string} and {string}")
    public void enterAnd(String firstName, String middleName, String lastName) {
        addEmployeePage.enterFirstMiddleAndLastName(firstName, middleName, lastName);
    }


    @Then("verify {string}, {string} and {string} is added successfully")
    public void verifyAndIsAddedSuccessfully(String firstName, String middleName, String lastName) {
        String fullName = firstName + " " + middleName + " " + lastName;
        String actualProfileName = personalDetailPage.getUserProfileName();
        Assert.assertEquals("Verifying profile name", fullName, actualProfileName );
    }

    @When("add multiple employees and verify they are added successfully")
    public void add_multiple_employees_and_verify_they_are_added_successfully(DataTable employees) throws InterruptedException {
       List<Map<String, String>> employeeNames = employees.asMaps();

       for(Map<String, String> employeeName:employeeNames){
           String firstName = employeeName.get("FirstName");
           String middleName = employeeName.get("MiddleName");
           String lastName = employeeName.get("LastName");
           String employeeId = employeeName.get("EmployeeId");

           addEmployeePage.enterFirstMiddleAndLastName(firstName, middleName, lastName);
           addEmployeePage.enterEmployeeId(employeeId);
           addEmployeePage.clickOnSaveBtn();
           String actualFullName = personalDetailPage.getUserProfileName();
           String expectedFullName = firstName + " " + middleName + " " + lastName;
           Assert.assertEquals("Verifying profile name", expectedFullName, actualFullName );
           dashboardPage.clickOnAddEmployeeBtn();
           Thread.sleep(2000);
       }

    }

    @When("add multiple employees from excel {string} sheet and verify they are added")
    public void add_multiple_employees_from_excel_sheet_and_verify_they_are_added(String sheetName) throws InterruptedException {
       List<Map<String, String>> excelData = ExcelReading.excelIntoListMap(Constants.TESTDATA_FILEPATH, sheetName);

       for(Map<String, String> excelEmpName: excelData){
           String firstName = excelEmpName.get("FirstName");
           String middleName = excelEmpName.get("MiddleName");
           String lastName = excelEmpName.get("LastName");
           String employeeId = excelEmpName.get("EmployeeId");

           addEmployeePage.enterFirstMiddleAndLastName(firstName, middleName, lastName);
           addEmployeePage.enterEmployeeId(employeeId);
           addEmployeePage.clickOnSaveBtn();
           String actualFullName = personalDetailPage.getUserProfileName();
           String expectedFullName = firstName + " " + middleName + " " + lastName;
           Assert.assertEquals("Verifying profile name", expectedFullName, actualFullName );
           dashboardPage.clickOnAddEmployeeBtn();
           Thread.sleep(2000);

       }

    }
}
