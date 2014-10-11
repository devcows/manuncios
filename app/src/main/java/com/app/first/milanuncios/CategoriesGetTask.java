package com.app.first.milanuncios;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoriesGetTask extends AsyncTask<CategoriesTaskListener, Void, List<Category>> {
    public CategoriesGetTask() {}

    private CategoriesTaskListener[] listeners;

    private Category LoadHtmlCatergory(Element row, String divNameIco, String divNameCategory){
        Category c = null;

        Elements divIcons =  row.select(divNameIco);
        Elements divCategories =  row.select(divNameCategory);

        if(divIcons.size() > 0 && divCategories.size() > 0){
            Elements imgTag = divIcons.get(0).select("img");
            String strImage = imgTag.get(0).attr("src");

            Bitmap bmpImage = null;
            try {
                URL urlImage = new URL(strImage);
                bmpImage = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            Elements nodeCategory = divCategories.get(0).select("a");
            String strUrl = "http://www.milanuncios.com" + nodeCategory.get(0).attr("href");
            String strName = nodeCategory.get(0).text();

            c = new Category(strName, bmpImage, strUrl);
        }

        return c;
    }

    @Override
    protected List<Category> doInBackground(CategoriesTaskListener... listeners) {
        this.listeners = listeners;
        List<Category> categories = new ArrayList<Category>();

        try {
            Document doc = Jsoup.connect("http://www.milanuncios.com").get();

            Elements rows = doc.select("div.filaCat");
            for (Element row: rows){
                Category c1 = LoadHtmlCatergory(row, "div.catIcoIzq", "div.categoriaIzq");
                Category c2 = LoadHtmlCatergory(row, "div.catIcoDch", "div.categoriaDch");

                if(c1 != null) {
                    categories.add(c1);
                }

                if(c2 != null){
                    categories.add(c2);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        for(CategoriesTaskListener listener: listeners){
            listener.onCategoriesGetResult(categories);
        }
    }
}
