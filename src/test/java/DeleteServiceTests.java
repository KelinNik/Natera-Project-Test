import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import steps.Steps;
import steps.Triangle;

import java.util.List;

import static utils.Specification.getUniversalSuccessfulSpec;

public class DeleteServiceTests extends ServiceTests {

    private static final String BASE_URL = "https://qa-quiz.natera.com/triangle/";
    private static final String ALL = "/all";

    Triangle triangle = new Triangle();

    private Steps step = new Steps(BASE_URL);
    private String newAddedId;
    private String first;
    private String second;
    private String third;

    @Test
    public void checkCreatingTriangle10Times() {
        cleanAllSavedData(step.httpGet(ALL));
        for (int i = 0; i < 10; i++) {
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
    }

    @Test(dependsOnMethods = "checkCreatingTriangle10Times", enabled = true)
    public void checkDelete10Times() {
        List<String> allId = getAllId(step.httpGet(ALL));
        for (String id : allId) {
            step.httpDelete(id);
        }
        SoftAssertions softly = new SoftAssertions();
        List<String> someId = getAllId(step.httpGet(ALL));
        softly.assertThat(someId.size()).isEqualTo(0);
        softly.assertAll();
    }

    private void cleanAllSavedData(Response response) {
        List<String> allId = getAllId(response);
        for (String id : allId) {
            step.httpDelete(id);
        }
    }
}
