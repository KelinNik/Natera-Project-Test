package steps;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class Steps {

    private static final String APPLICATION_JSON = "application/json";
    private static final String TOKEN = "a411be34-4b03-45a3-96d3-4bf1521b9e1f";

    private final String baseUrl;

    public Steps(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    private RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Content-Type", "application/json;charset=utf-8")
                .build();
    }

    @Step("POST-request on URL: {url}")
    public Response httpPost(String url, Object body, ResponseSpecification spec) {
        Response response = given()
                .spec(getRequestSpecification())
                .contentType(JSON)
                .header("X-User", TOKEN)
                .body(body)
                .log().all()
                .post(url)
                .then()
                .log()
                .all()
                .spec(spec)
                .extract()
                .response();
        addJsonBodyToReport(response);
        return response;
    }

    @Step("POST-request on URL: {url}")
    public Response httpPost(String url, Object body) {
        Response response = given()
                .spec(getRequestSpecification())
                .contentType(JSON)
                .header("X-User", TOKEN)
                .body(body)
                .log().all()
                .post(url)
                .then()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .response();
        addJsonBodyToReport(response);
        return response;
    }

    @Step("GET-request on URL: {url}")
    public Response httpGet(String url) {
        Response response = given()
                .spec(getRequestSpecification())
                .log().all()
                .header("X-User", TOKEN)
                .get(url)
                .then()
                .log().all()
                .extract().response();
        addJsonBodyToReport(response);
        return response;
    }

    @Step("GET-request on URL: {url}")
    public Response httpGet(String url, ResponseSpecification spec) {
        Response response = given()
                .spec(getRequestSpecification())
                .log().all()
                .header("X-User", TOKEN)
                .get(url)
                .then()
                .spec(spec)
                .statusCode(404)
                .log().all()
                .extract().response();
        addJsonBodyToReport(response);
        return response;
    }

    @Step("Delete-request on URL: {url}")
    public Response httpDelete(String url) {
        Response response = given()
                .spec(getRequestSpecification())
                .log().all()
                .header("X-User", TOKEN)
                .delete(url)
                .then()
                .log().all()
                .extract()
                .response();
        return response;
    }

    @Step("Getting response by JsonPath: {path}")
    private <T> T getValueFromResponse(Response response, String path) {
        Allure.addAttachment("Response body", APPLICATION_JSON, response.jsonPath().prettify());
        T actualValue = response.jsonPath().get(path);
        return actualValue;
    }

    @Step("Response body: ")
    public void addJsonBodyToReport(Response response) {
        Allure.addAttachment("Response body", APPLICATION_JSON, response.getBody().prettyPrint());
    }
}

