package com.app.first.milanuncios;

import android.app.Activity;
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

import java.util.List;


public class AdvancedSearchActivity extends Activity implements AdvancedSearchTaskListener{
    private AdvancedSearchGetTask advancedSearchGetTask;
    private ProgressBar progressBar;
    private ArrayAdapter<Category> categoryArrayAdapter;

    private SearchQuery searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        searchQuery = new SearchQuery(intent);


        Spinner spn_order_by = (Spinner) findViewById(R.id.spn_order_by);
        spn_order_by.setSelection(searchQuery.getOrder_by());


        advancedSearchGetTask = new AdvancedSearchGetTask();
        advancedSearchGetTask.execute(this);

        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SearchOffersActivity.class);

                EditText txtSearch = (EditText) findViewById(R.id.txt_string_query);
                if(!txtSearch.getText().toString().isEmpty()){
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
                if(!txtMinPrice.getText().toString().isEmpty()){
                    searchQuery.setMin_price(Integer.parseInt(txtMinPrice.getText().toString()));
                }

                EditText txtMaxPrice = (EditText) findViewById(R.id.txt_max_price);
                if(!txtMaxPrice.getText().toString().isEmpty()){
                    searchQuery.setMax_price(Integer.parseInt(txtMaxPrice.getText().toString()));
                }

                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Utils.SEARCH_QUERY, searchQuery);
                intent.putExtras(mBundle);
                startActivity(intent);

                Intent returnIntent = new Intent();
                setResult(RESULT_OK,returnIntent);

                finish();
            }
        });

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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAdvancedSearchGetResult() {
        progressBar.setVisibility(View.INVISIBLE);

        if(searchQuery.getString_query() != null && !searchQuery.getString_query().isEmpty()){
            EditText txtSearch = (EditText) findViewById(R.id.txt_string_query);
            txtSearch.setText(searchQuery.getString_query());
        }

        List<Category> categories = advancedSearchGetTask.getCategories();
        Category all = new Category();
        all.setPosition(0);
        all.setName("Todas");
        categories.add(0, all);

        categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        Spinner spn_categories = (Spinner) findViewById(R.id.spn_categories);
        spn_categories.setAdapter(categoryArrayAdapter);

        if(searchQuery.getCategory() != null){
            spn_categories.setSelection(searchQuery.getCategory().getPosition());
        }


        //TODO set items in the activity.
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent returnIntent = new Intent();
        setResult(RESULT_CANCELED,returnIntent);
    }
}
