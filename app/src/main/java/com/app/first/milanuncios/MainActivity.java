package com.app.first.milanuncios;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements CategoriesTaskListener { //, SearchView.OnQueryTextListener {
    private CategoriesListAdapter adapter;

    private ProgressBar pb;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.category_lst);

        adapter = new CategoriesListAdapter(this, new ArrayList<Category>());
        listview.setAdapter(adapter);

        pb = (ProgressBar) findViewById(R.id.pbHeaderProgress);

        pb.setIndeterminate(true);
        pb.setVisibility(View.VISIBLE);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Category item = (Category) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), SearchOfferActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("selected_category", item);
                intent.putExtras(mBundle);

                startActivity(intent);
            }
        });


        Context context = getBaseContext();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();


        ImageLoader.getInstance().init(config);

        new CategoriesGetTask().execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        //setupSearchView();

        return true;
    }

//    private void setupSearchView() {
//
//        mSearchView.setIconifiedByDefault(true);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if (searchManager != null) {
//            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
//
//            // Try to use the "applications" global search provider
//            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
//            for (SearchableInfo inf : searchables) {
//                if (inf.getSuggestAuthority() != null
//                        && inf.getSuggestAuthority().startsWith("applications")) {
//                    info = inf;
//                }
//            }
//            mSearchView.setSearchableInfo(info);
//        }
//
//       mSearchView.setOnQueryTextListener(this);
//    }

//    @Override
//    public boolean onQueryTextSubmit(String query_string){
//        Intent intent = new Intent(getBaseContext(), SearchOfferActivity.class);
//
//        Bundle mBundle = new Bundle();
//        mBundle.putString("query_string", query_string);
//        intent.putExtras(mBundle);
//
//        startActivity(intent);
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s){
//        //TODO need do something
//        return true;
//    }

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
    public void onCategoriesGetResult(List<Category> categories) {
        pb.setVisibility(View.INVISIBLE);
        adapter.setObjects(categories);

        // fire the event
        adapter.notifyDataSetChanged();
    }
}




