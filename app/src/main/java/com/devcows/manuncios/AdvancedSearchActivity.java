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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.devcows.manuncios.models.Category;

import java.util.ArrayList;
import java.util.List;


public class AdvancedSearchActivity extends DrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showFragment(new AdvancedSearchFragment(), -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_advanced_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED, returnIntent);
    }


    public static class AdvancedSearchFragment extends FragmentReturn {
        private ProgressBar progressBar;

        public AdvancedSearchFragment() {
        }

        @Override
        public String getReturnName() {
            return "al buscador";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.activity_advanced_search, container, false);
            this.context = getActivity();

            progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            SearchQuery searchQuery = SearchQuery.getInstance();

            //TODO change the arguments to receive activity and pass fragment arguments.
            Intent intent = ((Activity) context).getIntent();
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                searchQuery.setString_query(intent.getStringExtra(SearchManager.QUERY));
            }

            if (searchQuery.getString_query() != null && !searchQuery.getString_query().isEmpty()) {
                EditText txtSearch = (EditText) rootView.findViewById(R.id.txt_string_query);
                txtSearch.setText(searchQuery.getString_query());
            }

            ApiMilAnuncios api = ApiMilAnuncios.getInstance();
            List<Category> categories = new ArrayList<Category>(api.getCategories());
            Category all = new Category();
            all.setPosition(0);
            all.setName("Todas");
            categories.add(0, all);

            final ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(context, android.R.layout.simple_spinner_dropdown_item, categories);

            Spinner spn_categories = (Spinner) rootView.findViewById(R.id.spn_categories);
            spn_categories.setAdapter(categoryArrayAdapter);

            if (searchQuery.getCategory() != null) {
                spn_categories.setSelection(searchQuery.getCategory().getPosition());
            }


            Spinner spn_order_by = (Spinner) rootView.findViewById(R.id.spn_order_by);
            spn_order_by.setSelection(searchQuery.getOrder_by());

            if (searchQuery.getMin_price() != null) {
                TextView txtMinPrice = (TextView) rootView.findViewById(R.id.txt_min_price);
                txtMinPrice.setText(searchQuery.getMin_price().toString());
            }

            if (searchQuery.getMax_price() != null) {
                TextView txtMaxPrice = (TextView) rootView.findViewById(R.id.txt_max_price);
                txtMaxPrice.setText(searchQuery.getMax_price().toString());
            }

            //map_published_at
            int selectedPublish_atPosition = 0;
            Spinner spn_publish_at = (Spinner) rootView.findViewById(R.id.spn_publish_at);
            ArrayList<String> publish_values = new ArrayList<String>();
            for (int i = 0; i < SearchQuery.map_published_at.keySet().size(); i++) {
                String str = (String) SearchQuery.map_published_at.keySet().toArray()[i];
                publish_values.add(str);
                if (searchQuery.map_published_at.get(str) == searchQuery.getPublish_at()) {
                    selectedPublish_atPosition = i;
                }
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, publish_values); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_publish_at.setAdapter(spinnerArrayAdapter);
            spn_publish_at.setSelection(selectedPublish_atPosition);

            //map communities
            Spinner spn_communities = (Spinner) rootView.findViewById(R.id.spn_communities);
            ArrayList<String> communities_values = new ArrayList<String>();

            int selectedCommunityPosition = 0;
            for (int i = 0; i < api.getCommunities().keySet().size(); i++) {
                String str = (String) api.getCommunities().keySet().toArray()[i];
                communities_values.add(str);

                if (searchQuery.getCommunity().compareTo(api.getCommunities().get(str)) == 0) {
                    selectedCommunityPosition = i;
                }
            }

            ArrayAdapter<String> spinnerCommunitiesArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, communities_values); //selected item will look like a spinner set from XML
            spinnerCommunitiesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_communities.setAdapter(spinnerCommunitiesArrayAdapter);
            spn_communities.setSelection(selectedCommunityPosition);


            Button btnSearch = (Button) rootView.findViewById(R.id.btn_search);
            btnSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SearchQuery searchQuery = SearchQuery.getInstance();
                    searchQuery.setDefaultsValues();

                    EditText txtSearch = (EditText) rootView.findViewById(R.id.txt_string_query);
                    if (!txtSearch.getText().toString().isEmpty()) {
                        searchQuery.setString_query(txtSearch.getText().toString());
                    }

                    Spinner spn_categories = (Spinner) rootView.findViewById(R.id.spn_categories);

                    if (spn_categories.getSelectedItemPosition() > 0) {
                        Category category = categoryArrayAdapter.getItem(spn_categories.getSelectedItemPosition());
                        searchQuery.setCategory(category);
                    }

                    Spinner spn_order_by = (Spinner) rootView.findViewById(R.id.spn_order_by);
                    searchQuery.setOrder_by(spn_order_by.getSelectedItemPosition());

                    EditText txtMinPrice = (EditText) rootView.findViewById(R.id.txt_min_price);
                    if (!txtMinPrice.getText().toString().isEmpty()) {
                        searchQuery.setMin_price(Integer.parseInt(txtMinPrice.getText().toString()));
                    }

                    EditText txtMaxPrice = (EditText) rootView.findViewById(R.id.txt_max_price);
                    if (!txtMaxPrice.getText().toString().isEmpty()) {
                        searchQuery.setMax_price(Integer.parseInt(txtMaxPrice.getText().toString()));
                    }

                    Spinner spn_publish_at = (Spinner) rootView.findViewById(R.id.spn_publish_at);
                    Integer publish_at = SearchQuery.map_published_at.get(spn_publish_at.getSelectedItem());
                    searchQuery.setPublish_at(publish_at);


                    Spinner spn_community = (Spinner) rootView.findViewById(R.id.spn_communities);
                    ApiMilAnuncios api = ApiMilAnuncios.getInstance();
                    String community = api.getCommunities().get(spn_community.getSelectedItem());
                    searchQuery.setCommunity(community);


                    Intent intent = new Intent(context, SearchOffersActivity.class);
                    startActivity(intent);

                    Intent returnIntent = new Intent();
                    ((Activity) context).setResult(RESULT_OK, returnIntent);

                    ((Activity) context).finish();
                }
            });

            progressBar.setVisibility(View.INVISIBLE);

            return rootView;
        }

    }
}
