package com.app.first.milanuncios;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class SearchOffersActivity extends Activity implements SearchOffersTaskListener {
    private SearchOffersListAdapter adapter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offers);

        // Set description into TextView
        final ListView listview = (ListView) findViewById(R.id.offer_lst);

        adapter = new SearchOffersListAdapter(this, new ArrayList<Offer>());
        listview.setAdapter(adapter);


        TextView label = (TextView) findViewById(R.id.offer_label);
        SearchOffersGetTask offerTask = new SearchOffersGetTask();

        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            label.setText("Search => " + query);
            offerTask.setQuerySearch(query);
        } else {
            if (intent.hasExtra("selected_category")) {
                Category selectedCategory = (Category) intent.getSerializableExtra("selected_category");
                offerTask.setCategory(selectedCategory);
                label.setText(selectedCategory.getName() + " => " + selectedCategory.getUrl());
            }
        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Offer item = (Offer) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), OfferActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("selected_offer", item);
                intent.putExtras(mBundle);

                startActivity(intent);
            }
        });

        offerTask.execute(this);
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
        progressBar.setVisibility(View.INVISIBLE);
        adapter.setObjects(offers);

        // fire the event
        adapter.notifyDataSetChanged();
    }
}
