package requests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.UserBody;
import pojos.UserBodyResponse;
import utilities.ObjectMapperUtils;
import utilities.TestBaseReport;

import static baseUrl.PetStoreBaseUrl.setUp;
import static baseUrl.PetStoreBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CRUDRequests extends TestBaseReport {

    Long id;
    @Test (priority = 1)
    public void postRequest() {

        extentTest=extentReports.createTest("Post Request",
                "Yeni bir kullanici olusturma");

        extentTest.info("kullanici endpointi set eder");
        setUp();
        spec.pathParam("first", "user");

        extentTest.info("kullanici post edilecek datayı set eder");
        UserBody postData=new UserBody("Emre",
                "Emre",
                "Turker",
                "emreturker@gmail.com",
                "123456", "+905551112233", 200);

        extentTest.info("kullanici post request gonderir ve gelen bilgileri kaydeder");
        Response response=given().spec(spec).
                contentType(ContentType.JSON).
                body(postData).when().post("/{first}");

        extentTest.info("kullanici status kodun 200 oldugunu dogrular");
        assertEquals(200, response.getStatusCode());
    }

    @Test(priority = 2, dependsOnMethods = "postRequest")
    public void getRequestAfterCreate() {
        extentTest=extentReports.createTest("Get Request-1",
                "Yeni bir kullanici olusturduktan sonra bilgileri getirme");

        extentTest.info("kullanici endpointi set eder");
        setUp();
        spec.pathParams("first", "user", "second", "Emre");

        extentTest.info("kullanici expected datayı set eder");
        UserBody expectedData=new UserBody("Emre",
                "Emre",
                "Turker",
                "emreturker@gmail.com",
                "123456", "+905551112233", 200);

        extentTest.info("kullanici get request gonderir ve gelen bilgileri kaydeder");
        Response response=given().spec(spec).when().get("/{first}/{second}");


        extentTest.info("kullanici gelen datayı dogrulamak icin object mapper kullanir ");
        UserBodyResponse actualData=ObjectMapperUtils.convertJsonToJava(response.asString(), UserBodyResponse.class);
        id=actualData.getId();

        extentTest.info("kullanici status kodun 200 oldugunu dogrular");
        assertEquals(200, response.getStatusCode());

        extentTest.info("kullanici gelen data ile expected datayı karsılastırıp dogrular ");
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

        extentTest=extentReports.createTest("Put Request",
                "Yeni bir kullanici olusturduktan sonra bilgilerin tamamini gunceller");

        extentTest.info("kullanici endpointi set eder");
        setUp();
        spec.pathParams("first", "user", "second", "Emre");

        extentTest.info("kullanici guncellenecek datayı set eder");
        UserBody putData=new UserBody("Ahmet Can",
                "Ahmet Can",
                "Aran",
                "ahmetcan@gmail.com",
                "654321", "+905302223355", 201);

        extentTest.info("kullanici put request gonderir ve gelen bilgileri kaydeder");
        Response response=given().spec(spec).
                contentType(ContentType.JSON).
                body(putData).when().put("/{first}/{second}");


        extentTest.info("kullanici status kodun 200 oldugunu dogrular");
        assertEquals(200, response.getStatusCode());


    }

    @Test (priority = 4, dependsOnMethods = "putRequest")
    public void getRequestAfterUpdate(){

        extentTest=extentReports.createTest("Get Request-2",
                "Kullanici guncellendikten sonra bigilerin getirilmesi");

        extentTest.info("kullanici endpointi set eder");
        setUp();
        spec.pathParams("first", "user", "second", "Ahmet Can");

        extentTest.info("kullanici expected datayı set eder");
        UserBodyResponse expectedData=new UserBodyResponse(id,"Ahmet Can",
                "Ahmet Can",
                "Aran",
                "ahmetcan@gmail.com",
                "654321", "+905302223355", 201);

        extentTest.info("kullanici get request gonderir ve gelen bilgileri kaydeder");
        Response response=given().spec(spec).when().get("/{first}/{second}");


        extentTest.info("kullanici gelen datayı dogrulamak icin pojo class kullanir ");
        UserBodyResponse actualData=response.as(UserBodyResponse.class);

        extentTest.info("kullanici status kodun 200 oldugunu dogrular");
        assertEquals(200, response.getStatusCode());

        extentTest.info("kullanici gelen data ile expected datayı karsılastırıp dogrular");
        assertEquals(expectedData.getUsername(), actualData.getUsername());
        assertEquals(expectedData.getFirstName(), actualData.getFirstName());
        assertEquals(expectedData.getLastName(), actualData.getLastName());
        assertEquals(expectedData.getEmail(), actualData.getEmail());
        assertEquals(expectedData.getPassword(), actualData.getPassword());
        assertEquals(expectedData.getPhone(), actualData.getPhone());
        assertEquals(expectedData.getUserStatus(), actualData.getUserStatus());

    }



    @Test (priority = 5, dependsOnMethods = "putRequest")
    public void deleteRequest(){

        extentTest=extentReports.createTest("Delete Request",
                "Kullanici tamamen silinir");

        extentTest.info("kullanici endpointi set eder");
        setUp();
        spec.pathParams("first", "user", "second", "Ahmet Can");

        extentTest.info("kullanici delete request gonderir ve gelen bilgileri kaydeder");
        Response response=given().spec(spec).when().delete("/{first}/{second}");

        extentTest.info("kullanici status kodun 200 oldugunu dogrular");
        assertEquals(200, response.getStatusCode());
    }
}
