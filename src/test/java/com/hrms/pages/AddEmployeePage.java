package com.hrms.pages;

import com.hrms.utils.CommonMethods;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddEmployeePage extends CommonMethods {

    @FindBy(id = "firstName")
    public WebElement firstNameField;
    @FindBy(id = "middleName")
    public WebElement middleNameField;
    @FindBy(id = "lastName")
    public WebElement lastNameField;
    @FindBy(id = "employeeId")
    public WebElement empIDTextbox;
    @FindBy(id = "btnSave")
    public WebElement saveButton;
    @FindBy(id = "chkLogin")
    public WebElement createLoginCheckbox;
    @FindBy(id = "photofile")
    public WebElement photograph;
    @FindBy(id = "chkLogin")
    public WebElement createLoginDetails;
    @FindBy(id = "user_name")
    public WebElement usernameCreate;
    @FindBy(id = "user_password")
    public WebElement userPassword;
    @FindBy(id = "re_password")
    public WebElement rePassword;

    public void enterFirstAndLastName(String firstName, String lastName) {
        sendText(firstNameField, firstName);
        sendText(lastNameField, lastName);
    }

    public void enterFirstMiddleAndLastName(String firstName, String middleName, String lastName) {
        sendText(firstNameField, firstName);
        sendText(middleNameField, middleName);
        sendText(lastNameField, lastName);
    }

    public void enterEmployeeId(String employeeId) {
        sendText(empIDTextbox, employeeId);
    }

    public void clickOnSaveBtn() {
        jSclick(saveButton);
    }

    public AddEmployeePage() {
        PageFactory.initElements(driver, this);
    }


}
