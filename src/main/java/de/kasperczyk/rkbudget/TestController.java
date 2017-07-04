package de.kasperczyk.rkbudget;

import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    private String testValue = "Hello World!";

    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(String testValue) {
        this.testValue = testValue;
    }
}
