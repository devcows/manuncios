package com.devcows.manuncios;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.devcows.manuncios.models.Offer;
import com.devcows.manuncios.other_controls.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;


public class SearchOffersActivity extends DrawerActivity {
    static final int CLICK_SEARCH_ADVANCED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(new SearchOffersFragment(), -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_search_offers, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SearchQuery searchQuery = SearchQuery.getInstance();
        Integer order_by = searchQuery.getOrder_by();

        if (order_by != null) {
            menu.findItem(R.id.menuSortDateRecent).setChecked(order_by == SearchQuery.ORDER_BY_RECENT);
            menu.findItem(R.id.menuSortDateOld).setChecked(order_by == SearchQuery.ORDER_BY_OLD);
            menu.findItem(R.id.menuSortPriceCheap).setChecked(order_by == SearchQuery.ORDER_BY_CHEAP);
            menu.findItem(R.id.menuSortPriceExpensive).setChecked(order_by == SearchQuery.ORDER_BY_EXPENSIVE);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private boolean createNewSearch(int btnId) {
        SearchQuery searchQuery = SearchQuery.getInstance();

        switch (btnId) {
            case R.id.menuSortDateRecent:
                searchQuery.setOrder_by(SearchQuery.ORDER_BY_RECENT);
                break;
            case R.id.menuSortDateOld:
                searchQuery.setOrder_by(SearchQuery.ORDER_BY_OLD);
                break;
            case R.id.menuSortPriceExpensive:
                searchQuery.setOrder_by(SearchQuery.ORDER_BY_EXPENSIVE);
                break;
            case R.id.menuSortPriceCheap:
                searchQuery.setOrder_by(SearchQuery.ORDER_BY_CHEAP);
                break;
            case R.id.action_search:
                break;
        }

        Intent intent = new Intent(getBaseContext(), SearchOffersActivity.class);
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
            case R.id.menuSortDateRecent:
            case R.id.menuSortDateOld:
            case R.id.menuSortPriceExpensive:
            case R.id.menuSortPriceCheap:
                return createNewSearch(id);
            case R.id.action_search:
                Intent intent = new Intent(getBaseContext(), AdvancedSearchActivity.class);

                startActivityForResult(intent, CLICK_SEARCH_ADVANCED);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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


    public static class SearchOffersFragment extends Fragment implements SearchOffersTaskListener {
        private SearchOffersListAdapter adapter;
        private ProgressBar progressBar;

        public SearchOffersFragment() {
            SearchOffersGetTask offerTask = new SearchOffersGetTask();
            offerTask.execute(this);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_search_offers, container, false);

            progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            // Set description into TextView
            final ListView listview = (ListView) rootView.findViewById(R.id.offer_lst);

            adapter = new SearchOffersListAdapter(getActivity(), new ArrayList<Offer>());
            listview.setAdapter(adapter);

            SearchQuery searchQuery = SearchQuery.getInstance();
            Intent intent = getActivity().getIntent();
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                searchQuery.setString_query(intent.getStringExtra(SearchManager.QUERY));
            }

            String strTitle = "";
            if (searchQuery.getCategory() != null) {
                strTitle = searchQuery.getCategory().getName();

                if (searchQuery.getCategory().getName().length() > 20) {
                    strTitle = searchQuery.getCategory().getName().substring(0, 20);
                    strTitle += "...";
                }
            }

            if (searchQuery.getString_query() != null && searchQuery.getString_query().length() > 0) {
                strTitle = searchQuery.getString_query();

                if (searchQuery.getString_query().length() > 20) {
                    strTitle = searchQuery.getString_query().substring(0, 20);
                    strTitle += "...";
                }
            }

            if (strTitle.length() > 0) {
                getActivity().getActionBar().setTitle(strTitle);
            }

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final Offer item = (Offer) parent.getItemAtPosition(position);

                    Intent intent = new Intent(getActivity(), OfferActivity.class);

                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable(Utils.SELECTED_OFFER, item);
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

            return rootView;
        }

        private void customLoadMoreOffers(int page) {
            SearchQuery searchQuery = SearchQuery.getInstance();
            searchQuery.setPage_number(page);

            SearchOffersGetTask offerTask = new SearchOffersGetTask();
            offerTask.execute(this);
        }


        @Override
        public void onOffersGetResult(List<Offer> offers) {
            progressBar.setVisibility(View.INVISIBLE);
            adapter.addAllObjects(offers);

            // fire the event
            adapter.notifyDataSetChanged();
        }

    }
}
