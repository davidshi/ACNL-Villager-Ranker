/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package villagerrank.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author owner
 */
public class JSONUtil {

    private static final JSONParser parser = new JSONParser();

    public static JSONObject makeJSONObject(String s) throws ParseException {
        return (JSONObject) parser.parse(s);
    }

    public static JSONArray makeJSONArray(String s) throws ParseException {
        return (JSONArray) parser.parse(s);
    }

    public static String getJSONObjectString(JSONObject obj, String key) {
        return (String) obj.get(key);
    }

    public static int getJSONObjectInt(JSONObject obj, String key) {
        return (int) obj.get(key);
    }

    public static long getJSONObjectLong(JSONObject obj, String key) {
        return (long) obj.get(key);
    }

    public static boolean getJSONObjectBoolean(JSONObject obj, String key) {
        return (boolean) obj.get(key);
    }

    public static JSONObject getJSONObject(JSONObject obj, String key) {
        return (JSONObject) obj.get(key);
    }

    public static JSONArray getJSONArray(JSONObject obj, String key) {
        return (JSONArray) obj.get(key);
    }
}
