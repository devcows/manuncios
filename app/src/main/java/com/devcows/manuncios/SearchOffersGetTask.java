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
import com.devcows.manuncios.models.OfferOtherField;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SearchOffersGetTask extends AsyncTask<SearchOffersTaskListener, Void, List<Offer>> {
    private String cssFile = Utils.getContentCss();
    private SearchOffersTaskListener[] listeners;

    private Offer LoadHtmlOffer(Element row, int index) {
        Elements firstTitles = row.select("div.x4");
        Elements secondTitles = row.select("div.x7 a");
        Elements descriptions = row.select("div.tx");
        Elements images = row.select("div.x8");
        Elements others = row.select("div.x11");

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
        String url = "http://www.milanuncios.com";
        if (secondTitles.size() > 0) {
            Element elem = secondTitles.get(0);
            secondTitle = elem.text();
            url += elem.attr("href");
        }

        String description = "";
        if (descriptions.size() > 0) {
            Element elem = descriptions.get(0);
            description = elem.text();
        }

        String idOffer = "";
        String strImage = "";

        ApiMilAnuncios apiMilAnuncios = ApiMilAnuncios.getInstance();
        String[] imgServers = apiMilAnuncios.getIMG_SERVERS();

        if (images.size() > 0) {
            idOffer = images.get(0).attr("id").replace("f", "");

            if (Integer.parseInt(idOffer) > 99999999) {
                strImage = "http://" + imgServers[index % imgServers.length] + "/fp/" + idOffer.substring(0, 4) + "/" + idOffer.substring(4, 6) + "/" + idOffer + "_1.jpg";
            } else {
                strImage = "http://" + imgServers[index % imgServers.length] + "/fp/" + idOffer.substring(0, 3) + "/" + idOffer.substring(3, 5) + "/" + idOffer + "_1.jpg";
            }
        }

        //TODO: Get other fields. (price, others)
        List<OfferOtherField> othersLst = new ArrayList<OfferOtherField>();
        if (others.size() > 0) {
            for (Node elem : others.get(0).childNodes()) {
                if (elem instanceof Element) {
                    String text = ((Element) elem).text().replace("\u0080", "€");
                    String classElem = elem.attr("class");

                    //recolect particular and ver fotos case
                    if (classElem.equals("vembox") || classElem.equals("vefbox")) {
                        if (elem.childNodes().size() > 0 && elem.childNode(0).childNodes().size() > 0) {
                            classElem = elem.childNode(0).childNode(0).attr("class");
                        }
                    }

                    String backgroundColor = Utils.findPattern(classElem, cssFile);
                    if (backgroundColor.isEmpty()) {
                        String[] classSplited = classElem.split(" ");
                        backgroundColor = Utils.findPattern(classSplited[0], cssFile);

                        if (backgroundColor.isEmpty() && classSplited.length > 1) {
                            backgroundColor = Utils.findPattern(classSplited[1], cssFile);
                        }
                    }

                    if (text != null) {
                        OfferOtherField field = new OfferOtherField();
                        field.setText(text);
                        field.setBoxColor(backgroundColor);

                        othersLst.add(field);
                    }
                }
            }
        }

        Offer offer = new Offer();
        offer.setId(idOffer);
        offer.setFirstTitle(firstTitle);
        offer.setSecondTitle(secondTitle);
        offer.setDescription(description);
        offer.setImageUri(strImage);
        offer.setOther(othersLst);
        offer.setUrl(url);

        return offer;
    }

    @Override
    protected List<Offer> doInBackground(SearchOffersTaskListener... listeners) {
        this.listeners = listeners;
        List<Offer> offers = new ArrayList<Offer>();

        SearchQuery searchQuery = SearchQuery.getInstance();
        String urlQuery = searchQuery.getUrlQuery();
        Document doc = Utils.getDocumentFromUrl(urlQuery);
        if (doc != null) {
            Elements rows = doc.select("div.x1");
            for (int i = 0; i < rows.size(); i++) {
                Element row = rows.get(i);

                Offer offer = LoadHtmlOffer(row, i);
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
