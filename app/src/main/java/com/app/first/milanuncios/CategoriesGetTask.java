package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CategoriesGetTask extends AsyncTask<CategoriesTaskListener, Void, List<Category>> {
    private CategoriesTaskListener[] listeners;

    public CategoriesGetTask() {
    }

    private Category LoadHtmlCatergory(Element row, String divNameIco, String divNameCategory) {
        Category c = new Category();

        Elements divIcons = row.select(divNameIco);
        Elements divCategories = row.select(divNameCategory);

        if (divIcons.size() > 0 && divCategories.size() > 0) {
            Elements imgTag = divIcons.get(0).select("img");
            String strImage = imgTag.get(0).attr("src");

            Elements nodeCategory = divCategories.get(0).select("a");
            String strUrl = "http://www.milanuncios.com" + nodeCategory.get(0).attr("href");
            String strName = nodeCategory.get(0).text();


            c.setName(strName);
            c.setImageUri(strImage);
            c.setUrl(strUrl);
        }

        return c;
    }

    @Override
    protected List<Category> doInBackground(CategoriesTaskListener... listeners) {
        this.listeners = listeners;
        List<Category> categories = new ArrayList<Category>();

        Document doc = Utils.getDocumentFromUrl("http://www.milanuncios.com");
        if (doc != null) {
            Elements rows = doc.select("div.filaCat");
            for (Element row : rows) {
                Category c1 = LoadHtmlCatergory(row, "div.catIcoIzq", "div.categoriaIzq");
                Category c2 = LoadHtmlCatergory(row, "div.catIcoDch", "div.categoriaDch");

                if (c1.getUrl() != null) {
                    categories.add(c1);
                }

                if (c2.getUrl() != null) {
                    categories.add(c2);
                }
            }
        }

        return categories;
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        for (CategoriesTaskListener listener : listeners) {
            listener.onCategoriesGetResult(categories);
        }
    }
}
