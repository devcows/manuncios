package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCategoriesTask extends AsyncTask<CategoriesTaskListener, Void, Void> {
    public GetCategoriesTask() {}

    private List<Category> categories = new ArrayList<Category>();
    private CategoriesTaskListener[] listeners;

    @Override
    protected Void doInBackground(CategoriesTaskListener... listeners) {
        this.listeners = listeners;

        try {
            Document doc = Jsoup.connect("http://www.milanuncios.com").get();
            String title = doc.title();

            Elements rows = doc.select("div.filaCat");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: GET A LA WEB
        Category c1 = new Category("Motor", null, "http://www.milanuncios.com/motor");
        categories.add(c1);
        Category c2 = new Category("Empleo", null, "http://www.milanuncios.com/ofertas-de-empleo");
        categories.add(c2);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        for(CategoriesTaskListener listener: listeners){
            listener.onGetCategoriesResult(categories);
        }
    }
}
