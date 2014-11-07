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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Offer implements Serializable {
    private String id;
    private String firstTitle;
    private String secondTitle;
    private String description;

    private List<OfferOtherField> other; //price, year, etc.
    private String imageUri;
    private List<String> secondaryImages;
    private String url;


    public Offer() {
        secondaryImages = new ArrayList<String>();
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

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public List<String> getSecondaryImages() {
        return secondaryImages;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setSecondaryImages(List<String> secondaryImages) {
        this.secondaryImages = secondaryImages;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "firstTitle='" + firstTitle + '\'' +
                ", secondTitle='" + secondTitle + '\'' +
                ", description='" + description + '\'' +
                ", other=" + other +
                ", imageUri='" + imageUri + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
