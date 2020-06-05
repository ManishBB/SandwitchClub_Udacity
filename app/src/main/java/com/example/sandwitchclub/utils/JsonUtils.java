package com.example.sandwitchclub.utils;

import com.example.sandwitchclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        String mainName = null;
        ArrayList<String> alsoKnownAs = null;
        String placeOfOrigin = null;
        String description = null;
        String image = null;
        ArrayList<String> ingredients = null;
        try {
            JSONObject sandwichJSONData = new JSONObject(json);
            mainName = sandwichJSONData.getJSONObject(KEY_NAME).getString(KEY_MAIN_NAME);
            JSONArray knownAsJSONData = sandwichJSONData.getJSONObject(KEY_NAME).getJSONArray(KEY_ALSO_KNOWN_AS);
            alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < knownAsJSONData.length(); i++) {
                alsoKnownAs.add(knownAsJSONData.getString(i));
            }
            placeOfOrigin = sandwichJSONData.getString(KEY_PLACE_OF_ORIGIN);
            description = sandwichJSONData.getString(KEY_DESCRIPTION);
            image = sandwichJSONData.getString(KEY_IMAGE);
            JSONArray ingredientsJSONData = sandwichJSONData.getJSONArray(KEY_INGREDIENTS);

            ingredients = new ArrayList<>();

            for (int i = 0; i < ingredientsJSONData.length(); i++) {
                ingredients.add(ingredientsJSONData.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
