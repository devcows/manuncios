//Copyright (C) 2014  Guillermo G. (info@devcows.com)
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.devcows.manuncios;

import android.os.AsyncTask;

import com.devcows.manuncios.models.Offer;

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


    private List<String> getImages(String idOffer) {
        ApiMilAnuncios apiMilAnuncios = ApiMilAnuncios.getInstance();
        String[] imgServers = apiMilAnuncios.getIMG_SERVERS();

        List<String> images = new ArrayList<String>();
        int index = 1;
        boolean finish = false;

        while (!finish) {
            try {

                String strUrl = "http://" + imgServers[index % imgServers.length] + "/fg/" + idOffer.substring(0, 4) + "/" + idOffer.substring(4, 6) + "/" + idOffer + "_" + index + ".jpg";
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

        return images;
    }

    @Override
    protected Offer doInBackground(OfferTaskListener... listeners) {
        this.listeners = listeners;

        List<String> images = new ArrayList<String>();
        String idOffer = offer.getId();
        if (idOffer != null && !idOffer.isEmpty()) {
            images = getImages(idOffer);
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
