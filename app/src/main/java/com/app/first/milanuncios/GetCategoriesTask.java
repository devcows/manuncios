package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCategoriesTask extends AsyncTask<CategoriesTaskListener, Void, List<Category>> {
    public GetCategoriesTask() {}

    private CategoriesTaskListener[] listeners;

    @Override
    protected List<Category> doInBackground(CategoriesTaskListener... listeners) {
        this.listeners = listeners;
        List<Category> categories = new ArrayList<Category>();

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

        return categories;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        for(CategoriesTaskListener listener: listeners){
            listener.onGetCategoriesResult(categories);
        }
    }
}
