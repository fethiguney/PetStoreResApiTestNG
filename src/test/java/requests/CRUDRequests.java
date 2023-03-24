package requests;

import baseUrl.PetStoreBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.UserBody;
import utilities.ObjectMapperUtils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class CRUDRequests extends PetStoreBaseUrl {

    @Test (priority = 1)
    public void postRequest() {

        //set the url
        spec.pathParam("first", "user");

        //set the expected data
        UserBody expectedData=new UserBody("Emre",
                "Emre",
                "Turker",
                "emreturker@gmail.com",
                "123456", "+905551112233", 200);

        //send the request and get the response
        Response response=given().spec(spec).
                contentType(ContentType.JSON).
                body(expectedData).when().post("/{first}");

        //do assertion

        assertEquals(200, response.getStatusCode());
    }

    @Test(priority = 2, dependsOnMethods = "postRequest")
    public void getRequest() {

        //set the url
        spec.pathParams("first", "user", "second", "Emre");

        //set the expected data
        UserBody expectedData=new UserBody("Emre",
                "Emre",
                "Turker",
                "emreturker@gmail.com",
                "123456", "+905551112233", 200);

        //send the request and get the response
        Response response=given().spec(spec).when().get("/{first}/{second}");

        //do assertion with object mapper
        UserBody actualData=ObjectMapperUtils.convertJsonToJava(response.asString(), UserBody.class);

        assertEquals(expectedData.getUsername(), actualData.getUsername());
        assertEquals(expectedData.getFirstName(), actualData.getFirstName());
        assertEquals(expectedData.getLastName(), actualData.getLastName());
        assertEquals(expectedData.getEmail(), actualData.getEmail());
        assertEquals(expectedData.getPassword(), actualData.getPassword());
        assertEquals(expectedData.getPhone(), actualData.getPhone());
        assertEquals(expectedData.getUserStatus(), actualData.getUserStatus());

    }

    @Test(priority = 3, dependsOnMethods = "postRequest")
    public void putRequest(){
        //set the url
        spec.pathParams("first", "user", "second", "Emre");

        //set the expected data
        UserBody expectedData=new UserBody("Ahmet Can",
                "Ahmet Can",
                "Aran",
                "ahmetcan@gmail.com",
                "654321", "+905302223355", 201);

        //send the request and get the response
        Response response=given().spec(spec).
                contentType(ContentType.JSON).
                body(expectedData).when().put("/{first}/{second}");


        //do assertion with pojo class
        UserBody actualData=response.as(UserBody.class);

        assertEquals(expectedData.getUsername(), actualData.getUsername());
        assertEquals(expectedData.getFirstName(), actualData.getFirstName());
        assertEquals(expectedData.getLastName(), actualData.getLastName());
        assertEquals(expectedData.getEmail(), actualData.getEmail());
        assertEquals(expectedData.getPassword(), actualData.getPassword());
        assertEquals(expectedData.getPhone(), actualData.getPhone());
        assertEquals(expectedData.getUserStatus(), actualData.getUserStatus());

    }
    @Test (priority = 4, dependsOnMethods = "postRequest")
    public void deleteRequest(){
        //set the url
        spec.pathParams("first", "user", "second", "Ahmet Can");
    }
}
