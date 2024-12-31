package com.bestbuy.crudtest;

import com.bestbuy.constant.EndPoints;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class StoreCRUDTest extends TestBase {

    static int storeId = 0;

    static String name = "Krishna" + TestUtils.getRandomValue();
    static String type = "Bigbas";
    static String address = "moot court";
    static String address2 = "";
    static String city = "london";
    static String state = "uk";
    static String zip = "687656";


    @Test
    public void createStore() {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);


        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(storePojo)
                .post(EndPoints.GET_STORES)
                .then().log().ifValidationFails().statusCode(201);

        storeId = response.extract().path("id");
        System.out.println("store id is : " + storeId);

    }

    @Test
    public void readStoreDataById() {

        ValidatableResponse response = given().log().ifValidationFails()
                .pathParam("StoreId", storeId)
                .when()
                .get(EndPoints.GET_STORES_BY_ID)
                .then().log().ifValidationFails().statusCode(200);

        storeId = response.extract().path("id");
        System.out.println("store id is : " + storeId);

    }

    @Test
    public void UpdateStoreDataById() {

        StorePojo storePojo = new StorePojo();
        storePojo.setName(name + "UpdatedName" + TestUtils.getRandomValue());
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);


        ValidatableResponse response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .pathParam("StoreId", storeId)
                .when()
                .body(storePojo)
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then().log().ifValidationFails().statusCode(200);
    }

    @Test
    public void test004_deleteStore() {
        int storeId = 8924;
        given().log().ifValidationFails()
                .pathParam("StoreId", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then().log().all()
                .statusCode(200);

        given()
                .log()
                .ifValidationFails()
                .pathParam("StoreId", storeId)
                .when()
                .get(EndPoints.GET_STORES_BY_ID)
                .then().log().ifValidationFails().statusCode(404);


    }

}

