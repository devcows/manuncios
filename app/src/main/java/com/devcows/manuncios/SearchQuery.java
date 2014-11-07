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

import com.devcows.manuncios.models.Category;
import com.devcows.manuncios.persistent.MyHistory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class SearchQuery implements Serializable {

    //search parameters
    private Category category = null;
    private String string_query = null;
    private String community = null;

    private Integer page_number, order_by, min_price, max_price, publish_at;

    //ORDER BY
    public static final int ORDER_BY_RECENT = 0;
    public static final int ORDER_BY_OLD = 1;
    public static final int ORDER_BY_CHEAP = 2;
    public static final int ORDER_BY_EXPENSIVE = 3;

    private static SearchQuery instance;

    public static SearchQuery getInstance() {
        if (instance == null) {
            instance = new SearchQuery();
        }

        return instance;
    }


    //PUBLISH AT
    public static final LinkedHashMap<String, Integer> map_published_at = new LinkedHashMap<String, Integer>() {{
        put("En cualquier momento", -1);
        put("En el último día", 1);
        put("En los últimos 3 días", 3);
        put("En los últimos 5 días", 5);
        put("En los últimos 10 días", 10);
        put("En los últimos 15 días", 15);
        put("En los últimos 20 días", 20);
        put("En los últimos 30 días", 30);
        put("En los últimos 60 días", 60);
        put("En los últimos 90 días", 90);
        put("En los últimos 120 días", 120);

    }};

    public SearchQuery() {
        setDefaultsValues();
    }

    public void setDefaultsValues() {
        //default values

        this.category = null;
        this.string_query = null;
        this.community = ""; //en toda españa

        this.page_number = null;
        this.order_by = SearchQuery.ORDER_BY_RECENT;

        this.min_price = null;
        this.max_price = null;
        this.publish_at = null;
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

    public Integer getMin_price() {
        return min_price;
    }

    public Integer getMax_price() {
        return max_price;
    }

    public String getCommunity() {
        return community;
    }

    public Integer getPublish_at() {
        return publish_at;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setString_query(String string_query) {
        this.string_query = string_query;

        MyHistory myHistory = MyHistory.getInstance();
        myHistory.addQuery(string_query);
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

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getUrlQuery() {
        //TODO community ejemplo -en-alava/
        String urlQuery = "http://www.milanuncios.com/anuncios/";
        if (category != null) {
            urlQuery = category.getUrl();
        }

        if (community != null && !community.isEmpty()) {
            //remove final /
            if (urlQuery.endsWith("/")) {
                urlQuery = urlQuery.substring(0, urlQuery.length() - 1);
            }

            urlQuery += "-en-" + community + "/";
        }

        if (string_query != null && !string_query.isEmpty()) {
            urlQuery += string_query.replace(" ", "-") + ".htm";
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


        if (min_price != null) {
            parameters.add("desde=" + min_price.toString());
        }

        if (max_price != null) {
            parameters.add("hasta=" + max_price.toString());
        }

        if (publish_at != null && publish_at > 0) {
            parameters.add("dias=" + publish_at.toString());
        }

        if (parameters.size() > 0) {
            urlQuery += "?";
            for (String param : parameters) {
                urlQuery += param + "&";
            }

            urlQuery = urlQuery.substring(0, urlQuery.length() - 1);
        }

        urlQuery = urlQuery.replace(" ", "_");
        return urlQuery;
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
                "category=" + category +
                ", string_query='" + string_query + '\'' +
                ", community='" + community + '\'' +
                ", page_number=" + page_number +
                ", order_by=" + order_by +
                ", min_price=" + min_price +
                ", max_price=" + max_price +
                ", publish_at=" + publish_at +
                '}';
    }
}
