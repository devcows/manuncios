//Copyright (C) 2014  Guillermo G. (info@devcows.com)
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.devcows.manuncios;

import android.app.Activity;
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


    public static class SearchOffersFragment extends FragmentReturn implements SearchOffersTaskListener {
        private SearchOffersListAdapter adapter;
        private ProgressBar progressBar;

        public SearchOffersFragment() {
        }

        @Override
        public String getReturnName() {
            return "al listado";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_search_offers, container, false);
            this.context = getActivity();

            progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            // Set description into TextView
            final ListView listview = (ListView) rootView.findViewById(R.id.offer_lst);

            adapter = new SearchOffersListAdapter(context, new ArrayList<Offer>());
            listview.setAdapter(adapter);

            SearchQuery searchQuery = SearchQuery.getInstance();
            Intent intent = ((Activity) context).getIntent();
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                searchQuery.setDefaultsValues();
                searchQuery.setString_query(intent.getStringExtra(SearchManager.QUERY));
            }

            SearchOffersGetTask offerTask = new SearchOffersGetTask();
            offerTask.execute(this);

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
                super.getActivity().setTitle(strTitle);
                ((DrawerActivity) super.getActivity()).setDrawerTitle(strTitle);
            }

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final Offer item = (Offer) parent.getItemAtPosition(position);

                    Intent intent = new Intent(context, OfferActivity.class);

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
