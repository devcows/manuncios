package com.devcows.manuncios;

import java.util.ArrayList;
import java.util.List;

public class MyHistory extends MyObjects<String> {
    private static MyHistory instance;


    public List<String> getHistoryList() {
        List<String> list = new ArrayList<String>();

        Object[] array = getObjects().values().toArray();
        for (int i = array.length - 1; i >= 0; i--) {
            String query = (String) array[i];
            list.add(query);
        }

        return list;
    }

    public MyHistory() {
        super();
    }

    public static MyHistory getInstance() {
        if (instance == null) {
            instance = new MyHistory();
        }

        return instance;
    }


    public void addQuery(String query) {
        if (query != null && !query.isEmpty()) {
            addObject(query, query);
        }
    }

    public void delQuery(String query) {
        if (query != null && !query.isEmpty()) {
            delObject(query);
        }
    }

    public boolean containsQuery(String query) {
        return containsObject(query);
    }

}
