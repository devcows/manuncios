package com.devcows.manuncios;

import java.io.Serializable;


public class OfferOtherField implements Serializable {
    private String text;
    private String textColor;
    private String boxColor;

    public OfferOtherField() {
    }

    //Getters
    public String getText() {
        return text;
    }

    //setters
    public void setText(String text) {
        this.text = text;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBoxColor() {
        return boxColor;
    }

    public void setBoxColor(String boxColor) {
        this.boxColor = boxColor;
    }

    @Override
    public String toString() {
        return "OfferOtherField{" +
                "text='" + text + '\'' +
                ", textColor='" + textColor + '\'' +
                ", boxColor='" + boxColor + '\'' +
                '}';
    }
}
