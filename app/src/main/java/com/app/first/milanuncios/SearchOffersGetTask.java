package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SearchOffersGetTask extends AsyncTask<SearchOffersTaskListener, Void, List<Offer>> {
    private String cssFile = Utils.getContentCss();
    private Category category;
    private String querySearch;
    private int page;

    private SearchOffersTaskListener[] listeners;

    public SearchOffersGetTask() {
        this.page = 1;
    }

    public void setPage(int page){
        this.page = page;
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
            Element elem = (Element) secondTitles.get(0).childNodes().get(0);
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
        if (images.size() > 0) {
            idOffer = images.get(0).attr("id").replace("f", "");

            if (Integer.parseInt(idOffer) > 99999999) {
                strImage = "http://91.229.239.12/fp/" + idOffer.substring(0, 4) + "/" + idOffer.substring(4, 6) + "/" + idOffer + "_1.jpg";
            } else {
                strImage = "http://91.229.239.12/fp/" + idOffer.substring(0, 3) + "/" + idOffer.substring(3, 5) + "/" + idOffer + "_1.jpg";
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

                    if (classElem.contains("vem")) {
                        System.out.println("aaa");
                    }

                    String backgroundColor = Utils.findPattern(classElem, cssFile);
                    if (backgroundColor.isEmpty()) {
                        String[] classSplited = classElem.split(" ");
                        backgroundColor = Utils.findPattern(classSplited[0], cssFile);

                        if (backgroundColor.isEmpty() && classSplited.length > 1) {
                            backgroundColor = Utils.findPattern(classSplited[1], cssFile);
                        }
                    }

                    OfferOtherField fields = new OfferOtherField();
                    fields.setText(text);
                    fields.setBoxColor(backgroundColor);

                    othersLst.add(fields);
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

        String urlQuery = "http://www.milanuncios.com/anuncios/";
        if (category != null) {
            urlQuery = category.getUrl();
        }

        if (querySearch != null) {
            urlQuery += querySearch + ".htm";
        }

        if(page > 1){
            urlQuery += "?pagina=2";
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
