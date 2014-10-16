package com.app.first.milanuncios;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Base64;
import android.util.Log;

import java.io.*;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
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

    /** Write the object to a Base64 string. */
    private static String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
    }
}