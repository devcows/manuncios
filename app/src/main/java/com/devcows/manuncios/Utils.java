package com.devcows.manuncios;

import android.content.res.AssetManager;
import android.content.res.Resources;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static String contentCss = "";
    public static final String SELECTED_OFFER = "selected_offer";
    public static final String CONTACT_URL = "contact_url";
    public static final String DRAWER_POSITION = "drawer_position";

    public static Document getDocumentFromUrl(String url) {
        return getDocumentFromUrl(url, 3);
    }

    public static Document getDocumentFromUrl(String url, int retries) {
        Document doc = null;

        try {
            doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .timeout(10 * 1000) //10 seconds
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (doc == null && retries > 0) {
            doc = getDocumentFromUrl(url, retries - 1);
        }

        return doc;
    }

    public static void loadCss(AssetManager assetManager, String filePath) {
        try {
            InputStream stream = assetManager.open(filePath);
            contentCss = IOUtils.toString(stream);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String findPattern(String classElem, String cssFile) {
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


    public static int getPixels(Resources resource, int dps) {
        final float scale = resource.getDisplayMetrics().density;
        int pixels = (int) (dps * scale + 0.5f);

        return pixels;
    }

    public static String getContentCss() {
        return contentCss;
    }

    public static List<String> getStringList(String[] strings){
        List<String> arrayList = new ArrayList<String>();

        if (strings != null) {
            for (int i = 0; i < strings.length; i++) {
                arrayList.add(strings[i]);
            }
        }

        return arrayList;
    }
}
