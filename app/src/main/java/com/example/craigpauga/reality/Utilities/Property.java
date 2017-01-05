package com.example.craigpauga.reality.Utilities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by CraigPauga on 10/8/16.
 */

/*
public class Property implements Parcelable {
    private String propertyName;
    private String propertyPic;
    private int amountFunded;

    public Property(){

    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyPic() {
        return propertyPic;
    }

    public void setPropertyPic(String propertyPic) {
        this.propertyPic = propertyPic;
    }

    public int getAmountFunded() {
        return amountFunded;
    }

    public void setAmountFunded(int amountFunded) {
        this.amountFunded = amountFunded;
    }
}
*/

public class Property implements Parcelable{
    private String propertyName;
    private String propertyPic;
    private String amountFunded;
    private String amountTotal;
    private ArrayList<String> propertyDetailPictures;

    // Constructor
    public Property(String propertyName, String propertyPic, String amountFunded, String amountTotal){
        this.propertyName = propertyName;
        this.propertyPic = propertyPic;
        this.amountFunded = amountFunded;
        this.amountTotal = amountTotal;
    }

    // Getters & Setters
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyPic() {
        return propertyPic;
    }

    public void setPropertyPic(String propertyPic) {
        this.propertyPic = propertyPic;
    }

    public String getAmountFunded() {
        return amountFunded;
    }

    public void setAmountFunded(String amountFunded) {
        this.amountFunded = amountFunded;
    }

    public String getAmountTotal() {return amountTotal;}

    public void setAmountTotal(String amountTotal) { this.amountTotal = amountTotal; }

    // Parcelling part
    public Property(Parcel in){
        String[] data = new String[3];

        in.readStringArray(data);
        this.propertyName = data[0];
        this.propertyPic = data[1];
        this.amountFunded = data[2];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.propertyName,
                this.propertyPic,
                this.amountFunded});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

}