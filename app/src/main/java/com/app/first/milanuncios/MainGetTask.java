package com.app.first.milanuncios;

import android.os.AsyncTask;

public class MainGetTask extends AsyncTask<MainTaskListener, Void, Void> {
    private MainTaskListener[] listeners;

    public MainGetTask() {
    }

    @Override
    protected Void doInBackground(MainTaskListener... listeners) {
        this.listeners = listeners;

        //initializers
        ApiMilAnuncios.getInstance();
        SearchQuery.getInstance();

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        for (MainTaskListener listener : listeners) {
            listener.onMainGetResult();
        }
    }
}
