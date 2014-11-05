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
