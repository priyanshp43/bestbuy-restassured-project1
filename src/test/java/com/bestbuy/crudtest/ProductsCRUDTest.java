package com.bestbuy.crudtest;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.ProductPojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class ProductsCRUDTest extends TestBase {

    static int ProductId = 0;

    static String name = "Duracell Batteries" + TestUtils.getRandomValue();
    static String type = "Super";
    static double price = 6.87;
    static String upc = "07567657";
    static String manufacturer = "Duracell";
    static String description = "Compatible with select electronic devices";
    static String model = "MN2400KHG";



    @Test
    public void createProduct() {

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setManufacturer(manufacturer);
        productPojo.setDescription(description);
        productPojo.setModel(model);

        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .post(EndPoints.GET_PRODUCTS)
                .then().log().ifValidationFails().statusCode(201);

        ProductId = response.extract().path("id");
        System.out.println("product id is : " + ProductId);

    }

    @Test
    public void readProductDataById() {
        int ProductId = 9999683;

        ValidatableResponse response = given().log().ifValidationFails()
                .pathParam("ProductId", ProductId)
                .when()
                .get(EndPoints.GET_PRODUCTS_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

        ProductId = response.extract().path("id");
        System.out.println("product id is : " + ProductId);

    }


    @Test
    public void UpdateProductDataById() {
        int ProductId = 9999683;

        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name + "UpdatedName" + TestUtils.getRandomValue());
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setManufacturer(manufacturer);
        productPojo.setDescription(description);
        productPojo.setModel(model);


        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .pathParam("ProductId", ProductId)
                .when()
                .body(productPojo)
                .put(EndPoints.UPDATE_PRODUCTS_BY_ID)
                .then().log().ifValidationFails().statusCode(200);
    }

    @Test
    public void deleteProductById() {
        int ProductId = 9999683;

        given().log().ifValidationFails()
                .pathParam("ProductId", ProductId)
                .when()
                .delete(EndPoints.DELETE_PRODUCTS_BY_ID)
                .then()
                .statusCode(200);

        given()
                .log()
                .ifValidationFails()
                .pathParam("ProductId", ProductId)
                .when()
                .get(EndPoints.GET_PRODUCTS_BY_ID)
                .then().log().ifValidationFails().statusCode(404);
    }
}

