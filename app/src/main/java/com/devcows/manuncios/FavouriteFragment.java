package com.devcows.manuncios;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.devcows.manuncios.models.Favourite;
import com.devcows.manuncios.models.Offer;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends FragmentReturn {
    private SearchOffersListAdapter mAdapter;

    public FavouriteFragment() {
    }

    @Override
    public String getReturnName() {
        return "a favoritos";
    }

    private List<Offer> getFavouritesOffers() {
        MyFavourites myFavourites = MyFavourites.getInstance();
        List<Offer> offers = new ArrayList<Offer>();
        for (Favourite fa : myFavourites.getFavourites().values()) {
            offers.add(fa.getOffer());
        }

        return offers;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new SearchOffersListAdapter(getActivity(), getFavouritesOffers());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_list, container, false);

        // Set the adapter
        ListView listview = (ListView) view.findViewById(R.id.offer_lst);
        listview.setAdapter(mAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Offer item = (Offer) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), OfferActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Utils.SELECTED_OFFER, item);
                intent.putExtras(mBundle);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mAdapter.setObjects(getFavouritesOffers());

        // fire the event
        mAdapter.notifyDataSetChanged();
    }
}
