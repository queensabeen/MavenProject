package com.hrms.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.MatcherAssert.assertThat;

//given()
//when()
//then()

public class hardCodedExamples {
    String baseURI = RestAssured.baseURI = "http://3.237.189.167/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTQzMDkzODgsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYxNDM1MjU4OCwidXNlcklkIjoiMjQwMyJ9.KYmciTLAbNGxzExD_EfelIXVOdERXIl0gveZMTnQjPg";

    @Test
    public void sampleTest() {
//        RestAssured.baseURI = "http://3.237.189.167/syntaxapi/api";
//        String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2MTQyMjEzNDAsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTYxNDI2NDU0MCwidXNlcklkIjoiMjQwMyJ9.lr42CCE6eqSe03WxgsTzLZ-VyETLqr177qBk0IteNVQ";
//        //preparing request to get one employee
        RequestSpecification preparingGetOneEmployeeRequest = given().header("Authorization", token)
                .header("Content-Type", "Application/json")
                .queryParam("employee_id", "13001");
        //sending the request to the endpoint
        Response getOneEmployeeRespnse = preparingGetOneEmployeeRequest.when().get("/getOneEmployee.php");
//print the response
        System.out.println(getOneEmployeeRespnse.asString());
        //getOneEmployeeRespnse.prettyPrint(); another way

        //assert that status code is 200
        getOneEmployeeRespnse.then().assertThat().statusCode(200);
    }

    @Test
    public void aPostCreateEmployee() {
//preparing create an employee request
        RequestSpecification createEmployeeRequest = given().header("Authorization", token)
                .header("Content-Type", "Application/json").body(
                        "{\n+" +
                                "\"emp_firstname\": \"sabeen\",\n" +
                                "\"emp_lastname\": \"sadi\",\n" +
                                "\"emp_middle_name\": \"none\",\n" +
                                "\"emp_gender\": \"F\",\n" +
                                "\"emp_birthday\": \"1989-02-17\",\n" +
                                "\"emp_status\": \"Freelance\",\n" +
                                "\"emp_job_title\": \"Developer\"\n" +
                                "}");

        //Making a post call to create employee
        Response createEmployeeResponse=createEmployeeRequest.when().post("/createEmployee");
        createEmployeeResponse.prettyPrint();
        //assert that status code is 201
        createEmployeeResponse.then().assertThat().statusCode(201);
        //Get employee ID
        String employeeID=createEmployeeResponse.jsonPath().getString("Employee[0].employee_id");
        //print employee ID
        System.out.println(employeeID);
        //assert that message contains Entry created
        createEmployeeResponse.then().assertThat().body("Message", equalTo("Entry Created"));

        //assert that employee id is 16083A
        createEmployeeResponse.then().assertThat().body("Employee[0].emp_firstname", equalTo("sabeen"));

    }

    @Test
    public void bGetCreatedEmployee(){

        RequestSpecification getCreatedEmployee = given().header("Authorization", token)
                .header("Content-Type", "Application/json").queryParam("employee_id", "13001");

        Response getEmployeeResponse = getCreatedEmployee.when().get("/getOneEmployee.php");

        //getEmployeeResponse.prettyPrint();
        String empID = getEmployeeResponse.body().jsonPath().getString("employee[0].employee_id");
//checking if the empID is equal to the one mentioned in the string
        boolean VerifyEmployeeID = empID.equalsIgnoreCase("13001");
        System.out.println(VerifyEmployeeID);

        //Assert.assertTrue(VerifyEmployeeID);

        getEmployeeResponse.then().assertThat().body("employee[0].employee_id", equalTo("13001"));
    }

    @Test
    public void cUpdateEmployee(){
        RequestSpecification updateEmployeeRequest = given().header("Authorization", token)
                .header("Content-Type", "Application/json").body("{\n+" +
                        "\"employee_id\": \"14092A\",\n" +
                        "\"emp_firstname\": \"sab\",\n" +
                        "\"emp_lastname\": \"sadi\",\n" +
                        "\"emp_middle_name\": \"non\",\n" +
                        "\"emp_gender\": \"M\",\n" +
                        "\"emp_birthday\": \"1980-02-17\",\n" +
                        "\"emp_status\": \"Moneer\",\n" +
                        "\"emp_job_title\": \"Accountant\"\n" +
                        "}");
        Response UpdateEmployeeResponse = updateEmployeeRequest.when().put("/updateEmployee.php");
        UpdateEmployeeResponse.prettyPrint();

        //assert that updated information is correct
        JsonPath js=UpdateEmployeeResponse.jsonPath();
        String employee_firstname = js.get("employee[0].emp_firstname");
        System.out.println(employee_firstname);

        assertThat(employee_firstname,equalTo("sab"));

        //in another way
        UpdateEmployeeResponse.then().assertThat().body("employee[0].firstname",equalTo("sab"));
    };

    @Test
    public void dPartiallyUpdateEmployee(){
        RequestSpecification partiallyUpdateRequest = given().header("Authorization", token)
                .header("Content-Type", "Application/json").body("{\n+" +
                        "\"employee_id\": \"14092A\",\n" +
                        "\"emp_firstname\": \"saber\",\n" +
                        "}");
        Response updatedEmployeeResponse = partiallyUpdateRequest.when().patch("/updatePartialEmployeeDetails.php");
        updatedEmployeeResponse.prettyPrint();

        //assert that body contains message entry updated
        JsonPath js = updatedEmployeeResponse.jsonPath();
        Object message = js.get("Message");
        System.out.println(message);

        assertThat(message,equalTo("Entry updated"));

        //another method
        updatedEmployeeResponse.then().body("Message", containsString("Entry updated"));
    }

    @Test
    public void dDeleteEmployeeRequest(){
        RequestSpecification deleteEmployeeRequest = given().header("Authorization", token)
                .header("Content-Type", "Application/json").queryParam("employee_id", "13001");

        Response deleteRequestReponse = deleteEmployeeRequest.when().delete("/deleteEmployee.php");

        deleteRequestReponse.prettyPrint();


    }


}
