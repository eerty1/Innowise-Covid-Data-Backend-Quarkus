package com.innowise;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class CovidDataResourceTest {
    //It is not recommended to run all the tests simultaneously, since the API could block the request and require subscription
    @Inject
    PreTestData preTestData;
    @ConfigProperty(name = "com.innowise.toomanyrequests.exception.message")
    String tooManyRequestsExceptionMessage;
    @ConfigProperty(name = "com.innowise.faultydata.exception.message")
    String faultyDataExceptionMessage;

    @Test
    public void testCovidDataGetEndpointSuccessful() {
        given()
                .when().get("/covid-data")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(248));
    }

    @Test
    public void testCovidDataPostEndpointSuccessful() throws JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(preTestData.postRequiredCountriesForSuccessfulRequest())
                .when().post("/covid-data")
                .then()
                .statusCode(200)
                .body("minCasesAmount", is(994037))
                .body("minCasesCountryName", is("Belarus"))
                .body("minCasesDate", is("2023-01-20T00:00:00Z"))
                .body("maxCasesAmount", is(21974098))
                .body("maxCasesCountryName", is("Russian Federation"))
                .body("maxCasesDate", is("2023-03-01T00:00:00Z"));
    }

    @Test
    public void testCovidDataPostEndpointFailedTooManyRequests() throws JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(preTestData.postRequiredCountriesForFailedRequestTooManyRequests())
                .when().post("/covid-data")
                .then()
                .statusCode(429)
                .body(is(tooManyRequestsExceptionMessage));
    }

    @Test
    public void testCovidDataPostEndpointFailedFaultyData() throws JsonProcessingException {
        given()
                .contentType(ContentType.JSON)
                .body(preTestData.postRequiredCountriesForFailedRequestFaultyData())
                .when().post("/covid-data")
                .then()
                .statusCode(500)
                .body(is(faultyDataExceptionMessage));
    }
}
