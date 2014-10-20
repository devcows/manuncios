package com.app.first.milanuncios;

import android.os.AsyncTask;

import java.util.List;

public class AdvancedSearchGetTask extends AsyncTask<AdvancedSearchTaskListener, Void, Void> {
    private AdvancedSearchTaskListener[] listeners;
    private List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public AdvancedSearchGetTask() {
    }

    @Override
    protected Void doInBackground(AdvancedSearchTaskListener... listeners) {
        this.listeners = listeners;
        categories = ApiMilAnuncios.LoadCategories();

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        for (AdvancedSearchTaskListener listener : listeners) {
            listener.onAdvancedSearchGetResult();
        }
    }
}
