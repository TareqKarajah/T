package com.example.t;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;

public class chosenPlacesManager {
    private static final String PREFS_NAME = "ChosenPlacesPrefs";
    private static final String KEY_CHOSEN_PLACES = "ChosenPlaces";

    public static void savePlaces(Context context, ArrayList<JapanPlace> places) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(places);
        editor.putString(KEY_CHOSEN_PLACES, json);
        editor.apply();
    }

    public static ArrayList<JapanPlace> loadPlaces(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_CHOSEN_PLACES, null);

        if (json != null) {
            Gson gson = new Gson();
            JapanPlace[] placesArray = gson.fromJson(json, JapanPlace[].class);
            ArrayList<JapanPlace> placesList = new ArrayList<>();
            if (placesArray != null) {
                for (JapanPlace place : placesArray) {
                    placesList.add(place);
                }
            }
            return placesList;
        } else {
            return new ArrayList<>();
        }
    }


}
