package com.devcows.manuncios;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testApi() throws Exception{
        ApiMilAnuncios api = ApiMilAnuncios.getInstance();
    }

    public void testSearchOfferGetTask() throws Exception {
        Category item = new Category();
        item.setName("Motor");
        item.setUrl("http://www.milanuncios.com/motor/");

        SearchQuery searchQuery = SearchQuery.getInstance();
        searchQuery.setCategory(item);

        SearchOffersGetTask offerTask = new SearchOffersGetTask();
        List<Offer> listOffers = offerTask.doInBackground();

        for(Offer o: listOffers){
            Log.d("testSearchOfferGetTask", "=> " + o.toString());
        }

        Log.d("testSearchOfferGetTask", "Finish!");
    }
}