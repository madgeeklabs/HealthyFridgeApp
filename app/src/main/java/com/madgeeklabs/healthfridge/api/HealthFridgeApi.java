package com.madgeeklabs.healthfridge.api;

import com.madgeeklabs.healthfridge.models.Item;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by goofyahead on 2/28/15.
 */
public interface HealthFridgeApi {

    @GET("/consumptions/{ID}")
    void getMyConsumptions(@Path("ID") String uuid,
                          Callback<List<Item>> cb);
}
