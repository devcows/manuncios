package com.app.first.milanuncios;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;


public class OfferActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Intent intent = getIntent();
        Offer offer = (Offer) intent.getSerializableExtra("selected_offer");

        TextView txtFirstTitle = (TextView) findViewById(R.id.firstTitle);
        txtFirstTitle.setText(offer.getFirstTitle());
        TextView txtSecondTitle = (TextView) findViewById(R.id.secondTitle);
        txtSecondTitle.setText(offer.getSecondTitle());
        TextView txtDescription = (TextView) findViewById(R.id.description);
        txtDescription.setText(offer.getDescription());
        ImageView imageView = (ImageView) findViewById(R.id.icon);

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(offer.getImageUri(), imageView);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.othersList);
        for(OfferOtherField other: offer.getOther()){
            TextView txtView = new TextView(this);

            txtView.setText(other.getText());
            if(other.getBoxColor() != null && other.getBoxColor().length() > 0) {
                txtView.setBackgroundColor(Color.parseColor(other.getBoxColor()));
            }

            linearLayout.addView(txtView);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.offer, menu);
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
}
