
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

public abstract class ServiceTests {

    public static final String FIRST = "firstSide";
    public static final String SECOND = "secondSide";
    public static final String THIRD = "thirdSide";
    public static final String ID = "id";
    public static final String ERROR = "error";
    public static final String RESULT = "result";
    public static final String NOT_FOUND = "Not Found";

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
        return getValuesByJsonPath(response, ID);
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
        double halfPer = (a + b + c) / 2;
        return String.valueOf(Math.sqrt(halfPer * (halfPer - a) * (halfPer - b) * (halfPer - c)));
    }
}
