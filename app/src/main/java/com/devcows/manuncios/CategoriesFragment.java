package com.devcows.manuncios;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.devcows.manuncios.models.Category;


public class CategoriesFragment extends Fragment {
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
        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);
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




