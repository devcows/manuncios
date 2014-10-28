package com.devcows.manuncios;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.devcows.manuncios.models.Category;


public class CategoriesFragment extends Fragment {

    public CategoriesFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
        final Context context = rootView.getContext();

        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);


        ApiMilAnuncios api = ApiMilAnuncios.getInstance();

        CategoriesListAdapter adapter = new CategoriesListAdapter(context, api.getCategories());
        ListView listview = (ListView) rootView.findViewById(R.id.category_lst);
        listview.setAdapter(adapter);


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

        AssetManager assetManager = context.getAssets();
        //file:///android_asset/web_style.css
        Utils.loadCss(assetManager, "web_style.css");

        progressBar.setVisibility(View.INVISIBLE);

        return rootView;
    }

}




