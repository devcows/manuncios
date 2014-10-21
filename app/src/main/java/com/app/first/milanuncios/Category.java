package com.app.first.milanuncios;

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
