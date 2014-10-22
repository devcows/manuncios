package com.app.first.milanuncios;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ApiMilAnuncios {
    private static String MAIN_WEB = "http://www.milanuncios.com";
    private static String[] IMG_SERVERS = {"91.229.239.8", "91.229.239.12"};

    public static String[] getIMG_SERVERS() {
        return IMG_SERVERS;
    }

    private static Category LoadHtmlCatergory(Element row, String divNameIco, String divNameCategory) {
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

    public static List<Category> LoadCategories() {
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

        //starts in 1
        for (int position = 0; position < categories.size(); position++) {
            Category c = categories.get(position);
            c.setPosition(position + 1);
        }

        return categories;
    }


    //TODO copy all api queries methods here!
}
