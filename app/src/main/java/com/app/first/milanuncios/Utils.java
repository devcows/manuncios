package com.app.first.milanuncios;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Utils {
    public static Document getDocumentFromUrl(String url) {
        Document doc = null;

        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com").get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }
}
