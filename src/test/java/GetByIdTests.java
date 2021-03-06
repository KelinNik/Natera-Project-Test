import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import steps.Steps;
import steps.Triangle;

import java.util.List;

import static utils.Specification.getUniversalSuccessfulSpec;
import static utils.Specification.getUniversalUnsuccessfulSpec;

public class GetByIdTests extends ServiceTests {

    private static final String BASE_URL = "https://qa-quiz.natera.com/triangle/";
    private static final String ALL = "/all";
    private String newAddedId;

    Triangle triangle = new Triangle();
    private Steps step = new Steps(BASE_URL);

    @Description(value = "Check the main function of creating triangle")
    @Test
    public void checkCreatingTriangle() {
        cleanAllSavedData(step.httpGet(ALL));
        Response response = step.httpPost("",
                triangle.getDefaultTriangle().getJsonBody(),
                getUniversalSuccessfulSpec());
        SoftAssertions softly = new SoftAssertions();
        newAddedId = getValueByJsonPath(response, ID);
        String first = getValueByJsonPath(response, FIRST);
        String second = getValueByJsonPath(response, SECOND);
        String third = getValueByJsonPath(response, THIRD);
        softly.assertThat(newAddedId).isNotBlank().isNotEmpty().isNotNull();
        softly.assertThat(isTriangle(first, second, third)).isTrue();
        softly.assertAll();
    }

    @Description(value = "Check getting triangle with wrong ID")
    @Test(dependsOnMethods = "checkCreatingTriangle")
    public void checkGetByIdWithWrongId() {
        Response response = step.httpGet(newAddedId+"123", getUniversalUnsuccessfulSpec());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getValueByJsonPath(response, ERROR)).isEqualTo(NOT_FOUND);
        softly.assertThat(getValueByJsonPath(response, "message")).isEqualTo(NOT_FOUND);
        softly.assertAll();
    }

    private void cleanAllSavedData(Response response) {
        List<String> allId = getAllId(response);
        for (String id : allId) {
            step.httpDelete(id);
        }
    }
}
