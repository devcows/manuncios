package com.devcows.manuncios;

import android.content.Context;

import com.devcows.manuncios.models.Favourite;
import com.devcows.manuncios.models.Offer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MyFavourites {
    private final String FILE_NAME = "favourites.bin";
    private HashMap<String, Favourite> favourites;

    private static Context context = null;
    private static MyFavourites instance;


    public static void setContext(Context context) {
        MyFavourites.context = context;
    }

    public HashMap<String, Favourite> getFavourites() {
        return favourites;
    }

    public MyFavourites() {
        //search and save to file.

        favourites = new LinkedHashMap<String, Favourite>();
        try {
            readFile();
        } catch (Exception ex) {
            //TODO put message notification
            ex.printStackTrace(); //can't load favourites
        }

    }

    public static MyFavourites getInstance() {
        if (instance == null) {
            instance = new MyFavourites();
        }

        return instance;
    }


    private void readFile() throws IOException, ClassNotFoundException {
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (file.exists()) {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            favourites = (HashMap<String, Favourite>) is.readObject();
            is.close();
        }
    }

    private void dumpToFile() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(favourites);
            os.close();
        } catch (Exception ex) {
            //TODO put message notification
            ex.printStackTrace(); //can't save favourite
        }
    }

    public void addFavourite(Favourite favourite) {

        if (favourite != null && favourite.getOffer() != null) {
            String offerId = favourite.getOffer().getId();
            favourites.put(offerId, favourite);
        }

        dumpToFile();
    }

    public void delFavourite(Favourite favourite) {

        if (favourite != null && favourite.getOffer() != null) {
            String offerId = favourite.getOffer().getId();
            favourites.remove(offerId);
        }

        dumpToFile();
    }

    public boolean containsFavourite(Offer offer){
        boolean bok = false;

        if (favourites != null && favourites.containsKey(offer.getId())){
            bok = true;
        }

        return bok;
    }

}
