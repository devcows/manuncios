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

import java.util.List;


public class AdvancedSearchActivity extends Activity implements AdvancedSearchTaskListener{
    private AdvancedSearchGetTask advancedSearchGetTask;
    private ProgressBar progressBar;
    private ArrayAdapter<Category> categoryArrayAdapter;

    //search parameters
    private Category category = null;
    private String string_query = null;

    private Integer order_by, min_price, max_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);


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
        } else {
            order_by = Utils.ORDER_BY_RECENT;
        }

        if(intent.hasExtra(Utils.MIN_PRICE)){
            min_price = (Integer) intent.getSerializableExtra(Utils.MIN_PRICE);
        }

        if(intent.hasExtra(Utils.MAX_PRICE)){
            max_price = (Integer) intent.getSerializableExtra(Utils.MAX_PRICE);
        }

        Spinner spn_order_by = (Spinner) findViewById(R.id.spn_order_by);
        spn_order_by.setSelection(order_by);


        advancedSearchGetTask = new AdvancedSearchGetTask();
        advancedSearchGetTask.execute(this);

        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SearchOffersActivity.class);
                Bundle mBundle = new Bundle();

                EditText txtSearch = (EditText) findViewById(R.id.txt_string_query);
                if(!txtSearch.getText().toString().isEmpty()){
                    mBundle.putSerializable(Utils.STRING_QUERY, txtSearch.getText().toString());
                }

                Spinner spn_categories = (Spinner) findViewById(R.id.spn_categories);

                if (spn_categories.getSelectedItemPosition() > 0) {
                    Category category = categoryArrayAdapter.getItem(spn_categories.getSelectedItemPosition());
                    mBundle.putSerializable(Utils.SELECTED_CATEGORY, category);
                }

                Spinner spn_order_by = (Spinner) findViewById(R.id.spn_order_by);
                mBundle.putSerializable(Utils.ORDER_BY, spn_order_by.getSelectedItemPosition());

                if (min_price != null){
                    mBundle.putSerializable(Utils.MIN_PRICE, min_price);
                }

                if (max_price != null){
                    mBundle.putSerializable(Utils.MIN_PRICE, max_price);
                }

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

        if(string_query != null){
            EditText txtSearch = (EditText) findViewById(R.id.txt_string_query);
            txtSearch.setText(string_query);
        }

        List<Category> categories = advancedSearchGetTask.getCategories();
        Category all = new Category();
        all.setPosition(0);
        all.setName("Todas");
        categories.add(0, all);

        categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        Spinner spn_categories = (Spinner) findViewById(R.id.spn_categories);
        spn_categories.setAdapter(categoryArrayAdapter);

        if(category != null){
            spn_categories.setSelection(category.getPosition());
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
