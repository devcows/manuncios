package com.devcows.manuncios;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.devcows.manuncios.models.Category;
import com.devcows.manuncios.models.Favourite;
import com.devcows.manuncios.models.Offer;
import com.devcows.manuncios.persistent.MyFavourites;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testApi() throws Exception {
        ApiMilAnuncios api = ApiMilAnuncios.getInstance();
    }

    public void testFavourites() {
        Context context = getContext();

        MyFavourites.setContext(context);
        MyFavourites myFavourites = MyFavourites.getInstance();

        Offer o = new Offer();
        o.setId("test_id");

        Favourite f = new Favourite(o);
        myFavourites.addFavourite(f);

        Log.d("testFavourites", "Element added");
        for (Offer offer : myFavourites.getFavouriteOffersList()) {
            Log.d("testFavourites", "=> " + offer.toString());
        }

        myFavourites.delFavourite(f);
        Log.d("testFavourites", "Element removed");
        for (Offer offer : myFavourites.getFavouriteOffersList()) {
            Log.d("testFavourites", "=> " + offer.toString());
        }

    }

    public void testSearchOfferGetTask() throws Exception {
        Category item = new Category();
        item.setName("Motor");
        item.setUrl("http://www.milanuncios.com/motor/");

        SearchQuery searchQuery = SearchQuery.getInstance();
        searchQuery.setDefaultsValues();
        searchQuery.setCategory(item);

        SearchOffersGetTask offerTask = new SearchOffersGetTask();
        List<Offer> listOffers = offerTask.doInBackground();

        for (Offer o : listOffers) {
            Log.d("testSearchOfferGetTask", "=> " + o.toString());
        }

        Log.d("testSearchOfferGetTask", "Finish!");
    }

    public void testCapitalizeString() throws Exception{

        String text = Utils.capitalizeString(null);
        Log.d("testCapitalizeString", "=> " + text);

        text = Utils.capitalizeString("h");
        Log.d("testCapitalizeString", "=> " + text);

        text = Utils.capitalizeString("hola");
        Log.d("testCapitalizeString", "=> " + text);

        text = Utils.capitalizeString("ébola");
        Log.d("testCapitalizeString", "=> " + text);

    }
}