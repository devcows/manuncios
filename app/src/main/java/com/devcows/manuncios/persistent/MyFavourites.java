package com.devcows.manuncios.persistent;

import com.devcows.manuncios.models.Favourite;
import com.devcows.manuncios.models.Offer;

import java.util.ArrayList;
import java.util.List;

public class MyFavourites extends MyObjects<Favourite> {
    private static MyFavourites instance;

    public List<Offer> getFavouriteOffersList() {
        List<Offer> offers = new ArrayList<Offer>();

        Object[] array = getObjects().values().toArray();
        for (int i = array.length - 1; i >= 0; i--) {
            Favourite fa = (Favourite) array[i];
            offers.add(fa.getOffer());
        }

        return offers;
    }

    public MyFavourites() {
        super();
    }

    public static MyFavourites getInstance() {
        if (instance == null) {
            instance = new MyFavourites();
        }

        return instance;
    }


    public void addFavourite(Favourite favourite) {
        if (favourite != null && favourite.getOffer() != null) {
            String offerId = favourite.getOffer().getId();
            addObject(offerId, favourite);
        }
    }

    public void delFavourite(Favourite favourite) {
        if (favourite != null && favourite.getOffer() != null) {
            String offerId = favourite.getOffer().getId();
            delObject(offerId);
        }
    }

    public boolean containsFavourite(Favourite favourite) {
        boolean bok = false;

        if (favourite != null && favourite.getOffer() != null) {
            String offerId = favourite.getOffer().getId();
            bok = containsObject(offerId);
        }

        return bok;
    }
}
