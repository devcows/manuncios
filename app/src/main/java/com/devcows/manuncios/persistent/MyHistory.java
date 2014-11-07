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

package com.devcows.manuncios.persistent;

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
        super("history.bin");
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

        if (getObjects().size() > 20) {
            String key = (String) getObjects().keySet().toArray()[0];
            delQuery(key);
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
