package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StoresAssertionTest  extends TestBase {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        response = given()
                .when()
                .get("/stores")
                .then().statusCode(200);
    }

    // 1. Verify the if the total is equal to 1561
    @Test
    public void test01(){
        response.body("total", equalTo(1561));
    }

    // 2. Verify the if the stores of limit is equal to 10
    @Test
    public void test02(){
        response.body("limit",equalTo(10));
    }

    // 3. Check the single ‘Name’ in the Array list (Inver Grove Heights)
    @Test
    public void test03(){
        response.body("data[1].name", equalTo("Inver Grove Heights"));
    }

    // 4. Check the multiple ‘Names’ in the ArrayList (Roseville, Burnsville, Maplewood)
    @Test
    public void test04(){
        response.body("data.name", hasItems("Roseville", "Burnsville", "Maplewood"));
    }


    // 5. Verify the storied=7 inside store services of the third store of second services
    @Test
    public void test05(){
        response.body("data[2].services[1].storeservices.storeId", equalTo(7));
    }

    // 6. Check hash map values ‘createdAt’ inside store services map where store name = Roseville
    @Test
    public void test06(){
        response.body("data.find { it.name == 'Roseville' }.services.storeservices.createdAt", notNullValue());
    }

    // 7. Verify the state = MN of forth store
    @Test
    public void test07(){
        response.body("data[3].state", equalTo("MN"));
    }

    // 8. Verify the store name = Rochester of 9th store
    @Test
    public void test08(){
        response.body("data[8].name", equalTo("Rochester"));
    }

    // 9. Verify the storeId = 11 for the 6th store
    @Test
    public void test09(){
        response.body("data[5].id", equalTo(11));
    }

    // 10. Verify the serviceId = 4 for the 7th store of forth service
    @Test
    public void test010(){
        response.body("data[6].services[3].storeservices.serviceId", equalTo(4));
    }
}
