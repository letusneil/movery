package com.nbvinas.movery;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by nvinas on 06/01/2018.
 */
public class Delivery extends RealmObject implements Parcelable {

    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("receiverName")
    @Expose
    private String receiverName;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("contactNo")
    @Expose
    private String contactNo;
    @SerializedName("fees")
    @Expose
    private Double fees;
    @SerializedName("location")
    @Expose
    private Location location;

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getAvailability() {
        return availability;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Double getFees() {
        return fees;
    }

    public Location getLocation() {
        return location;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.imageUrl);
        dest.writeString(this.receiverName);
        dest.writeString(this.availability);
        dest.writeString(this.contactNo);
        dest.writeDouble(this.fees);
        dest.writeParcelable(this.location, flags);
    }

    public Delivery() {
    }

    protected Delivery(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.imageUrl = in.readString();
        this.receiverName = in.readString();
        this.availability = in.readString();
        this.contactNo = in.readString();
        this.fees = in.readDouble();
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<Delivery> CREATOR = new Creator<Delivery>() {
        @Override
        public Delivery createFromParcel(Parcel source) {
            return new Delivery(source);
        }

        @Override
        public Delivery[] newArray(int size) {
            return new Delivery[size];
        }
    };
}
