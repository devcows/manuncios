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

import com.app.first.milanuncios.other_controls.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;


public class SearchOffersActivity extends Activity implements SearchOffersTaskListener {
    static final int CLICK_SEARCH_ADVANCED = 1;

    private SearchOffersListAdapter adapter;
    private ProgressBar progressBar;

    //search parameters
    private SearchQuery searchQuery;


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

        searchQuery = new SearchQuery(getIntent());


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
        searchQuery.setPage_number(page);
        doSearch();
    }

    private void doSearch() {
        //TODO Kill offertask if the activity finish.
        SearchOffersGetTask offerTask = new SearchOffersGetTask();
        offerTask.setSearchQuery(searchQuery);
        offerTask.execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_search_offers, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Integer order_by = searchQuery.getOrder_by();

        menu.findItem(R.id.menuSortDateRecent).setChecked(order_by == Utils.ORDER_BY_RECENT);
        menu.findItem(R.id.menuSortDateOld).setChecked(order_by == Utils.ORDER_BY_OLD);
        menu.findItem(R.id.menuSortPriceCheap).setChecked(order_by == Utils.ORDER_BY_CHEAP);
        menu.findItem(R.id.menuSortPriceExpensive).setChecked(order_by == Utils.ORDER_BY_EXPENSIVE);

        return super.onPrepareOptionsMenu(menu);
    }

    private boolean createNewSearch(int btnId) {
        Bundle mBundle = new Bundle();

        switch (btnId) {
            case R.id.menuSortDateRecent:
                searchQuery.setOrder_by(Utils.ORDER_BY_RECENT);
                break;
            case R.id.menuSortDateOld:
                searchQuery.setOrder_by(Utils.ORDER_BY_OLD);
                break;
            case R.id.menuSortPriceExpensive:
                searchQuery.setOrder_by(Utils.ORDER_BY_EXPENSIVE);
                break;
            case R.id.menuSortPriceCheap:
                searchQuery.setOrder_by(Utils.ORDER_BY_CHEAP);
                break;
            case R.id.action_search:
                break;
        }

        mBundle.putSerializable(Utils.SEARCH_QUERY, searchQuery);

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
            case R.id.menuSortDateOld:
            case R.id.menuSortPriceExpensive:
            case R.id.menuSortPriceCheap:
                return createNewSearch(id);
            case R.id.action_search:
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Utils.SEARCH_QUERY, searchQuery);

                Intent intent = new Intent(getBaseContext(), AdvancedSearchActivity.class);
                intent.putExtras(mBundle);

                startActivityForResult(intent, CLICK_SEARCH_ADVANCED);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CLICK_SEARCH_ADVANCED) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
