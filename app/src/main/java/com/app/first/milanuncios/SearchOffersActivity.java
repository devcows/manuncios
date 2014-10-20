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

    //search parameters
    private Category category = null;
    private String string_query = null;
    private int page_number = 1;

    private boolean most_recent = false;
    private boolean most_old = false;
    private boolean most_expensive = false;
    private boolean most_cheap = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_offers);

        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        // Set description into TextView
        final ListView listview = (ListView) findViewById(R.id.offer_lst);

        adapter = new SearchOffersListAdapter(this, new ArrayList<Offer>());
        listview.setAdapter(adapter);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            string_query = intent.getStringExtra(SearchManager.QUERY);
        }

        if (intent.hasExtra("string_query")) {
            string_query = (String) intent.getSerializableExtra("string_query");
        }

        if (intent.hasExtra("selected_category")) {
            category = (Category) intent.getSerializableExtra("selected_category");
        }

        if (intent.hasExtra("most_recent")) {
            most_recent = (Boolean) intent.getSerializableExtra("most_recent");
        }

        if (intent.hasExtra("most_old")) {
            most_recent = (Boolean) intent.getSerializableExtra("most_old");
        }

        if (intent.hasExtra("most_expensive")) {
            most_recent = (Boolean) intent.getSerializableExtra("most_expensive");
        }

        if (intent.hasExtra("most_cheap")) {
            most_recent = (Boolean) intent.getSerializableExtra("most_cheap");
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

        listview.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreOffers(page);
            }
        });

        doSearch();
    }


    private void customLoadMoreOffers(int page) {
        this.page_number = page;
        doSearch();
    }

    private void doSearch() {
        //TODO Kill offertask if the activity finish.
        SearchOffersGetTask offerTask = new SearchOffersGetTask();

        TextView label = (TextView) findViewById(R.id.offer_label);
        if (category != null) {
            label.setText(category.getName() + " => " + category.getUrl());
            offerTask.setCategory(category);
        }

        if (string_query != null && !string_query.isEmpty()) {
            label.setText("Search => " + string_query);
            offerTask.setQuerySearch(string_query);
        }

        offerTask.setMost_cheap(most_cheap);
        offerTask.setMost_expensive(most_expensive);
        offerTask.setMost_old(most_old);
        offerTask.setMost_recent(most_recent);

        offerTask.setPage(page_number);
        offerTask.execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_search_offers, menu);

        menu.findItem(R.id.menuSortDateOld).setChecked(most_old);
        menu.findItem(R.id.menuSortDateRecent).setChecked(most_recent);
        menu.findItem(R.id.menuSortPriceCheap).setChecked(most_cheap);
        menu.findItem(R.id.menuSortPriceExpensive).setChecked(most_expensive);

        return true;
    }


    private boolean createNewSearch(int btnId){
        Bundle mBundle = new Bundle();

        switch (btnId) {
            case R.id.menuSortDateRecent:
                mBundle.putSerializable("most_recent", true);
            case R.id.menuSortDateOld:
                mBundle.putSerializable("most_old", true);
            case R.id.menuSortPriceExpensive:
                mBundle.putSerializable("most_expensive", true);
            case R.id.menuSortPriceCheap:
                mBundle.putSerializable("most_cheap", true);
            case R.id.action_search:
        }

        if(string_query != null){
            mBundle.putSerializable("string_query", string_query);
        }

        if(category != null){
            mBundle.putSerializable("selected_category", category);
        }

        Intent intent = new Intent(getBaseContext(), SearchOffersActivity.class);
        intent.putExtras(mBundle);

        startActivity(intent);
        finish();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.menuSortDateRecent:
                return createNewSearch(id);
            case R.id.menuSortDateOld:
                return createNewSearch(id);
            case R.id.menuSortPriceExpensive:
                return createNewSearch(id);
            case R.id.menuSortPriceCheap:
                return createNewSearch(id);
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onOffersGetResult(List<Offer> offers) {
        progressBar.setVisibility(View.INVISIBLE);
        adapter.addAllObjects(offers);

        // fire the event
        adapter.notifyDataSetChanged();
    }
}
