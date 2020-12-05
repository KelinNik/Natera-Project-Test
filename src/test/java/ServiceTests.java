
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

public abstract class ServiceTests {

    static {
        RestAssured.useRelaxedHTTPSValidation();
    }

    String getValueByJsonPath(Response response, String path) {
        return response.jsonPath().get(path).toString();
    }

    List<String> getValuesByJsonPath(Response response, String path) {
        return response.jsonPath().getList(path);
    }

    protected boolean isTriangle(String first, String second, String third) {
        double a = new Double(first);
        double b = new Double(second);
        double c = new Double(third);
        return ((a < b + c) & (b < a + c) & (c < a + b));
    }

    protected List<String> getAllId(Response response) {
        return getValuesByJsonPath(response, "id");
    }

    protected String calcPerimeter(String first, String second, String third) {
        double a = new Double(first);
        double b = new Double(second);
        double c = new Double(third);
        return String.valueOf(a + b + c);
    }

    protected String calcArea(String first, String second, String third) {
        double a = new Double(first);
        double b = new Double(second);
        double c = new Double(third);
        double halfPer = (a+b+c)/2;
        return String.valueOf(Math.sqrt(halfPer*(halfPer-a)*(halfPer-b)*(halfPer-c)));
    }
}
