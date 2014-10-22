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
    private Category category = null;
    private String string_query = null;
    private int page_number = 1;

    private int order_by;


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

        if (intent.hasExtra(Utils.STRING_QUERY)) {
            string_query = (String) intent.getSerializableExtra(Utils.STRING_QUERY);
        }

        if (intent.hasExtra(Utils.SELECTED_CATEGORY)) {
            category = (Category) intent.getSerializableExtra(Utils.SELECTED_CATEGORY);
        }

        if(intent.hasExtra(Utils.ORDER_BY)){
            order_by = (Integer) intent.getSerializableExtra(Utils.ORDER_BY);
        } else{
            order_by = Utils.ORDER_BY_RECENT;
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

        if (category != null) {
            offerTask.setCategory(category);
        }

        if (string_query != null && !string_query.isEmpty()) {
            offerTask.setQuerySearch(string_query);
        }

        offerTask.setOrder_by(order_by);
        offerTask.setPage(page_number);
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

        menu.findItem(R.id.menuSortDateRecent).setChecked(order_by == Utils.ORDER_BY_RECENT);
        menu.findItem(R.id.menuSortDateOld).setChecked(order_by == Utils.ORDER_BY_OLD);
        menu.findItem(R.id.menuSortPriceCheap).setChecked(order_by == Utils.ORDER_BY_CHEAP);
        menu.findItem(R.id.menuSortPriceExpensive).setChecked(order_by == Utils.ORDER_BY_EXPENSIVE);

        return super.onPrepareOptionsMenu(menu);
    }

    private Bundle createBundle() {
        Bundle mBundle = new Bundle();

        mBundle.putSerializable(Utils.ORDER_BY, order_by);

        if (string_query != null) {
            mBundle.putSerializable(Utils.STRING_QUERY, string_query);
        }

        if (category != null) {
            mBundle.putSerializable(Utils.SELECTED_CATEGORY, category);
        }

        return mBundle;
    }

    private boolean createNewSearch(int btnId) {
        Bundle mBundle = createBundle();

        switch (btnId) {
            case R.id.menuSortDateRecent:
                order_by = Utils.ORDER_BY_RECENT;
                break;
            case R.id.menuSortDateOld:
                order_by = Utils.ORDER_BY_OLD;
                break;
            case R.id.menuSortPriceExpensive:
                order_by = Utils.ORDER_BY_EXPENSIVE;
                break;
            case R.id.menuSortPriceCheap:
                order_by = Utils.ORDER_BY_CHEAP;
                break;
            case R.id.action_search:
                break;
        }

        mBundle.putSerializable(Utils.ORDER_BY, order_by);

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
                Bundle mBundle = createBundle();

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
