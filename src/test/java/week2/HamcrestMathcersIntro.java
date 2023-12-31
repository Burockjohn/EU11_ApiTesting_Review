package week2;

import io.restassured.http.*;
import io.restassured.path.json.*;
import io.restassured.response.*;
import org.hamcrest.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class HamcrestMathcersIntro {

    @Test
    public void test1(){
        MatcherAssert.assertThat(5,is(5));
        MatcherAssert.assertThat(5,not(10));
        MatcherAssert.assertThat(5,is(not(10)));
        MatcherAssert.assertThat(5,equalTo(5));


        MatcherAssert.assertThat(10,notNullValue());

        String one = "one";
        String two = "two";

        MatcherAssert.assertThat(one,not(two));
        MatcherAssert.assertThat(one,not(equalTo(two)));
        MatcherAssert.assertThat(one,containsString("o"));

        Object obj = "hello";
        MatcherAssert.assertThat(obj,instanceOf(String.class));

        List<Integer> numbers = Arrays.asList(1,2,3,4,5);
        MatcherAssert.assertThat(numbers,everyItem(greaterThan(0)));
    }

    @Test
    @DisplayName("Hamcrest Matchers Api Testing")
    public void test2(){

        given().accept(ContentType.JSON)
                .when().get("http://54.152.219.47:8000/api/spartans/10")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("id",is(10)
                ,"name",is("Lorenza")
                ,"gender",is(not("Male"))
                ,"phone",equalTo(3312820936L));
    }

    @Test
    @DisplayName("Hamcrest Matchers Api Testing search spartan")
    public void test3(){
        Map<String,Object> queryParams = new HashMap<>();
        queryParams.put("nameContains","da");
        queryParams.put("gender","Male");

        given().accept(ContentType.JSON)
                .and().queryParams(queryParams)
                .when().get("http://54.152.219.47:8000/api/spartans/search")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("totalElement",is(2),
                        "content[0].name",equalTo("Adan"),
                        "content[1].phone",is(3154964396L),
                        "content.id",everyItem(notNullValue()));
    }

    @Test
    @DisplayName("get response after validation")
    public void test4(){
        Response response = given().accept(ContentType.JSON)
                .when().get("http://54.152.219.47:8000/api/spartans/10")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("id",is(10)
                        ,"name",is("Lorenza")
                        ,"gender",is(not("Male"))
                        ,"phone",equalTo(3312820936L))
                .extract().response();

        response.prettyPrint();
    }

    @Test
    @DisplayName("get list after validation")
    public void test5(){
        List<String> names = given().accept(ContentType.JSON)
                .when().get("http://54.152.219.47:8000/api/spartans")
                .then().statusCode(200)
                .and().body("id[1]",is(2))
                .extract().jsonPath().getList("name");

        System.out.println(names);
    }
}
