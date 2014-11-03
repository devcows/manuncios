package com.devcows.manuncios;

import com.devcows.manuncios.models.Category;

import java.util.List;

public interface CategoriesTaskListener {
    public void onCategoriesGetResult(List<Category> categories);
}