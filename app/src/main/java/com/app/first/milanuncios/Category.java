package com.app.first.milanuncios;

import android.graphics.Bitmap;

public class Category {
    private String name;
    private Bitmap icon;
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
