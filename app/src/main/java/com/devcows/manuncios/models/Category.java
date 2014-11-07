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

public class Category implements Serializable {
    private String name;
    private String imageUri;
    private String url;
    private int position;

    public Category() {
    }

    //getters
    public String getUrl() {
        return url;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //needs for the adapter.
    @Override
    public String toString() {
        return name;
    }

    public String toStringAll() {
        return "Category{" +
                "name='" + name + '\'' +
                ", imageUri='" + imageUri + '\'' +
                ", url='" + url + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
