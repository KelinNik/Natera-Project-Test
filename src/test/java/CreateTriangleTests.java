import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.Test;
import steps.Steps;
import steps.Triangle;

import java.util.List;
import java.util.Map;

import static utils.DataSupplier.getSideSizeWithSeparator;
import static utils.Specification.getUniversalSuccessfulSpec;
import static utils.Specification.getUniversalUnsuccessfulSpec;

public class CreateTriangleTests extends ServiceTests {

    private static final String BASE_URL = "https://qa-quiz.natera.com/triangle/";
    private static final String ALL = "/all";

    Triangle triangle = new Triangle();

    private Steps step = new Steps(BASE_URL);
    private String newAddedId;
    private String first;
    private String second;
    private String third;


    @Description(value = "Check creating triangle without optional separator")
    @Test
    public void checkCreatingTriangleWithoutSeparator() {
        cleanAllSavedData(step.httpGet(ALL));
        Response response = step.httpPost("",
                triangle.getTriangleWithoutSeparator().getJsonBody(),
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

    @Description(value = "Check case when separator is different from other in input")
    @Test
    public void checkCreatingTriangleWithSeparatorDifferentFromAnotherInInput() {
        cleanAllSavedData(step.httpGet(ALL));
        Response response = step.httpPost("",
                triangle.getTriangleWithUnusualSeparator().getJsonBody(),
                getUniversalUnsuccessfulSpec());
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(getValueByJsonPath(response, "error")).isNotEmpty();
        softly.assertThat(getValueByJsonPath(response, "status")).isEqualTo(422);
        softly.assertAll();
    }

    @Description(value = "Check createTriangle without \"input\"")
    @Test
    public void checkCreatingTriangleWithoutInput() {
        cleanAllSavedData(step.httpGet(ALL));
        step.httpPost("", triangle.getTriangleWithoutInput().getJsonBody(),
                getUniversalUnsuccessfulSpec());
    }

    @Description(value = "Check check some possible separators")
    @Test
    public void checkVariousOfSeparators(){
        Map<String, String> sideSizeWithSeparator = getSideSizeWithSeparator("4", "5", "7");
        SoftAssertions softly = new SoftAssertions();
        for (String separator: sideSizeWithSeparator.keySet()) {
            cleanAllSavedData(step.httpGet(ALL));
            Response response = step.httpPost("", triangle.getDiffTriangle(separator, sideSizeWithSeparator.get(separator)).getJsonBody(),
                    getUniversalSuccessfulSpec());
            newAddedId = getValueByJsonPath(response, "id");
            first = getValueByJsonPath(response, "firstSide");
            second = getValueByJsonPath(response, "secondSide");
            third = getValueByJsonPath(response, "thirdSide");
            softly.assertThat(newAddedId).isNotBlank().isNotEmpty().isNotNull();
            softly.assertThat(isTriangle(first, second, third)).isTrue();
        }
        softly.assertAll();
    }

    @Description(value = "Check negative numbers in input field")
    @Test
    public void checkNegativeNumbersInInputField(){
        SoftAssertions softly = new SoftAssertions();
        Response response = step.httpPost("", triangle.getNegativeTriangle().getJsonBody(),
                getUniversalUnsuccessfulSpec());
        softly.assertThat(getValueByJsonPath(response, "error")).isNotEmpty();
        softly.assertAll();
    }


    private void cleanAllSavedData(Response response) {
        List<String> allId = getAllId(response);
        for (String id : allId) {
            step.httpDelete(id);
        }
    }
}
