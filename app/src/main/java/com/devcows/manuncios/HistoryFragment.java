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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.devcows.manuncios.persistent.MyHistory;

import java.util.List;

public class HistoryFragment extends FragmentReturn {
    private ArrayAdapter mAdapter;
    private View rootView;

    public HistoryFragment() {
    }

    @Override
    public String getReturnName() {
        return "a historial";
    }

    private List<String> getHistoryList() {
        MyHistory myHistory = MyHistory.getInstance();
        List<String> list = myHistory.getHistoryList();

        return list;
    }

    private void setVisibleEmptyList() {
        LinearLayout empty_list = (LinearLayout) rootView.findViewById(R.id.empty_list);

        if (mAdapter.isEmpty()) {
            empty_list.setVisibility(View.VISIBLE);
        } else {
            empty_list.setVisibility(View.GONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_history_list, container, false);
        this.context = getActivity();

        // Set the adapter
        mAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, getHistoryList());
        ListView listview = (ListView) rootView.findViewById(R.id.history_lst);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);

                SearchQuery searchQuery = SearchQuery.getInstance();
                searchQuery.setDefaultsValues();
                searchQuery.setString_query(item);

                Intent intent = new Intent(context, SearchOffersActivity.class);
                startActivity(intent);
                ((Activity) context).finish();
            }
        });

        setVisibleEmptyList();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter.clear();
        for (String text : getHistoryList()) {
            mAdapter.add(text);
        }

        //mAdapter.addAll(getHistoryList());

        // fire the event
        mAdapter.notifyDataSetChanged();

        setVisibleEmptyList();
    }
}
