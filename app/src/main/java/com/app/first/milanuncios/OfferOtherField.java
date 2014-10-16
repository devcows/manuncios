package com.app.first.milanuncios;

import java.io.Serializable;

public class OfferOtherField implements Serializable{
    private String text;
    private String textColor;
    private String boxColor;

    public OfferOtherField() {
    }

    //Getters
    public String getText() {
        return text;
    }

    public String getTextColor() {
        return textColor;
    }

    public String getBoxColor() {
        return boxColor;
    }

    //setters
    public void setText(String text) {
        this.text = text;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
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
