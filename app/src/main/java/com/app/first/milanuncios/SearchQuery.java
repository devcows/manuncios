package com.app.first.milanuncios;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchQuery implements Serializable {

    //search parameters
    private Category category = null;
    private String string_query = null;

    private Integer page_number, order_by, min_price, max_price, publish_at;

    //ORDER BY
    public static final int ORDER_BY_RECENT = 0;
    public static final int ORDER_BY_OLD = 1;
    public static final int ORDER_BY_EXPENSIVE = 2;
    public static final int ORDER_BY_CHEAP = 3;


    //PUBLISH AT
    public static final Map<String , Integer> map_published_at = new HashMap<String , Integer>() {{
        put("En cualquier momento", -1);
        put("En el último dia", 1);
        put("En los últimos 3 dias", 3);
        put("En los últimos 5 dias", 5);
        put("En los últimos 10 dias", 10);
        put("En los últimos 15 dias", 15);
        put("En los últimos 20 dias", 20);
        put("En los últimos 30 dias", 30);
        put("En los últimos 60 dias", 60);
        put("En los últimos 90 dias", 90);
        put("En los últimos 120 dias", 120);

    }};

    public SearchQuery() {
        this.order_by = SearchQuery.ORDER_BY_RECENT;
    }

    public Category getCategory() {
        return category;
    }

    public String getString_query() {
        return string_query;
    }

    public Integer getOrder_by() {
        return order_by;
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

    public void setPublish_at(Integer publish_at) {
        this.publish_at = publish_at;
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

        if (page_number != null && page_number > 1) {
            parameters.add("pagina=" + page_number.toString());
        }

        if (order_by != null) {
            switch (order_by) {
                case ORDER_BY_RECENT:
                    //parameters.add("orden=nuevos");
                    break;
                case ORDER_BY_OLD:
                    parameters.add("orden=viejos");
                    break;
                case ORDER_BY_EXPENSIVE:
                    parameters.add("orden=caros");
                    break;
                case ORDER_BY_CHEAP:
                    parameters.add("orden=baratos");
                    break;
            }
        }


        if (min_price != null){
            parameters.add("desde=" + min_price.toString());
        }

        if (max_price != null){
            parameters.add("hasta=" + min_price.toString());
        }

        if (publish_at != null && publish_at > 0){
            parameters.add("dias=" + publish_at.toString());
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
