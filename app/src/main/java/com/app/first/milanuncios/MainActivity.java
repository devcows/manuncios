package com.app.first.milanuncios;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private List<Category> categories = getCategories();


    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();

        //TODO: GET A LA WEB
        Category c1 = new Category("Motor", null, "http://www.milanuncios.com/motor");
        categories.add(c1);
        Category c2 = new Category("Empleo", null, "http://www.milanuncios.com/ofertas-de-empleo");
        categories.add(c2);

        return categories;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        final ListView listview = (ListView) findViewById(R.id.category_lst);
        final ArrayList<String> list = new ArrayList<String>();
        for (Category cat : categories) {
            list.add(cat.getName());
        }
        final CategoryArrayAdapter adapter = new CategoryArrayAdapter(this, android.R.layout.simple_list_item_1, list);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}




