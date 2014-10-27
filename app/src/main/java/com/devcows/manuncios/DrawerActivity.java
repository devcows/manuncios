package com.devcows.manuncios;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public abstract class DrawerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        ListView list = (ListView) findViewById(R.id.left_drawer);


    }


    public abstract int getLayoutResourceId();
}
