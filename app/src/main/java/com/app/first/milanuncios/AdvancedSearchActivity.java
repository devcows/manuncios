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

    //search parameters
    private Category category = null;
    private String string_query = null;

    private boolean most_recent = false;
    private boolean most_old = false;
    private boolean most_expensive = false;
    private boolean most_cheap = false;

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

        if (intent.hasExtra("string_query")) {
            string_query = (String) intent.getSerializableExtra("string_query");
        }

        if (intent.hasExtra("selected_category")) {
            category = (Category) intent.getSerializableExtra("selected_category");
        }

        if (intent.hasExtra("most_recent")) {
            most_recent = (Boolean) intent.getSerializableExtra("most_recent");
        }

        if (intent.hasExtra("most_old")) {
            most_old = (Boolean) intent.getSerializableExtra("most_old");
        }

        if (intent.hasExtra("most_expensive")) {
            most_expensive = (Boolean) intent.getSerializableExtra("most_expensive");
        }

        if (intent.hasExtra("most_cheap")) {
            most_cheap = (Boolean) intent.getSerializableExtra("most_cheap");
        }


        advancedSearchGetTask = new AdvancedSearchGetTask();
        advancedSearchGetTask.execute(this);

        Button btnSearch = (Button) findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SearchOffersActivity.class);
                Bundle mBundle = new Bundle();

                //TODO pass parameters.


                intent.putExtras(mBundle);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.advanced_search, menu);
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
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        Spinner spn_categories = (Spinner) findViewById(R.id.spn_categories);
        spn_categories.setAdapter(categoryArrayAdapter);

        if(category != null){
            spn_categories.setSelection(category.getPosition());
        }


        //TODO set items in the activity.
    }
}
