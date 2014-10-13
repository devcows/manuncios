package com.app.first.milanuncios;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;
    private transient Bitmap icon;
    private String imageUri;

    public String getUrl() {
        return url;
    }

    private String url;

    public Category(){}

    public Category(String name, Bitmap icon, String imageUri, String url) {
        this.name = name;
        this.icon = icon;
        this.url = url;
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getName() {
        return name;
    }

    public Bitmap getIcon() { return icon; }

}
