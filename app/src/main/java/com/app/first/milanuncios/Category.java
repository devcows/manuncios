package com.app.first.milanuncios;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;
    private String imageUri;

    public String getUrl() {
        return url;
    }

    private String url;

    public Category(){}

    public Category(String name, String imageUri, String url) {
        this.name = name;
        this.url = url;
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getName() {
        return name;
    }

}
