package com.app.first.milanuncios;

import android.content.res.AssetManager;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static String contentCss = "";

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

    public static void loadCss(AssetManager assetManager, String filePath){
        try{
            InputStream stream = assetManager.open(filePath);
            contentCss = IOUtils.toString(stream);
        } catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public static String findPattern(String classElem, String cssFile){
        String backgroundColor = "";

        //http://myregexp.com/
        //regex to read others ===> \.pr.+{.+background:(?<color>.{1,7})
        //regex in java ===> \.pr[,\{].+\{.+background:#(.+);
        //      corregida => \.pr[,\{]?.*\{.*background:([^;]+);
        String pattern = "\\." + classElem + "[,\\{]?.*\\{.*background:([^;]+);";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(cssFile);

        if (m.find() && m.groupCount() > 0) {
            backgroundColor = m.group(1);
        }

        return backgroundColor;
    }


    public static String getContentCss(){
        return contentCss;
    }
}
