package com.madgeeklabs.healthfridge.models;

import java.util.List;

/**
 * Created by goofyahead on 2/28/15.
 */
public class Item {
    String name;
    String amount;
    NutritionFacts nutritionFacts;

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public NutritionFacts getNutritionFacts() {
        return nutritionFacts;
    }
}
