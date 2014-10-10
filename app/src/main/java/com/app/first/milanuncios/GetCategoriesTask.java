package com.app.first.milanuncios;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCategoriesTask extends AsyncTask<Activity, Void, Void> {
    public GetCategoriesTask() {}

    private List<Category> categories = new ArrayList<Category>();
    private Activity mainActivity;

    @Override
    protected Void doInBackground(Activity... activities) {
        this.mainActivity = activities[0];

        try {
            Document doc = Jsoup.connect("http://www.milanuncios.com").get();
            String title = doc.title();

            Elements rows = doc.select("div.filaCat");

        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: GET A LA WEB
        Category c1 = new Category("Motor", null, "http://www.milanuncios.com/motor");
        categories.add(c1);
        Category c2 = new Category("Empleo", null, "http://www.milanuncios.com/ofertas-de-empleo");
        categories.add(c2);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // Set description into TextView

        final ListView listview = (ListView) mainActivity.findViewById(R.id.category_lst);
        ArrayList<String> list = new ArrayList<String>();
        for (Category cat : categories) {
            list.add(cat.getName());
        }

        CategoryArrayAdapter adapter = new CategoryArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                //categories[position] =>  access to categories with index.

            }
        });
    }
}
