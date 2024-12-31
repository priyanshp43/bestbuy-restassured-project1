package com.bestbuy.testsuite;

import com.bestbuy.testbase.TestBase;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ProductAssertionTest extends TestBase {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3030;
        response = given()
                .when()
                .get("/products")
                .then().statusCode(200);
    }

    // 11) Verify that the products of total is = 51957
    @Test
    public void test01() {
        response.body("total", equalTo(51956));
    }

    // 12. Verify if the stores of limit is equal to 10
    @Test
    public void tes02() {
        response.body("limit", equalTo(10));
    }


    // 13. Check the single ‘Name’ in the Array list (Duracell - AAA Batteries (4-Pack))
    @Test
    public void test03() {
        response.body("data.name", hasItem("Duracell - AA 1.5V CopperTop Batteries (4-Pack)"));
    }

    //14. Check the multiple ‘Names’ in the ArrayList (Duracell - AA 1.5V CopperTop Batteries (4-Pack), Duracell - AA Batteries (8-Pack), Energizer - MAX Batteries AA (4-Pack))
    @Test
    public void test04() {
        response.body("data.name", hasItems("Duracell - AA 1.5V CopperTop Batteries (4-Pack)", "Duracell - AA Batteries (8-Pack)", "Energizer - MAX Batteries AA (4-Pack)"));
    }

    //  15. Verify the productId=150115 inside categories of the third name is “Household Batteries”
    @Test
    public void test05() {
        // response.body("data[2].categories[2].name", hasItem("Household Batteries"));
        response.body("data[2].categories[2].name", equalTo("Household Batteries"));
    }


    // 16. Verify the categories second name = “Housewares” of productID = 333179
    @Test
    public void test06() {
        response.body("data[7].categories[1].name", equalTo("Housewares"));

    }

    //17. Verify the price = 4.99 of forth product
    @Test
    public void test07() {
    //response.body("data[2].price", hasItem(4.99));
        response.body("data[2].price",equalTo(4.99F));

    }


    //18. Verify the Product name = Duracell - D Batteries (4-Pack) of 6th product
    @Test
    public void test08() {
        response.body("data[4].name", equalTo("Duracell - D Batteries (4-Pack)"));
    }


    //19. Verify the ProductId = 333179 for the 9th product
    @Test
    public void test09() {
        response.body("data[7].id", equalTo(333179));
    }

    //20. Verify the prodctId = 346575 have 5 categories
    @Test
    public void test10() {
        response.body("data[8].categories.size()", equalTo(5));
    }
}
