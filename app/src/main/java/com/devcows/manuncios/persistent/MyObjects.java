package com.devcows.manuncios.persistent;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MyObjects<T> {
    private HashMap<String, T> objects;
    private String FILE_NAME;

    private static Context context = null;

    public static void setContext(Context context) {
        MyObjects.context = context;
    }

    public HashMap<String, T> getObjects() {
        return this.objects;
    }

    public MyObjects(String file_name) {
        //search and save to file.
        this.FILE_NAME = file_name;

        this.objects = new LinkedHashMap<String, T>();
        try {
            readFile();
        } catch (Exception ex) {
            //TODO put message notification
            ex.printStackTrace(); //can't load favourites
        }

    }

    private void readFile() throws IOException, ClassNotFoundException {
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (file.exists()) {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            this.objects = (HashMap<String, T>) is.readObject();
            is.close();
        }
    }

    private void dumpToFile() {
        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(objects);
            os.close();
        } catch (Exception ex) {
            //TODO put message notification
            ex.printStackTrace(); //can't save favourite
        }
    }

    protected void addObject(String key, T object) {

        if (object != null && key != null) {
            objects.put(key, object);
        }

        dumpToFile();
    }

    protected void delObject(String key) {

        if (key != null) {
            objects.remove(key);
        }

        dumpToFile();
    }

    protected boolean containsObject(String key) {
        boolean bok = false;

        if (key != null && objects.containsKey(key)) {
            bok = true;
        }

        return bok;
    }

}
