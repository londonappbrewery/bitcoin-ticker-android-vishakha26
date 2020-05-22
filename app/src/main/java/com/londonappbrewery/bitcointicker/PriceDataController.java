package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class PriceDataController {

    private double price;

    public static PriceDataController parseJson(JSONObject object) {
        PriceDataController dataController = new PriceDataController();

        try {
            dataController.price = object.getDouble("last");
            return dataController;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public double getPrice() {
        return price;
    }
}
