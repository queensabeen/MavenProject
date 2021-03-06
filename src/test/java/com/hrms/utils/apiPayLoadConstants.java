package com.hrms.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minidev.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class apiPayLoadConstants {
    public static String createEmployeeBody() {

//        JSONObject obj = new JSONObject();
//        obj.put("emp_firstname", "sabeen");
//        obj.put("emp_lastname", "sadi");
//        obj.put("emp_middle_name", "none");
//        obj.put("emp_gender", "F");
//        obj.put("emp_birthday", "1989-02-17");
//        obj.put("emp_status", "Freelance");
//        obj.put("emp_job_title", "Developer");
//
//        return obj.toString();

        File input = new File("C:/Users/FS/eclipse-workspace/CucumberFramework/src/test/resources/JsonData/createUser.json");
    JsonObject CreateUserData=null;
        try {
            //parsing the input file
            JsonElement fileElement = JsonParser.parseReader(new FileReader(input));
           CreateUserData = fileElement.getAsJsonObject();


//            JsonElement employee_birthday = CreateUserData.get("emp_birthday");
//            System.out.println("The employee's birthday is " +employee_birthday);


        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }
        return CreateUserData.toString();
    }


}
