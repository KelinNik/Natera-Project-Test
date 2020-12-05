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

    Triangle triangle = new Triangle();

    private Steps step = new Steps(BASE_URL);
    private String newAddedId;
    private String first;
    private String second;
    private String third;

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

    @Test(dependsOnMethods = "checkCreatingTriangle")
    public void checkGetByIdWithWrongId() {
        Response response = step.httpGet(newAddedId+"123", getUniversalUnsuccessfulSpec());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getValueByJsonPath(response, "error")).isEqualTo("Not Found");
        softly.assertThat(getValueByJsonPath(response, "message")).isEqualTo("Not Found");
        softly.assertAll();
    }

    private void cleanAllSavedData(Response response) {
        List<String> allId = getAllId(response);
        for (String id : allId) {
            step.httpDelete(id);
        }
    }
}
