package week2;

import io.restassured.*;
import io.restassured.http.*;
import io.restassured.path.json.*;
import io.restassured.response.*;
import org.junit.jupiter.api.*;

import java.util.*;

public class JsonPathMethod {

    @BeforeAll
    public static void init(){
        RestAssured.baseURI = "http://54.152.219.47:8000";
    }

    @Test
    public void test1(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("/api/spartans/1");

        JsonPath jsonPath = response.jsonPath();

        int id = jsonPath.getInt("id");
        System.out.println("id = " + id);

        String name = jsonPath.getString("name");
        System.out.println("name = " + name);
    }

    @Test
    public void test2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        JsonPath jsonPath = response.jsonPath();

        List<String> names = jsonPath.getList("name");
        System.out.println("names = " + names);

        List<Long> phones = jsonPath.getList("phone");
        System.out.println("phones = " + phones);
    }
}
