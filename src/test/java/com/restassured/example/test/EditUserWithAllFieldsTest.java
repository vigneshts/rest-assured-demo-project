package com.restassured.example.test;

import com.github.javafaker.Faker;
import com.restassured.example.Constants;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.restassured.example.Constants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

/**
 * Project Name    : rest-assured-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 8/11/2021
 * Time            : 1:20 PM
 * Description     :
 **/

public class EditUserWithAllFieldsTest extends BaseTest {

    private int userId;

    @BeforeMethod
    public void before() {
        userId = createNewUserAndReturnUserId();
    }

    @Test(description = "Verify that a user can be edited with all fields")
    public void testUserModificationForAllFields() {
        Faker faker = new Faker();
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String fullName = firstName + " " + lastName;
        String email = firstName + lastName + Constants.FAKE_EMAIL_DOMAIN;
        String gender = "male";
        String status = "active";

        JSONObject userRequestJson = new JSONObject();
        userRequestJson.put("email", email);
        userRequestJson.put("name", fullName);
        userRequestJson.put("gender", gender);
        userRequestJson.put("status", status);

        given()
                .request()
                .spec(requestSpec)
                .body(userRequestJson.toString()).log().all()
                .pathParam(USER_PATH_PARAM_NAME, userId)
                .put(GET_USER_ENDPOINT)
                .then().log().all()
                .statusCode(SC_OK)
                .body("data.name", equalTo(fullName))
                .body("data.email", equalTo(email))
                .body("data.gender", equalTo(gender))
                .body("data.status", equalTo(status));
    }
}
