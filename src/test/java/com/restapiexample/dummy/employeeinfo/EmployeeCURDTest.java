package com.restapiexample.dummy.employeeinfo;

import com.restapiexample.dummy.steps.EmployeeSteps;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasKey;

@RunWith(SerenityRunner.class)
public class EmployeeCURDTest {
    static String status = "success";
    static int employeeID;
    static String message;
    static String employeeName = "sheel";
    @Steps
    EmployeeSteps employeeSteps;

    @Title("This will create a new employee")
    @Test
    public void test001() {
        HashMap<String, Object> createRecord = new HashMap<>();
        createRecord.put("name", employeeName);
        createRecord.put("salary", "12500");
        createRecord.put("age", "65");
        createRecord.put("id", 104);

        ValidatableResponse response = EmployeeSteps.createUser(status, createRecord);
        response.log().all().statusCode(200);
        employeeID = response.log().all().extract().path("data.id");
        message = response.log().all().extract().path("message");
        Assert.assertThat(message, anything("Successfully!"));
        System.out.println(employeeID + " is added " + message);
    }

    @Title("This will Read an employee by ID")
    @Test
    public void test002() {
        ValidatableResponse response = EmployeeSteps.readingEmployee();
        response.log().all().statusCode(200);
        employeeID = response.log().all().extract().path("data.id");
        HashMap<?, ?> getEmployee = response.log().all().extract().path("");
        Assert.assertThat(getEmployee, hasKey("status"));
        Assert.assertThat(getEmployee, hasKey("data"));

        message = response.log().all().extract().path("message");
        Assert.assertThat(message, anything("Successfully!"));
    }

    @Title("This will Update an employee by ID")
    @Test
    public void test003() {
        HashMap<String, Object> createRecord = new HashMap<>();
        employeeName = employeeName + "_updated";
        createRecord.put("name", employeeName);
        ValidatableResponse response = employeeSteps.updatingEmployee(createRecord);
        response.log().all().statusCode(200);
        message = employeeSteps.readingEmployee().log().all().extract().path("message");
        System.out.println(message);
        Assert.assertThat(message, anything("Successfully!"));
    }

    @Title("This will Delete an employee by ID")
    @Test
    public void test004() {
        ValidatableResponse response = employeeSteps.deletingEmployee();
        response.log().all().statusCode(200);
        message = employeeSteps.readingEmployee().log().all().extract().path("message");
        System.out.println(message);
        Assert.assertThat(message, anything("Successfully!"));
    }

    @Title("1. Verify if total records are 24")
    @Test
    public void test005() {
        List<String> totalRecord = employeeSteps.readingAllEmployee().log().all().extract().path("data");
        Assert.assertEquals(24,totalRecord.size());
    }

    @Title("2. Verify if 24th Data id is 24")
    @Test
    public void test006() {
        int iD24 = employeeSteps.readingAllEmployee().log().all().extract().path("data[23].id");
        Assert.assertEquals(iD24, 24);
    }

    @Title("3. Verify if 23rd employee name is Doris Wilder")
    @Test
    public void test007() {
        String employeeName = employeeSteps.readingAllEmployee().log().all().extract().path("data[23].employee_name");
        Assert.assertEquals(employeeName, "Doris Wilder");
    }

    @Title("4. Verify if message is Successfully! All records has been fetched")
    @Test
    public void test008() {
        String message = employeeSteps.readingAllEmployee().log().all().extract().path("message");
        Assert.assertEquals(message, "Successfully! All records has been fetched.");
    }

    @Title("5. Verify if status is success")
    @Test
    public void test009() {
        String status = employeeSteps.readingAllEmployee().log().all().extract().path("status");
        Assert.assertEquals(status, "success");
    }

    @Title("6. Verify 3rd id salary is 86000")
    @Test
    public void test010() {
        List<?> employeeSalary = employeeSteps.readingAllEmployee().log().all().extract().path("data.findAll{it.id==3}.employee_salary");
        Assert.assertEquals(employeeSalary.get(0), 86000);
    }

    @Title("7. Verify 6th id age is 61")
    @Test
    public void test011() {
        List<?> employeeAge = employeeSteps.readingAllEmployee().log().all().extract().path("data.findAll{it.id==6}.employee_age");
        Assert.assertEquals(employeeAge.get(0), 61);
    }

    @Title("8. Verify 11th id name is Jena Gaines")
    @Test
    public void test012() {
        List<?> employeeName1 = employeeSteps.readingAllEmployee().log().all().extract().path("data.findAll{it.id==11}.employee_name");
        Assert.assertEquals(employeeName1.get(0), "Jena Gaines");
    }


}

