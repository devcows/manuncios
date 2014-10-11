package com.app.first.milanuncios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class OfferActivity extends Activity implements OffersTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Intent intent = getIntent();
        Category selectedCategory = (Category) intent.getSerializableExtra("selected_category");

        TextView label = (TextView) findViewById(R.id.offer_label);
        label.setText(selectedCategory.getName() + " => " + selectedCategory.getUrl());

        new OffersGetTask(selectedCategory).execute(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.offer, menu);
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
    public void onOffersGetResult(List<Offer> offers) {
        // Set description into TextView
        final ListView listview = (ListView) findViewById(R.id.offer_lst);

        OffersListAdapter adapter = new OffersListAdapter(this, offers);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
//                final Category item = (Category) parent.getItemAtPosition(position);
//
//                Intent intent = new Intent(getBaseContext(), OfferActivity.class);
//
//                Bundle mBundle = new Bundle();
//                mBundle.putSerializable("selected_category", item);
//                intent.putExtras(mBundle);
//
//                startActivity(intent);
            }
        });
    }
}
