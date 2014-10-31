package com.devcows.manuncios.models;

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

    private String capitalize(String line)
    {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    //setters
    public void setText(String text) {
        this.text = capitalize(text);
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
