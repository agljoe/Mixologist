package persistence;

import org.json.JSONObject;

public interface Codable {

    // EFFECTS: returns this as a JSONObject
    JSONObject asJson();

}
