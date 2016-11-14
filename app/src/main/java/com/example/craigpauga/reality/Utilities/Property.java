package com.example.craigpauga.reality.Utilities;

/**
 * Created by CraigPauga on 10/8/16.
 */


public class Property {
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
