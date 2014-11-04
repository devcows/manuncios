package com.devcows.manuncios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.devcows.manuncios.models.Category;

import java.util.List;


public class CategoriesActivity extends DrawerActivity implements SearchView.OnQueryTextListener {

    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(new CategoriesFragment(), DrawerActivity.DRAWER_START_POSITION);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        SearchQuery searchQuery = SearchQuery.getInstance();
        searchQuery.setDefaultsValues();

        searchQuery.setString_query(s);

        Intent intent = new Intent(this, SearchOffersActivity.class);
        startActivity(intent);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_category, menu);

        // Associate searchable configuration with the SearchView
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        SearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.action_search:
                mSearchView.setIconified(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static class CategoriesFragment extends FragmentReturn implements CategoriesTaskListener {
        private final ApiMilAnuncios mApi = ApiMilAnuncios.getInstance();
        private CategoriesListAdapter mAdapter;
        private View rootView;
        private ProgressBar progressBar;

        public CategoriesFragment() {
        }

        @Override
        public String getReturnName() {
            return "a inicio";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final CategoriesTaskListener self = this;
            this.context = getActivity();
            this.rootView = inflater.inflate(R.layout.activity_categories, container, false);

            LinearLayout empty = (LinearLayout) rootView.findViewById(R.id.empty_list);
            empty.setVisibility(View.GONE);

            progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            mAdapter = new CategoriesListAdapter(context, mApi.getCategories());

            ListView listview = (ListView) rootView.findViewById(R.id.category_lst);
            listview.setAdapter(mAdapter);

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, final View view,
                                        int position, long id) {
                    final Category item = (Category) parent.getItemAtPosition(position);

                    SearchQuery searchQuery = SearchQuery.getInstance();
                    searchQuery.setDefaultsValues();
                    searchQuery.setCategory(item);

                    Intent intent = new Intent(context, SearchOffersActivity.class);
                    startActivity(intent);
                }
            });

            Button btn_retry = (Button) rootView.findViewById(R.id.btn_retry);
            btn_retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LinearLayout empty = (LinearLayout) rootView.findViewById(R.id.empty_list);
                    empty.setVisibility(View.GONE);

                    progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);
                    progressBar.setIndeterminate(true);
                    progressBar.setVisibility(View.VISIBLE);

                    CategoriesGetTask task = new CategoriesGetTask();
                    task.execute(self);
                }
            });

            CategoriesGetTask task = new CategoriesGetTask();
            task.execute(self);

            return rootView;
        }

        @Override
        public void onCategoriesGetResult(List<Category> categories) {
            progressBar.setVisibility(View.INVISIBLE);

            mAdapter.setObjects(categories);
            mAdapter.notifyDataSetChanged();

            if (mAdapter.isEmpty()) {
                LinearLayout empty = (LinearLayout) rootView.findViewById(R.id.empty_list);
                empty.setVisibility(View.VISIBLE);
            }
        }
    }
}




