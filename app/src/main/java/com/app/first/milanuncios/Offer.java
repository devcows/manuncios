package com.app.first.milanuncios;

import android.graphics.Bitmap;

public class Offer {
    private String firstTitle;
    private String secondTitle;
    private String description;

    private String other; //price, year, etc.
    private transient Bitmap icon;


    public Offer(String firstTitle, String secondTitle, String description, String other, Bitmap icon) {
        this.firstTitle = firstTitle;
        this.secondTitle = secondTitle;
        this.description = description;
        this.other = other;
        this.icon = icon;
    }

    public String getFirstTitle() {
        return firstTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public String getDescription() {
        return description;
    }

    public String getOther() {
        return other;
    }

    public Bitmap getIcon() {
        return icon;
    }
}
