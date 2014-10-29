package com.devcows.manuncios;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity implements MainTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String version = "";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (pInfo.versionName != null && !pInfo.versionName.isEmpty()) {
                version = "Version: " + pInfo.versionName;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        TextView txtVersion = (TextView) findViewById(R.id.txtVersion);
        txtVersion.setText(version);

        MainGetTask mainGetTask = new MainGetTask(this);
        mainGetTask.execute(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
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
    public void onMainGetResult() {
        Intent intent = new Intent(getBaseContext(), CategoriesActivity.class);


//        Bundle mBundle = new Bundle();
//        mBundle.putSerializable(Utils.DRAWER_POSITION, DrawerActivity.DRAWER_START_POSITION);
//        intent.putExtras(mBundle);

        startActivity(intent);

        finish();
    }
}




