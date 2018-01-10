package com.nbvinas.movery.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nbvinas.movery.Delivery;

import java.util.List;

/**
 * Created by nvinas on 06/01/2018.
 */

public class DeliveriesResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deliveries")
    @Expose
    private List<Delivery> deliveries = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }

}