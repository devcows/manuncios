package com.devcows.manuncios;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
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
import android.widget.SearchView;

import com.devcows.manuncios.models.Category;


public class CategoriesActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(new CategoriesFragment(), DrawerActivity.DRAWER_START_POSITION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_category, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView SearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }


    public static class CategoriesFragment extends Fragment {
        private final ApiMilAnuncios mApi = ApiMilAnuncios.getInstance();
        private CategoriesListAdapter mAdapter;

        public CategoriesFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            mAdapter = new CategoriesListAdapter(getActivity(), mApi.getCategories());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_categories, container, false);
            final Context context = rootView.getContext();

            ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            ListView listview = (ListView) rootView.findViewById(R.id.category_lst);
            listview.setAdapter(mAdapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final Category item = (Category) parent.getItemAtPosition(position);

                    SearchQuery searchQuery = SearchQuery.getInstance();
                    searchQuery.setCategory(item);

                    Intent intent = new Intent(context, SearchOffersActivity.class);
                    startActivity(intent);
                }
            });

            progressBar.setVisibility(View.INVISIBLE);

            return rootView;
        }

    }
}




