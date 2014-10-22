package com.app.first.milanuncios;

import android.app.SearchManager;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchQuery implements Serializable {

    //search parameters
    private Category category = null;
    private String string_query = null;

    private Integer page_number, order_by, min_price, max_price;

    public SearchQuery() {
    }

    public SearchQuery(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            this.string_query = intent.getStringExtra(SearchManager.QUERY);
        }

        if (intent.hasExtra(Utils.STRING_QUERY)) {
            this.string_query = (String) intent.getSerializableExtra(Utils.STRING_QUERY);
        }

        if (intent.hasExtra(Utils.SELECTED_CATEGORY)) {
            this.category = (Category) intent.getSerializableExtra(Utils.SELECTED_CATEGORY);
        }

        if(intent.hasExtra(Utils.ORDER_BY)){
            this.order_by = (Integer) intent.getSerializableExtra(Utils.ORDER_BY);
        } else {
            this.order_by = Utils.ORDER_BY_RECENT;
        }

        if(intent.hasExtra(Utils.MIN_PRICE)){
            this.min_price = (Integer) intent.getSerializableExtra(Utils.MIN_PRICE);
        }

        if(intent.hasExtra(Utils.MAX_PRICE)){
            this.max_price = (Integer) intent.getSerializableExtra(Utils.MAX_PRICE);
        }
    }


    public Category getCategory() {
        return category;
    }

    public String getString_query() {
        return string_query;
    }

    public Integer getPage_number() {
        return page_number;
    }

    public Integer getOrder_by() {
        return order_by;
    }

    public Integer getMin_price() {
        return min_price;
    }

    public Integer getMax_price() {
        return max_price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setString_query(String string_query) {
        this.string_query = string_query;
    }

    public void setOrder_by(Integer order_by) {
        this.order_by = order_by;
    }

    public void setPage_number(Integer page_number) {
        this.page_number = page_number;
    }

    public void setMin_price(Integer min_price) {
        this.min_price = min_price;
    }

    public void setMax_price(Integer max_price) {
        this.max_price = max_price;
    }


    public String getUrlQuery(){
        String urlQuery = "http://www.milanuncios.com/anuncios/";
        if (category != null) {
            urlQuery = category.getUrl();
        }

        if (string_query != null) {
            urlQuery += string_query + ".htm";
        }

        List<String> parameters = new ArrayList<String>();

        if (page_number > 1) {
            parameters.add("pagina=" + page_number.toString());
        }

        switch (order_by){
            case Utils.ORDER_BY_RECENT:
                //parameters.add("orden=nuevos");
                break;
            case Utils.ORDER_BY_OLD:
                parameters.add("orden=viejos");
                break;
            case Utils.ORDER_BY_EXPENSIVE:
                parameters.add("orden=caros");
                break;
            case Utils.ORDER_BY_CHEAP:
                parameters.add("orden=baratos");
                break;
        }


        if (min_price != null){
            parameters.add("desde=" + min_price.toString());
        }

        if (max_price != null){
            parameters.add("hasta=" + min_price.toString());
        }


        if (parameters.size() > 0) {
            urlQuery += "?";
            for (String param : parameters) {
                urlQuery += param + "&";
            }

            urlQuery = urlQuery.substring(0, urlQuery.length() - 1);
        }

        return urlQuery;
    }
}
