package com.nbvinas.movery.network.api;

import com.nbvinas.movery.network.DeliveriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by nvinas on 06/01/2018.
 */
public interface DeliveriesApi {

    @GET("deliveries")
    Call<DeliveriesResponse> deliveries(@Header("Content-Type") String contentType,
                                        @Header("JsonStub-User-Key") String userKey,
                                        @Header("JsonStub-Project-Key") String projectKey);
}
