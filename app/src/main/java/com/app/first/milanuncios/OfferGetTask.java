package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

        int index = 1;
        boolean finish = false;

        while (!finish) {
            try {
                String idOffer = offer.getId();

                String strUrl = "http://91.229.239.8/fg/" + idOffer.substring(0, 4) + "/" + idOffer.substring(4, 6) + "/" + idOffer + "_" + index + ".jpg";
                URL url = new URL(strUrl);
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();

                huc.setRequestMethod("HEAD");
                huc.connect();
                int code = huc.getResponseCode();
                if (code >= 200 && code < 300) {
                    images.add(strUrl);
                } else {
                    finish = true;
                }

                index++;
            } catch (IOException ex) {
                finish = true;
            }
        }

/////// Old mode not working now.
//        Document doc = Utils.getDocumentFromUrl(offer.getUrl());
//        if (doc != null) {
//            Elements rows = doc.select("div.pagAnuFoto img");
//            for (Element row : rows) {
//                String strImage = row.attr("src");
//                images.add(strImage);
//            }
//        }

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
