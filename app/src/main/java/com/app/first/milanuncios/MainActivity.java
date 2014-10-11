package com.app.first.milanuncios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements CategoriesTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetCategoriesTask().execute(this);
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

    @Override
    public void onGetCategoriesResult(List<Category> categories) {
        // Set description into TextView
        final ListView listview = (ListView) findViewById(R.id.category_lst);
        ArrayList<String> list = new ArrayList<String>();
        for (Category cat : categories) {
            list.add(cat.getName());
        }

        CategoriesListAdapter adapter = new CategoriesListAdapter(this, categories);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Category item = (Category) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), OfferActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("selected_category", item);
                intent.putExtras(mBundle);

                startActivity(intent);
            }
        });
    }
}




