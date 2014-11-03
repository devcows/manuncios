package com.devcows.manuncios;

import android.os.AsyncTask;

import com.devcows.manuncios.models.Category;

import java.util.List;

public class CategoriesGetTask extends AsyncTask<CategoriesTaskListener, Void, List<Category>> {
    private final ApiMilAnuncios mApi = ApiMilAnuncios.getInstance();
    private CategoriesTaskListener[] listeners;

    @Override
    protected List<Category> doInBackground(CategoriesTaskListener... listeners) {
        this.listeners = listeners;

        if (mApi.getCategories().isEmpty()) {
            mApi.loadCategories();
        }

        return mApi.getCategories();
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        for (CategoriesTaskListener listener : listeners) {
            listener.onCategoriesGetResult(categories);
        }
    }

}
