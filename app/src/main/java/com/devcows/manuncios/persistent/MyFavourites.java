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
        super("favourites.bin");
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

    public boolean containsFavourite(Offer offer) {
        boolean bok = false;

        if (offer != null) {
            String offerId = offer.getId();
            bok = containsObject(offerId);
        }

        return bok;
    }
}
