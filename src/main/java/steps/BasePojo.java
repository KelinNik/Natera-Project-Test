package steps;

import java.util.HashMap;
import java.util.Map;

public abstract class BasePojo {

    protected Map<String, Object> jsonBody = new HashMap<>();

    public Map<String, Object> getJsonBody() {
        return jsonBody;
    }

    public BasePojo withAdditionalProperty(String property, String value) {
        jsonBody.put(property, value);
        return this;
    }

    public BasePojo without(String property) {
        jsonBody.remove(property);
        return this;
    }
}
