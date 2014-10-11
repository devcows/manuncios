package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OffersGetTask extends AsyncTask<OffersTaskListener, Void, List<Offer>> {
    private Category category;
    private OffersTaskListener[] listeners;

    public OffersGetTask(Category category) {
        this.category = category;
    }

    private Offer LoadHtmlOffer(Element row) {
        Elements firstTitles = row.select("div.x4");
        Elements secondTitles = row.select("div.x7");

//        this.secondTitle = secondTitle;
//        this.description = description;
//        this.other = other;
//        this.icon = icon;

        String firstTitle = "";
        if (firstTitles.size() > 0) {
            for (Node elem : firstTitles.get(0).childNodes()) {

                if (elem instanceof Element) {
                    firstTitle += ((Element) elem).text();
                } else if (elem instanceof TextNode) {
                    firstTitle += ((TextNode) elem).text();
                }
            }


//            Elements imgTag = divIcons.get(0).select("img");
//            String strImage = imgTag.get(0).attr("src");
//
//            Bitmap bmpImage = null;
//            try {
//                URL urlImage = new URL(strImage);
//                bmpImage = BitmapFactory.decodeStream(urlImage.openConnection().getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Elements nodeCategory = divCategories.get(0).select("a");
//            String strUrl = "http://www.milanuncios.com" + nodeCategory.get(0).attr("href");
//            String strName = nodeCategory.get(0).text();
        }

        String secondTitle = "";
        if (secondTitles.size() > 0) {
            Element elem = (Element) secondTitles.get(0).childNodes().get(0);
            secondTitle = elem.text();
        }


        Offer o = new Offer(firstTitle, secondTitle, null, null, null);
        return o;
    }

    @Override
    protected List<Offer> doInBackground(OffersTaskListener... listeners) {
        this.listeners = listeners;
        List<Offer> offers = new ArrayList<Offer>();

        try {
            String urlQuery = "http://www.milanuncios.com";
            if (category != null) {
                urlQuery = category.getUrl();
            }

            Document doc = Jsoup.connect(urlQuery).get();

            Elements rows = doc.select("div.x1");
            for (Element row : rows) {

                Offer offer = LoadHtmlOffer(row);
                if (offer != null) {
                    offers.add(offer);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return offers;
    }

    @Override
    protected void onPostExecute(List<Offer> offers) {
        for (OffersTaskListener listener : listeners) {
            listener.onOffersGetResult(offers);
        }
    }
}
