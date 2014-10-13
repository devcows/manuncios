package com.app.first.milanuncios;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchOffersGetTask extends AsyncTask<SearchOffersTaskListener, Void, List<Offer>> {
    private Category category;
    private String querySearch;

    private SearchOffersTaskListener[] listeners;

    public SearchOffersGetTask() {
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setQuerySearch(String querySearch) {
        this.querySearch = querySearch.replace(" ", "-");
    }

    private Offer LoadHtmlOffer(Element row) {
        Elements firstTitles = row.select("div.x4");
        Elements secondTitles = row.select("div.x7");
        Elements descriptions = row.select("div.tx");
        Elements images = row.select("div.x8");

        //TODO: Get other fields. (price, others)
//        this.other = other;
        //regex to read others ===> \.pr.+{.+background:(?<color>.{1,7})
        //file ==> file:///android_asset/file.css

        String firstTitle = "";
        if (firstTitles.size() > 0) {
            for (Node elem : firstTitles.get(0).childNodes()) {

                if (elem instanceof Element) {
                    firstTitle += ((Element) elem).text();
                } else if (elem instanceof TextNode) {
                    firstTitle += ((TextNode) elem).text();
                }
            }
        }

        String secondTitle = "";
        if (secondTitles.size() > 0) {
            Element elem = (Element) secondTitles.get(0).childNodes().get(0);
            secondTitle = elem.text();
        }

        String description = "";
        if (descriptions.size() > 0) {
            Element elem = descriptions.get(0);
            description = elem.text();
        }

        String strImage = "";
        if (images.size() > 0) {
            Elements imgTag = images.get(0).select("img");
            if (imgTag.size() > 0) {
                strImage = imgTag.get(0).attr("src");
            }
        }

        Offer offer = new Offer();
        offer.setFirstTitle(firstTitle);
        offer.setSecondTitle(secondTitle);
        offer.setDescription(description);
        offer.setImageUri(strImage);

        return offer;
    }

    @Override
    protected List<Offer> doInBackground(SearchOffersTaskListener... listeners) {
        this.listeners = listeners;
        List<Offer> offers = new ArrayList<Offer>();

        String urlQuery = "http://www.milanuncios.com/anuncios/";
        if (category != null) {
            urlQuery = category.getUrl();
        }

        if (querySearch != null){
            urlQuery += querySearch + ".htm";
        }

        Document doc = Utils.getDocumentFromUrl(urlQuery);
        if (doc != null) {
            Elements rows = doc.select("div.x1");
            for (Element row : rows) {

                Offer offer = LoadHtmlOffer(row);
                if (offer != null) {
                    offers.add(offer);
                }
            }
        }

        return offers;
    }

    @Override
    protected void onPostExecute(List<Offer> offers) {
        for (SearchOffersTaskListener listener : listeners) {
            listener.onOffersGetResult(offers);
        }
    }
}
