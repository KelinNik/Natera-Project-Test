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
                .expectBody("error", notNullValue())
                .build();
    }

    public static ResponseSpecification getUnsuccessfulErrorTextContainsSpec(String text) {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(false))
                .expectBody("error.text", containsString(text))
                .build();
    }

    public static ResponseSpecification getSectorRouteErrorTextContainsSpec(String text) {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(false))
                .expectBody("error", containsString(text))
                .build();
    }

    public static ResponseSpecification getErrorNumberSpec(String text) {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(false))
                .expectBody("error.code", containsString(text))
                .build();
    }

    public static ResponseSpecification fieldValidationErrorSpec(String text) {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(false))
                .expectBody("error.text", containsString(text))
                .expectBody("error.text", containsString("е заполнен"))
                .build();
    }

    public static ResponseSpecification shouldBeOneFromObligatoryFieldsErrorSpec() {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(false))
                .expectBody("error.text", containsString("Должно быть заполнено хотя бы одно из полей"))
                .build();
    }


    public static ResponseSpecification getDefaultTopologySpec() {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(true))
                .expectBody("body.currentComponent.nodePath", notNullValue())
                .build();
    }

    public static ResponseSpecification getRouteTokenSpec() {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(true))
                .expectBody("body.sub.subType", equalTo("...."))
                .expectBody("body.sub.userId.id", equalTo("...."))
                .expectBody("body.sub.userId.tb", equalTo("...."))
                .expectBody("body.jwt", equalTo("...."))
                .expectBody("body.srcDeploymentInfo", equalTo("...."))
                .expectBody("body.srcServiceCode", equalTo("...."))
                .expectBody("body.channel", equalTo("...."))
                .expectBody("body.routes.routeNodePath", equalTo("...."))
                .expectBody("body.routes.nativeNode.currentMode", equalTo("...."))
                .expectBody("body.routes.nativeNode.nodePath", equalTo("...."))
                .expectBody("body.routes.slaveNode.currentMode", equalTo("...."))
                .expectBody("body.routes.slaveNode.nodePath", equalTo("...."))
                .expectBody("body.routes.nativeNode.additionalNodeInfo", equalTo("...."))
                .expectBody("body.routes.slaveNode.additionalNodeInfo", equalTo("...."))
                .expectBody("body.routes.currentNodeType", equalTo("...."))
                .build();
    }

    public static ResponseSpecification getSessionSpec() {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(true))
                .expectBody("body.routes", notNullValue())
                .expectBody("body.jwt", equalTo("...."))
                .expectBody("body.envelope", equalTo("...."))
                .build();
    }

    public static ResponseSpecification getUpdateProfileSuccessSpec() {
        return new ResponseSpecBuilder()
                .expectBody("success", equalTo(true))
                .expectBody("$", not(hasKey("error")))
                .expectBody("$", not(hasKey("messages")))
                .expectBody("$", not(hasKey("context")))
                .build();
    }
}
