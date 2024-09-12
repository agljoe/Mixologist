package persistence;

import java.io.*;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

// Represents an encoder that encodes model's data abstractions to JSON data, and writes it to a file
public class Encoder {

    private static final int TAB_SIZE = 4;
    private PrintWriter encoder;
    private String path;
    
    // EFFECTS: constructs an encoder to write JSON to path
    public Encoder(String path) {
        this.path = path;
    }   

    // REQUIRES: input is any Codable object
    // MODIFIES: this
    // EFFECTS: encodes and writes input to the file at path
    public void encode(Codable input) throws FileNotFoundException {
        encoder = new PrintWriter(new File(path));
        JSONObject json = input.asJson();

        encoder.print(json.toString(TAB_SIZE));
        encoder.close();
    }

    // MODIFIES: this
    // EFFECTS: encodes and writes a list of drinks to the file at path
    public void encodeList(List<Codable> input, String key) throws FileNotFoundException {
        encoder = new PrintWriter(new File(path));
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (Codable c : input) {
            jsonArray.put(c.asJson());
        }

        json.put(key, jsonArray);

        encoder.print(json.toString(TAB_SIZE));
        encoder.close();
    }
}
