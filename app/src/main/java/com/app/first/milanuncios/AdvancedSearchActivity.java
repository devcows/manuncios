package com.app.first.milanuncios;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AdvancedSearchActivity extends Activity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        SearchQuery searchQuery = SearchQuery.getInstance();
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            searchQuery.setString_query(intent.getStringExtra(SearchManager.QUERY));
        }

        if (searchQuery.getString_query() != null && !searchQuery.getString_query().isEmpty()) {
            EditText txtSearch = (EditText) findViewById(R.id.txt_string_query);
            txtSearch.setText(searchQuery.getString_query());
        }

        ApiMilAnuncios api = ApiMilAnuncios.getInstance();
        List<Category> categories = new ArrayList<Category>(api.getCategories());
        Category all = new Category();
        all.setPosition(0);
        all.setName("Todas");
        categories.add(0, all);

        final ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        Spinner spn_categories = (Spinner) findViewById(R.id.spn_categories);
        spn_categories.setAdapter(categoryArrayAdapter);

        if (searchQuery.getCategory() != null) {
            spn_categories.setSelection(searchQuery.getCategory().getPosition());
        }


        Spinner spn_order_by = (Spinner) findViewById(R.id.spn_order_by);
        spn_order_by.setSelection(searchQuery.getOrder_by());

        if (searchQuery.getMin_price() != null) {
            TextView txtMinPrice = (TextView) findViewById(R.id.txt_min_price);
            txtMinPrice.setText(searchQuery.getMin_price().toString());
        }

        if (searchQuery.getMax_price() != null) {
            TextView txtMaxPrice = (TextView) findViewById(R.id.txt_max_price);
            txtMaxPrice.setText(searchQuery.getMax_price().toString());
        }

        //map_published_at
        int selectedPublish_atPosition = 0;
        Spinner spn_publish_at = (Spinner) findViewById(R.id.spn_publish_at);
        ArrayList<String> publish_values = new ArrayList<String>();
        for (int i = 0; i < SearchQuery.map_published_at.keySet().size(); i++) {
            String str = (String) SearchQuery.map_published_at.keySet().toArray()[i];
            publish_values.add(str);
            if(searchQuery.map_published_at.get(str) == searchQuery.getPublish_at()){
                selectedPublish_atPosition = i;
            }
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, publish_values); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_publish_at.setAdapter(spinnerArrayAdapter);
        spn_publish_at.setSelection(selectedPublish_atPosition);

        //map communities
        Spinner spn_communities = (Spinner) findViewById(R.id.spn_communities);
        ArrayList<String> communities_values = new ArrayList<String>();

        int selectedCommunityPosition = 0;
        for (int i = 0; i < api.getCommunities().keySet().size(); i++) {
            String str = (String) api.getCommunities().keySet().toArray()[i];
            communities_values.add(str);

            if (searchQuery.getCommunity().compareTo(api.getCommunities().get(str)) == 0) {
                selectedCommunityPosition = i;
            }
        }

        ArrayAdapter<String> spinnerCommunitiesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, communities_values); //selected item will look like a spinner set from XML
        spinnerCommunitiesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_communities.setAdapter(spinnerCommunitiesArrayAdapter);
        spn_communities.setSelection(selectedCommunityPosition);


        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchQuery searchQuery = SearchQuery.getInstance();

                EditText txtSearch = (EditText) findViewById(R.id.txt_string_query);
                if (!txtSearch.getText().toString().isEmpty()) {
                    searchQuery.setString_query(txtSearch.getText().toString());
                }

                Spinner spn_categories = (Spinner) findViewById(R.id.spn_categories);

                if (spn_categories.getSelectedItemPosition() > 0) {
                    Category category = categoryArrayAdapter.getItem(spn_categories.getSelectedItemPosition());
                    searchQuery.setCategory(category);
                }

                Spinner spn_order_by = (Spinner) findViewById(R.id.spn_order_by);
                searchQuery.setOrder_by(spn_order_by.getSelectedItemPosition());

                EditText txtMinPrice = (EditText) findViewById(R.id.txt_min_price);
                if (!txtMinPrice.getText().toString().isEmpty()) {
                    searchQuery.setMin_price(Integer.parseInt(txtMinPrice.getText().toString()));
                }

                EditText txtMaxPrice = (EditText) findViewById(R.id.txt_max_price);
                if (!txtMaxPrice.getText().toString().isEmpty()) {
                    searchQuery.setMax_price(Integer.parseInt(txtMaxPrice.getText().toString()));
                }

                Spinner spn_publish_at = (Spinner) findViewById(R.id.spn_publish_at);
                Integer publish_at = SearchQuery.map_published_at.get(spn_publish_at.getSelectedItem());
                searchQuery.setPublish_at(publish_at);


                Spinner spn_community = (Spinner) findViewById(R.id.spn_communities);
                ApiMilAnuncios api = ApiMilAnuncios.getInstance();
                String community = api.getCommunities().get(spn_community.getSelectedItem());
                searchQuery.setCommunity(community);


                Intent intent = new Intent(getBaseContext(), SearchOffersActivity.class);
                startActivity(intent);

                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);

                finish();
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
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
}
