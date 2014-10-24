package com.devcows.manuncios;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.devcows.manuncios.ApiMilAnuncios;
import com.devcows.manuncios.Category;
import com.devcows.manuncios.Offer;
import com.devcows.manuncios.SearchOffersGetTask;

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

        SearchOffersGetTask offerTask = new SearchOffersGetTask();
        offerTask.setCategory(item);
        List<Offer> listOffers = offerTask.doInBackground();


        for(Offer o: listOffers){
            Log.d("testSearchOfferGetTask", "=> " + o.toString());
        }

        Log.d("testSearchOfferGetTask", "Finish!");
    }
}