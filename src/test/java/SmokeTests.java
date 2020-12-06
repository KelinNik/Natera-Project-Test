import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import steps.Steps;
import steps.Triangle;

import java.util.List;

import static utils.Specification.getUniversalSuccessfulSpec;

@Test(suiteName = "SmokeTests")
public class SmokeTests extends ServiceTests {

    private static final String BASE_URL = "https://qa-quiz.natera.com/triangle/";
    private static final String ALL = "/all";
    private static final String PERIMETER = "/perimeter";
    private static final String AREA = "/area";

    Triangle triangle = new Triangle();

    private Steps step = new Steps(BASE_URL);
    private String newAddedId;
    private String first;
    private String second;
    private String third;


    @Description(value = "Check the main function of creating triangle")
    @Test
    public void checkCreatingTriangle() {
        cleanAllSavedData(step.httpGet(ALL));
        Response response = step.httpPost("",
                triangle.getDefaultTriangle().getJsonBody(),
                getUniversalSuccessfulSpec());
        SoftAssertions softly = new SoftAssertions();
        newAddedId = getValueByJsonPath(response, "id");
        first = getValueByJsonPath(response, "firstSide");
        second = getValueByJsonPath(response, "secondSide");
        third = getValueByJsonPath(response, "thirdSide");
        softly.assertThat(newAddedId).isNotBlank().isNotEmpty().isNotNull();
        softly.assertThat(isTriangle(first, second, third)).isTrue();
        softly.assertAll();
    }

    @Description(value = "Check function - getById")
    @Test(dependsOnMethods = "checkCreatingTriangle")
    public void checkGetById() {
        Response response = step.httpGet(newAddedId);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(newAddedId).isEqualTo(getValueByJsonPath(response, "id"));
        softly.assertThat(first).isEqualTo(getValueByJsonPath(response, "firstSide"));
        softly.assertThat(second).isEqualTo(getValueByJsonPath(response, "secondSide"));
        softly.assertThat(third).isEqualTo(getValueByJsonPath(response, "thirdSide"));
        softly.assertAll();
    }

    @Description(value = "Check function - delete")
    @Test(dependsOnMethods = "checkPerimeter", enabled = true)
    public void checkDelete() {
        step.httpDelete(newAddedId);
        SoftAssertions softly = new SoftAssertions();
        Response response = step.httpGet(newAddedId);
        softly.assertThat(getValueByJsonPath(response, "error")).isEqualTo("Not Found");
        softly.assertAll();
    }

    @Description(value = "Check getAll function")
    @Test(dependsOnMethods = "checkGetById")
    public void checkGetAll() {
        Response response = step.httpGet(ALL);
        SoftAssertions softly = new SoftAssertions();
        List<String> allId = getAllId(response);
        softly.assertThat(allId.size()).isEqualTo(1);
        softly.assertAll();
    }

    @Description(value = "Check getPerimeter function")
    @Test(dependsOnMethods = "checkGetById")
    public void checkPerimeter() {
        Response response = step.httpGet(newAddedId + PERIMETER);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getValueByJsonPath(response, "result")).isEqualTo(calcPerimeter(first, second, third));
        softly.assertAll();
    }

    @Description(value = "Check getArea function")
    @Test(dependsOnMethods = "checkGetById")
    public void checkArea() {
        Response response = step.httpGet(newAddedId + AREA);
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(calcArea(first, second, third)).containsSequence(getValueByJsonPath(response, "result"));
        softly.assertAll();
    }

    private void cleanAllSavedData(Response response) {
        List<String> allId = getAllId(response);
        for (String id : allId) {
            step.httpDelete(id);
        }
    }
}
