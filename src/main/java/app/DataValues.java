package app;

import java.util.Map;

import org.json.JSONObject;

public class DataValues {
    public static Map<String, String> map = Map.of("name", "anders");

    public static String toJson() {
        return new JSONObject(map).toString();
    }
}
