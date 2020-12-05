package steps;

import static utils.DataSupplier.*;

public class Triangle extends BasePojo {

    public BasePojo getDefaultTriangle() {
        jsonBody.clear();
        return this
                .withAdditionalProperty("separator", ";")
                .withAdditionalProperty("input", getSideSize());
    }

    public BasePojo getNegativeTriangle() {
        jsonBody.clear();
        return this
                .withAdditionalProperty("separator", ";")
                .withAdditionalProperty("input", getNegativeSize());
    }

    public BasePojo getDiffTriangle(String separator, String input) {
        jsonBody.clear();
        return this
                .withAdditionalProperty("separator", separator)
                .withAdditionalProperty("input", input);
    }

    public BasePojo getTriangleWithUnusualSeparator() {
        jsonBody.clear();
        return this
                .withAdditionalProperty("separator", getSeparator())
                .withAdditionalProperty("input", getSideSize());
    }

    public BasePojo getTriangleWithoutSeparator() {
        jsonBody.clear();
        return this
                .withAdditionalProperty("input", getSideSize());
    }

    public BasePojo getTriangleWithoutInput() {
        jsonBody.clear();
        return this
                .withAdditionalProperty("separator", ";");
    }
}

