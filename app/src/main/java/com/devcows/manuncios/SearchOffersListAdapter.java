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
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.devcows.manuncios.models.Offer;

import java.util.List;


public class SearchOffersListAdapter extends BaseAdapter {

    private Context context;
    private List<Offer> objects;

    public SearchOffersListAdapter(Context context, List<Offer> objects) {
        this.context = context;
        this.objects = objects;
    }

    public void setObjects(List<Offer> objects) {
        this.objects = objects;
    }

    public void addAllObjects(List<Offer> objects) {
        this.objects.addAll(objects);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int position) {
        return objects.indexOf(getItem(position));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OfferAdapter adapter;
        Offer rowItem = (Offer) getItem(i);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_offer_list_item, null);
        }

        OfferHolder holder = new OfferHolder(view);
        adapter = new OfferAdapter(holder, rowItem, context);

        adapter.fillOffer(true);

        return view;
    }

}

