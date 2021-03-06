package com.hrms.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hrms.utils.apiConstants;
import com.hrms.utils.apiPayLoadConstants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.openqa.selenium.json.Json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class apiTestingFinalSteps {

    RequestSpecification request;
    Response response;
    static String employeeID;
    static String updatedEmployeeMiddleName;
    static String partialUpdatedFirstName;


    @Given("a request is prepared to create an employee")
    public void a_request_is_prepared_to_create_an_employee() {
        //preparing request to create Employee
        request = given().header(apiConstants.Header_content_type, apiConstants.Content_type)
                .header(apiConstants.Header_authorization, generateTokenSteps.token)
                .body(apiPayLoadConstants.createEmployeeBody()).log().all();//logs all the request
        //apiMethods.createEmployeeRequest(generateTokenSteps.token, CommonMethods.readJson(apiConstants.CREATE_EMPLOYEE_JSON));

        File input = new File("C:/Users/FS/eclipse-workspace/CucumberFramework/src/test/resources/JsonData/createUser.json");
        JsonObject CreateUserData = null;
        try {
            //parsing the input file
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
            CreateUserData = fileElement.getAsJsonObject();
            //Access the key message

            JsonElement Message=CreateUserData.get("Message");
            JsonArray Employee=CreateUserData.get("Employee").getAsJsonArray();
            JsonArray Employee_detail = Employee.getAsJsonArray();
            JsonElement Employee1_details = Employee_detail.get(0);
            JsonObject Employee1_object = Employee1_details.getAsJsonObject();

            System.out.println(Employee1_object.get("emp_firstname"));


//            JsonElement employee_birthday = CreateUserData.get("emp_birthday");
//            System.out.println("The employee's birthday is " +employee_birthday);


        } catch (FileNotFoundException e) {
            e.printStackTrace();


        }
    }

    @When("a POST call is made to create an employee")
    public void a_POST_call_is_made_to_create_an_employee() {
        //sending the request to create employee
        response = request.when().post(apiConstants.CREATE_EMPLOYEE_URI);
    }

    @Then("the status code for creating an employee is {int}")
    public void the_status_code_for_creating_an_employee_is(int status_code) {
        //assert that the status code is 201
        response.then().assertThat().statusCode(status_code);
    }

    @And("the employee is created contains key {string} and value {string}")
    public void theEmployeeIsCreatedContainsKeyAndValue(String key, String value) {
        response.then().assertThat().body(key, equalTo(value));
    }

    @And("the employeeID {string} is stored in the global variable to be used for other calls")
    public void theEmployeeIDIsStoredInTheGlobalVariableToBeUsedForOtherCalls(String value) {
        //extract employeeID from the json response
        employeeID = response.jsonPath().getString(value);
    }

    //=====================================Scenario: retrieving the created employee===============================
    @Given("a request is prepared to retrieve the created employee")
    public void aRequestIsPreparedToRetrieveTheCreatedEmployee() {
        //preparing the request to retrieve the created employee
        System.out.println(employeeID);
        request = given()
                .header(apiConstants.Header_content_type, apiConstants.Content_type)
                .header(apiConstants.Header_authorization, generateTokenSteps.token)
                .queryParam("employee_id", employeeID);
    }

    @When("a GET call is made to retrieve the created employee")
    public void aGETCallIsMadeToRetrieveTheCreatedEmployee() {
        //sending the request to retrieve the created employee
        response = request.when().get(apiConstants.GET_ONE_EMPLOYEE_URI);
    }

    @Then("the status code for retrieving the created employee is {int}")
    public void theStatusCodeForRetrievingTheCreatedEmployeeIs(int status_code) {
        //assert that status code is 200
        response.then().assertThat().statusCode(status_code);
    }

    @And("the retrieved employeeID {string} matches the globally stored employeeID")
    public void theRetrievedEmployeeIDMatchesTheGloballyStoredEmployeeID(String value) {
        //assert that employee id is same as the one stored globally
        response.then().assertThat().body(value, equalTo(employeeID));
    }

    @And("the retrieved data matches the data used to create the employee")
    public void theRetrievedDataMatchesTheDataUsedToCreateTheEmployee() {

        //response.then().assertThat().body("employee[0].emp_firstname",equalTo("sabeen"));

        JsonPath jpath = response.jsonPath();
//        String emp_first_name = jpath.getString("employee[0].emp_firstname");
//        String emp_last_name = jpath.getString("employee[0].emp_lastname");
//        String emp_middle_name = jpath.getString("employee[0].emp_middle_name");
//        String emp_birthday = jpath.getString("employee[0].emp_birthday");
//        String emp_gender = jpath.getString("employee[0].emp_gender");
//        String emp_job_title = jpath.getString("employee[0].emp_job_title");
//        String emp_status = jpath.getString("employee[0].emp_status");

//        System.out.println(emp_first_name +" "+ emp_last_name +" "+ emp_middle_name +" "+ emp_birthday
//                +" "+ emp_gender +" "+ emp_job_title +" "+ emp_status);

//        assertThat(emp_first_name, equalTo("sabeen"));
//        assertThat(emp_last_name, equalTo("sadi"));
//        assertThat(emp_middle_name, equalTo("none"));
//        assertThat(emp_birthday, equalTo("1989-02-17"));
//        assertThat(emp_gender, equalTo("Female"));
//        assertThat(emp_job_title, equalTo("Developer"));
//        assertThat(emp_status, equalTo("Freelance"));

        String Body = response.asString();

        JsonElement Response_json=JsonParser.parseString(Body);
        JsonObject Response_json_obj = Response_json.getAsJsonObject();
        JsonElement Employee_data = Response_json_obj.get("employee");
        JsonArray Employee_data_array = Employee_data.getAsJsonArray();
        JsonElement Employee_0_information=Employee_data_array.get(0);
        JsonObject Employee_info = Employee_0_information.getAsJsonObject();
        System.out.println("The emp first name is "+Employee_info.get("emp_firstname").toString());

    }
//====================updating the created employee==============================

    @Given("a request is prepared to update the created employee")
    public void a_request_is_prepared_to_update_the_created_employee() {
        updatedEmployeeMiddleName = "updated middle name";
        JSONObject payload = new JSONObject();
        payload.put("employee_id", employeeID);
        payload.put("emp_firstname", "sabeen");
        payload.put("emp_lastname", "sadi");
        payload.put("emp_middle_name", "updated middle name");
        payload.put("emp_gender", "F");
        payload.put("emp_birthday", "1989-02-17");
        payload.put("emp_status", "Freelance");
        payload.put("emp_job_title", "Developer");
        String final_payload = payload.toString();

        request = given().header(apiConstants.Header_content_type, apiConstants.Content_type)
                .header(apiConstants.Header_authorization, generateTokenSteps.token)
                .body(final_payload).log().all();
    }

    @When("a PUT call is made to update the employee")
    public void a_PUT_call_is_made_to_update_the_employee() {
        //sending the [ut request to update the employee
        response = request.when().put(apiConstants.UPDATE_EMPLOYEE_URI);
    }

    @Then("the status code is {int}")
    public void the_status_code_is(int status_code) {
        //asserting status code iss 201
        response.then().assertThat().statusCode(status_code);
    }

    @Then("the updated employee contains key {string} and value {string}")
    public void the_updated_employee_contains_key_and_value(String key, String value) {
        //assert that the response contains message entry updated
        response.then().assertThat().body(key, equalTo(value));
    }

    //===========================retrieving the updated employee information================================

    @Given("a request is prepared to retrieve the updated employee")
    public void aRequestIsPreparedToRetrieveTheUpdatedEmployee() {
        request=given().header(apiConstants.Header_content_type, apiConstants.Content_type)
                .header(apiConstants.Header_authorization, generateTokenSteps.token)
                .queryParam("employee_id", employeeID);
    }

    @When("a GET call is made to retrieve the updated employee")
    public void aGETCallIsMadeToRetrieveTheUpdatedEmployee() {
        response=request.when().get(apiConstants.GET_ONE_EMPLOYEE_URI);
    }

    @Then("the status code for retrieving the updated employee is {int}")
    public void theStatusCodeForRetrievingTheUpdatedEmployeeIs(int status_code) {
        //assert status code
        response.then().assertThat().statusCode(status_code);
    }

    @And("the retrieved employee_middle_name {string} matches the globally stored emp middle name")
    public void theRetrievedEmployee_middle_nameMatchesTheGloballyStoredEmpMiddleName(String updatedMiddleName) {
        //response.then().assertThat().body(updatedMiddleName,equalTo(updatedEmployeeMiddleName));

        String actualMiddleName=response.jsonPath().getString(updatedMiddleName);
        assertThat(actualMiddleName, equalTo(updatedEmployeeMiddleName));
    }

    //===============partially updating the employee========================

    @Given("a request is prepared to partially update the employee")
    public void a_request_is_prepared_to_partially_update_the_employee() {
        partialUpdatedFirstName="Updated Name Success";
        JSONObject payload = new JSONObject();
        payload.put("employee_id", employeeID);
        payload.put("emp_firstname", partialUpdatedFirstName);
        String final_payload=payload.toString();

       request=given().header(apiConstants.Header_content_type, apiConstants.Content_type)
               .header(apiConstants.Header_authorization, generateTokenSteps.token)
               .body(final_payload);
    }


    @When("a PATCH call is made to partially update the employee")
    public void a_PATCH_call_is_made_to_partially_update_the_employee() {
        response=request.when().patch(apiConstants.PARTIALLY_UPDATE_EMPLOYEE_URI);
    }


    @And("the partially updated employee contains key {string} and value {string}")
    public void thePartiallyUpdatedEmployeeContainsKeyAndValue(String key, String value) {
        response.then().assertThat().body(key,equalTo(value));
    }

    @And("the partially updated employee {string} matches globally stored first name")
    public void thePartiallyUpdatedEmployeeMatchesGloballyStoredFirstName(String value) {
        response.then().assertThat().body(value,equalTo(partialUpdatedFirstName));
    }


    //====================deleting created employee==============================


    @Given("a request is prepared to delete the employee")
    public void aRequestIsPreparedToDeleteTheEmployee() {
        request=given().header(apiConstants.Header_content_type, apiConstants.Content_type)
                .header(apiConstants.Header_authorization, generateTokenSteps.token)
                .queryParam("employee_id", employeeID);
    }

    @When("a DELETE call in made")
    public void aDELETECallInMade() {
        response=request.when().delete(apiConstants.DELETE_EMPLOYEE_URI);
    }

    @And("the employee is successfully deleted with the {string} in response {string}")
    public void theEmployeeIsSuccessfullyDeletedWithTheInResponse(String key, String value) {
        response.then().assertThat().body(key,equalTo(value));
    }

    @And("the {string} is the same as the one stored in global")
    public void theIsTheSameAsTheOneStoredInGlobal(String employee_id) {
        response.then().assertThat().body(employee_id, equalTo(employeeID));
    }

    //====================retrieving all employees==============================

    @Given("a request is prepared to retrieve all employees")
    public void aRequestIsPreparedToRetrieveAllEmployees() {
        request=given().header(apiConstants.Header_content_type, apiConstants.Content_type)
                .header(apiConstants.Header_authorization, generateTokenSteps.token)
                .queryParam("employee_id", employeeID);
    }

    @When("a GET call is made to retrieve all employees")
    public void aGETCallIsMadeToRetrieveAllEmployees() {
        response=request.when().get(apiConstants.GET_ALL_EMPLOYEES_URI);
    }

    @Then("it contains key1 {string} and Key2 {string}")
    public void it_contains_key1_and_Key2(String key1, String key2) {
//        JsonPath js = response.jsonPath();
//        String payload = js.toString();
//        System.out.println(payload);

        response.then().assertThat().body(containsString(key1)).body(containsString(key2));
        String payload = response.asString();


        JsonPath js = new JsonPath(payload);
        int count = js.getInt("Employees.size()");
        System.out.println(count);
        //print all employee IDs
//        for (int i=0; i<count; i++){
//            String allEmployeeIDs = js.getString("Employees["+i+"].employee_id");
//            System.out.println(allEmployeeIDs);
//        }

        String response_string = response.asString();
        JsonObject response_getallEmployees = JsonParser.parseString(response_string).getAsJsonObject();
    }
    //====================retrieving all employees status==============================

    @Given("a request is prepared to get all employee status")
    public void a_request_is_prepared_to_get_all_employee_status() {
        request=given().header(apiConstants.Header_content_type, apiConstants.Content_type)
                .header(apiConstants.Header_authorization, generateTokenSteps.token);
    }

    @When("a GET call is made to retrieve the status of all employees")
    public void a_GET_call_is_made_to_retrieve_the_status_of_all_employees() {
        response=request.when().get(apiConstants.GET_EMPLOYEE_STATUS_URI);
    }

    @Then("it contains the value1 {string} and value2 {string}")
    public void it_contains_the_value1_and_value2(String value1, String value2) {
       response.then().assertThat().body(containsString(value1)).body(containsString(value2));
    }


}
