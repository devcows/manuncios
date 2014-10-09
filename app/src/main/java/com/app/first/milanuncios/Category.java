package com.app.first.milanuncios;

import android.media.Image;

public class Category {
    private String name;
    private Image icon;
    private String url;

    public Category(String name, Image icon, String url) {
        this.name = name;
        this.icon = icon;
        this.url = url;
    }

    public String getName() {
        return name;
    }
}
