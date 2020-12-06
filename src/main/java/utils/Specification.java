package utils;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

public class Specification {

    public static ResponseSpecification getUniversalSuccessfulSpec() {
        return new ResponseSpecBuilder()
                .expectBody("id", notNullValue())
                .expectBody("firstSide", notNullValue())
                .expectBody("secondSide", notNullValue())
                .expectBody("thirdSide", notNullValue())
                .build();
    }

    public static ResponseSpecification getUniversalUnsuccessfulSpec() {
        return new ResponseSpecBuilder()
                .expectBody(ERROR, notNullValue())
                .build();
    }
}
