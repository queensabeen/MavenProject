package com.hrms.utils;

import com.hrms.api.generateTokenSteps;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class apiCommonMethods {

    public static RequestSpecification createEmployeeRequest(String token, String Employee_body){

        return given().header(apiConstants.Header_content_type, "application.json")
                .header(apiConstants.Header_authorization, token)
                .body(Employee_body);

    }

}
