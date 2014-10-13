package com.app.first.milanuncios;

import android.content.res.AssetManager;

import com.nostra13.universalimageloader.utils.IoUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
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

    public static String getPattern(String classHtml){
        //String pattern = "\\." + classHtml + ".+\\{.+background:\\(?<color>.\\{1,7\\}\\)";
        String pattern = "\\.pr.+{.+background:(?<color>.{1,7})";
        return pattern;
    }

    public static final String PATTERN = "(?<=(^|,))(([^\",]+)|\"([^\"]*)\")(?=($|,))";
    public static void main(String[] args) {
        String line = ",1234,ABC";
        Matcher matcher = Pattern.compile(PATTERN).matcher(line);
        while (matcher.find()) {
            if (matcher.group(3) != null) {
                System.out.println(matcher.group(3));
            } else {
                System.out.println(matcher.group(4));
            }
        }
    }


    public static String getContentCss(){
        return contentCss;
    }
}
