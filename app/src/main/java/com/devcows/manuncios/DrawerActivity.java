package com.devcows.manuncios;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class DrawerActivity extends Activity {
    public final static int DRAWER_IMG_POSITION = 0;
    public final static int DRAWER_START_POSITION = 1;
    public final static int DRAWER_FAVOURITE_POSITION = 2;
    public final static int DRAWER_RETURN_POSITION = 3;

    public final static int DRAWER_RATE_POSITION = 0;
    public final static int DRAWER_SHARE_POSITION = 1;

    private int currentPosition;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerListAdapter mDrawerListAdapter;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private List<String> mOptionsTitlesFirst;
    private List<String> mOptionsTitlesSecond;
    private Fragment mReturnFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        mTitle = mDrawerTitle = getTitle();
        mOptionsTitlesFirst = Utils.getStringList(getResources().getStringArray(R.array.drawer_array_first));
        mOptionsTitlesSecond = Utils.getStringList(getResources().getStringArray(R.array.drawer_array_second));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        mDrawerListAdapter = new DrawerListAdapter(this, mOptionsTitlesFirst, mOptionsTitlesSecond);
        mDrawerList.setAdapter(mDrawerListAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            currentPosition = DRAWER_START_POSITION;
            Intent intent = getIntent();

            if (intent.hasExtra(Utils.DRAWER_POSITION)) {
                currentPosition = (Integer) intent.getSerializableExtra(Utils.DRAWER_POSITION);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_drawer, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view

        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame);
        if (fragment instanceof FavouriteFragment) {
            for (int i = 0; i < menu.size(); i++) {
                MenuItem item = menu.getItem(i);
                item.setVisible(false);
            }
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (currentPosition != position) {
                selectItem(position);
            }
        }
    }

    protected void showFragment(Fragment fragment, int position) {
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            if (position == DRAWER_FAVOURITE_POSITION) {
                // update selected item and title, then close the drawer

                if (position > mOptionsTitlesFirst.size() - 1) {
                    int newPosition = position - mOptionsTitlesFirst.size();
                    setTitle(mOptionsTitlesSecond.get(newPosition));
                } else {
                    setTitle(mOptionsTitlesFirst.get(position));

                    mDrawerList.clearChoices();
                    mDrawerList.setItemChecked(currentPosition, true);
                }

                mDrawerLayout.closeDrawer(mDrawerList);
            }
        }
    }

    private void setReturnFragment() {
        mReturnFragment = getFragmentManager().findFragmentById(R.id.content_frame);

        mOptionsTitlesFirst.add("Volver a");

        // fire the event
        mDrawerListAdapter.notifyDataSetChanged();
    }

    private void unsetReturnFragment() {
        if (mReturnFragment != null) {
            showFragment(mReturnFragment, -1);
        }

        mOptionsTitlesFirst.remove(mOptionsTitlesFirst.size() - 1);

        // fire the event
        mDrawerListAdapter.notifyDataSetChanged();
        mDrawerList.clearChoices();
    }

    private void selectItem(int position) {
        if (position == DRAWER_IMG_POSITION) {
            mDrawerList.setItemChecked(position, false);
            return;
        }

        mDrawerLayout.closeDrawers();

        if (position > mOptionsTitlesFirst.size() - 1) {
            int newPosition = position - mOptionsTitlesFirst.size();

            switch (newPosition) {
                case DRAWER_RATE_POSITION:
                    rateApp();
                    mDrawerList.clearChoices();
                    break;
                case DRAWER_SHARE_POSITION:
                    shareApp();
                    mDrawerList.clearChoices();
                    break;
            }
        } else {
            currentPosition = position;

            switch (position) {
                case DRAWER_START_POSITION:
                    Intent intent = new Intent(getBaseContext(), CategoriesActivity.class);
                    startActivity(intent);

                    finish();
                    break;
                case DRAWER_FAVOURITE_POSITION:
                    setReturnFragment();
                    showFragment(new FavouriteFragment(), position);
                    break;

                case DRAWER_RETURN_POSITION:
                    unsetReturnFragment();
                    break;
            }
        }

        mDrawerList.setItemChecked(currentPosition, true);
    }

    private void rateApp() {
        String sAux = "market://details?id=%s";
        sAux = String.format(sAux, this.getPackageName());

        Uri uri = Uri.parse(sAux);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            sAux = "http://play.google.com/store/apps/details?id=%s&hl=es";
            sAux = String.format(sAux, this.getPackageName());

            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sAux)));
        }
    }

    private void shareApp() {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + " aplicación android");

            String sAux = "Te recomiendo esta aplicación\nhttps://play.google.com/store/apps/details?id=%s&hl=es\n";
            sAux = String.format(sAux, this.getPackageName());
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "Elige uno"));
        } catch (Exception e) {
            //TODO what do?
        }
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(title);
    }

    public void setDrawerTitle(CharSequence title) {
        mDrawerTitle = title;
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}