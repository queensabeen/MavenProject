package com.hrms.pages;

import com.hrms.utils.CommonMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PIMConfigurationPage extends CommonMethods {

    @FindBy(id = "menu_pim_Configuration")
    public WebElement configurationDropDown;

    @FindBy(id = "menu_pim_Configuration")
    public WebElement optionalFields;

    @FindBy(xpath = "//input[@value = 'Edit']")
    public WebElement editButton;

    @FindBy(xpath = "//li[@class = 'checkbox']")
    public List<WebElement> checkBoxes;

    public void clickOnConfigDropDown(){
        jSclick(configurationDropDown);
    }

    public void clickOnOptionalFields(){
        jSclick(optionalFields);
    }

    public PIMConfigurationPage(){
        PageFactory.initElements(driver, this);
    }

    public void clickOnEditButton(){
        jSclick(editButton);
    }

}
