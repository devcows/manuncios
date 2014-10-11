package com.app.first.milanuncios;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;
    private transient Bitmap icon;

    public String getUrl() {
        return url;
    }

    private String url;

    public Category(){}

    public Category(String name, Bitmap icon, String url) {
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public Bitmap getIcon() { return icon; }

}
