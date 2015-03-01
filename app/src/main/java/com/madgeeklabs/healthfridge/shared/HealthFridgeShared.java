package com.madgeeklabs.healthfridge.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by goofyahead on 9/10/14.
 */
public class HealthFridgeShared {

    private static final String MAX_CARBS = "MAX_CARBS";
    private static final String MAX_CALORIES = "MAX_CALORIES";
    private static final String MAX_FAT = "MAX_FAT";
    private static final String MAX_PROTEIN = "MAX_PROTEIN";
    private static final String MAX_CHOLESTEROL = "MAX_CHOLESTEROL";

    private final SharedPreferences prefs;

    public HealthFridgeShared(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setMaxCarbs(int max) {
        prefs.edit().putInt(MAX_CARBS, max).commit();
    }

    public int getMaxCarbs() {
        return prefs.getInt(MAX_CARBS, 500);
    }

    public void setMaxCalories(int max) {
        prefs.edit().putInt(MAX_CALORIES, max).commit();
    }

    public int getMaxCalories() {
        return prefs.getInt(MAX_CALORIES, 2000);
    }

    public void setMaxFat(int max) {
        prefs.edit().putInt(MAX_FAT, max).commit();
    }

    public int getMaxFat() {
        return prefs.getInt(MAX_FAT, 500);
    }

    public void setMaxProtein(int max) {
        prefs.edit().putInt(MAX_PROTEIN, max).commit();
    }

    public int getMaxProtein() {
        return prefs.getInt(MAX_PROTEIN, 500);
    }


    public void setMaxColesterol(int max) {
        prefs.edit().putInt(MAX_CHOLESTEROL, max).commit();
    }

    public int getMaxCholesterol() {
        return prefs.getInt(MAX_CHOLESTEROL, 500);
    }
}
