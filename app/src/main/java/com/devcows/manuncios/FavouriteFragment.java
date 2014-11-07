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

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.devcows.manuncios.models.Offer;
import com.devcows.manuncios.persistent.MyFavourites;

import java.util.List;

public class FavouriteFragment extends FragmentReturn {
    private SearchOffersListAdapter mAdapter;
    private View rootView;

    public FavouriteFragment() {
    }

    @Override
    public String getReturnName() {
        return "a favoritos";
    }

    private List<Offer> getFavouritesOffers() {
        MyFavourites myFavourites = MyFavourites.getInstance();
        List<Offer> offers = myFavourites.getFavouriteOffersList();

        return offers;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_favourite_list, container, false);
        this.context = getActivity();

        // Set the adapter
        mAdapter = new SearchOffersListAdapter(context, getFavouritesOffers());
        ListView listview = (ListView) rootView.findViewById(R.id.offer_lst);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Offer item = (Offer) parent.getItemAtPosition(position);

                Intent intent = new Intent(context, OfferActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Utils.SELECTED_OFFER, item);
                intent.putExtras(mBundle);

                startActivity(intent);
            }
        });

        setVisibleEmptyList();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter.setObjects(getFavouritesOffers());

        // fire the event
        mAdapter.notifyDataSetChanged();

        setVisibleEmptyList();
    }
}
