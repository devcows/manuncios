package com.devcows.manuncios;

import com.devcows.manuncios.models.Category;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ApiMilAnuncios {
    private List<Category> categories = new ArrayList<Category>();
    private LinkedHashMap<String, String> communities = new LinkedHashMap<String, String>();

    private final String MAIN_WEB = "http://www.milanuncios.com";
    private final String[] IMG_SERVERS = {"91.229.239.8", "91.229.239.12"};

    public String[] getIMG_SERVERS() {
        return IMG_SERVERS;
    }

    private static ApiMilAnuncios instance = null;

    protected ApiMilAnuncios() {
        loadCategories();

        if (!categories.isEmpty()) {
            Category firstCategory = categories.get(0);
            loadCommunities(firstCategory);
        }
    }

    public static ApiMilAnuncios getInstance() {
        if (instance == null) {
            instance = new ApiMilAnuncios();
        }

        return instance;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public LinkedHashMap<String, String> getCommunities() {
        return communities;
    }

    private Category loadHtmlCatergory(Element row, String divNameIco, String divNameCategory) {
        Category c = new Category();

        Elements divIcons = row.select(divNameIco);
        Elements divCategories = row.select(divNameCategory);

        if (divIcons.size() > 0 && divCategories.size() > 0) {
            Elements imgTag = divIcons.get(0).select("img");
            String strImage = imgTag.get(0).attr("src");

            Elements nodeCategory = divCategories.get(0).select("a");
            String strUrl = MAIN_WEB + nodeCategory.get(0).attr("href");
            String strName = nodeCategory.get(0).text();


            c.setName(strName);
            c.setImageUri(strImage);
            c.setUrl(strUrl);
        }

        return c;
    }

    private void loadCategories() {
        categories.clear();

        Document doc = Utils.getDocumentFromUrl("http://www.milanuncios.com");
        if (doc != null) {
            Elements rows = doc.select("div.filaCat");
            for (Element row : rows) {
                Category c1 = loadHtmlCatergory(row, "div.catIcoIzq", "div.categoriaIzq");
                Category c2 = loadHtmlCatergory(row, "div.catIcoDch", "div.categoriaDch");

                if (c1.getUrl() != null) {
                    categories.add(c1);
                }

                if (c2.getUrl() != null) {
                    categories.add(c2);
                }
            }
        }

        //starts in 1
        for (int position = 0; position < categories.size(); position++) {
            Category c = categories.get(position);
            c.setPosition(position + 1);
        }
    }

    private void loadCommunities(Category category) {
        communities.clear();

        Document doc = Utils.getDocumentFromUrl(category.getUrl());
        if (doc != null) {
            Elements options = doc.select("select#protmp option");
            for (Element option : options) {

                String keyCommunity = option.text();
                String valueCommunity = option.attr("value");
                communities.put(keyCommunity, valueCommunity);
            }
        }

    }


    //TODO copy all api queries methods here!
}
