package com.hrms.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",//need to give a path for feature class
                    glue = "com/hrms/stepDefinitions",//for glue, specify package - implement step definitions
                    dryRun = false, //when true, will run over the feature steps and reveal unimplemented steps in console
                    tags = {"@dashboardTabs"}, //adding tag
                    strict = false, //when true, will fail the execution when undefined step is found
                    plugin = {"pretty", //will print executed steps inside console
                            "html:target/cucumber-default-reports", //generates default html report
                            "rerun:target/FailedTests.txt", //generates txt file with failed tests only
                            "json:target/cucumber.json" //generates json report -> stores information
                                }
                        )
public class TestRunner {
}
