package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class OfferGetTask extends AsyncTask<OfferTaskListener, Void, Offer> {
    private Offer offer;

    private OfferTaskListener[] listeners;

    public OfferGetTask() {
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    @Override
    protected Offer doInBackground(OfferTaskListener... listeners) {
        this.listeners = listeners;

        List<String> images = new ArrayList<String>();
        Document doc = Utils.getDocumentFromUrl(offer.getUrl());
        if (doc != null) {
            Elements rows = doc.select("div.pagAnuFoto img");
            for (Element row : rows) {
                String strImage = row.attr("src");
                images.add(strImage);
            }
        }

        offer.setSecondaryImages(images);
        return offer;
    }

    @Override
    protected void onPostExecute(Offer offer) {
        for (OfferTaskListener listener : listeners) {
            listener.onOfferGetResult(offer);
        }
    }
}
