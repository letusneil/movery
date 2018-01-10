package com.nbvinas.movery;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by nvinas on 06/01/2018.
 */

public class Location extends RealmObject implements Parcelable {

    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("address")
    @Expose
    private String address;

    /**
     * This should never be, in any case, default or null
     * as it would defeat the purpose of the app.
     *
     * @return Location latitude.
     */
    @NonNull
    public Double getLat() {
        return lat;
    }

    /**
     * This should never be, in any case, default or null.
     * as it would defeat the purpose of the app.
     *
     * @return Location longitude.
     */
    @NonNull
    public Double getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("lat=").append(lat);
        sb.append(", lng=").append(lng);
        sb.append(", address='").append(address).append('\'');
        sb.append('}');
        return sb.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.lat);
        dest.writeValue(this.lng);
        dest.writeString(this.address);
    }

    public Location() {
    }

    protected Location(Parcel in) {
        this.lat = (Double) in.readValue(Double.class.getClassLoader());
        this.lng = (Double) in.readValue(Double.class.getClassLoader());
        this.address = in.readString();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

}
