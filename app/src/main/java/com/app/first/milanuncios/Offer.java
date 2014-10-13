package com.app.first.milanuncios;

import java.util.List;

public class Offer {
    private String firstTitle;
    private String secondTitle;
    private String description;

    private List<OfferOtherField> other; //price, year, etc.
    private String imageUri;
    private String url;


    public Offer() {
    }

    //getters
    public String getFirstTitle() {
        return firstTitle;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public String getDescription() {
        return description;
    }

    public List<OfferOtherField> getOther() {
        return other;
    }

    public String getImageUri() {
        return imageUri;
    }

    //setters
    public void setFirstTitle(String firstTitle) {
        this.firstTitle = firstTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOther(List<OfferOtherField> other) {
        this.other = other;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
