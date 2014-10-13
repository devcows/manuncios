package com.app.first.milanuncios;

import android.os.AsyncTask;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchOffersGetTask extends AsyncTask<SearchOffersTaskListener, Void, List<Offer>> {
    private String cssFile = Utils.getContentCss();
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
        Elements others = row.select("div.x11");

        //TODO: Get other fields. (price, others)
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

        List<OfferOtherField> othersLst = new ArrayList<OfferOtherField>();
        if (others.size() > 0){
            Elements divOthers = others.get(0).select("div");
            for(Element elem : divOthers){
                String text = elem.text();
                String classElem = elem.attr("class");

                //http://myregexp.com/
                //regex to read others ===> \.pr.+{.+background:(?<color>.{1,7})
                String pattern = "\\.pr.+\\{.+background:\\{1-7\\}";

                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(cssFile);

                if (m.find( )) {
                    String cde = m.group(0);
                    String abc = m.group(1);
                    System.out.println(m);
                }

                OfferOtherField fields = new OfferOtherField();
                fields.setText(text);

                othersLst.add(fields);
            }
        }

        Offer offer = new Offer();
        offer.setFirstTitle(firstTitle);
        offer.setSecondTitle(secondTitle);
        offer.setDescription(description);
        offer.setImageUri(strImage);
        offer.setOther(othersLst);

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
