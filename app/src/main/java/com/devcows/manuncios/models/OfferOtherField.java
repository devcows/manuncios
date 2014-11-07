//Copyright (C) 2014  Guillermo G. (info@devcows.com)
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.devcows.manuncios.models;

import com.devcows.manuncios.Utils;

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
        this.text = Utils.capitalizeString(text);
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
