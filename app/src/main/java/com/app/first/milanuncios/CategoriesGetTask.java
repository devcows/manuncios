package com.app.first.milanuncios;

import android.os.AsyncTask;

import java.util.List;

public class CategoriesGetTask extends AsyncTask<CategoriesTaskListener, Void, List<Category>> {
    private CategoriesTaskListener[] listeners;

    public CategoriesGetTask() {
    }

    @Override
    protected List<Category> doInBackground(CategoriesTaskListener... listeners) {
        this.listeners = listeners;
        return ApiMilAnuncios.LoadCategories();
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        for (CategoriesTaskListener listener : listeners) {
            listener.onCategoriesGetResult(categories);
        }
    }
}
