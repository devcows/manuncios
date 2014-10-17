package com.app.first.milanuncios;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;


public class OfferActivity extends Activity implements OfferTaskListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        final Offer offer = (Offer) intent.getSerializableExtra("selected_offer");

        OfferGetTask offerGetTask = new OfferGetTask();
        offerGetTask.setOffer(offer);
        offerGetTask.execute(this);

        TextView txtFirstTitle = (TextView) findViewById(R.id.firstTitle);
        txtFirstTitle.setText(offer.getFirstTitle());
        TextView txtSecondTitle = (TextView) findViewById(R.id.secondTitle);
        txtSecondTitle.setText(offer.getSecondTitle());
        TextView txtDescription = (TextView) findViewById(R.id.description);
        txtDescription.setText(offer.getDescription());
        ImageView imageView = (ImageView) findViewById(R.id.icon);

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(offer.getImageUri(), imageView);

        TableLayout tl = (TableLayout) findViewById(R.id.othersList);


        android.view.ViewGroup.LayoutParams layoutParams = new android.view.ViewGroup.LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(layoutParams);

        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(layoutParams);

        for (int i = 0; i < offer.getOther().size(); i++) {
            OfferOtherField other = offer.getOther().get(i);
            TextView txtView = new TextView(this);

            txtView.setText(other.getText());
            if (other.getBoxColor() != null && other.getBoxColor().length() > 0) {
                txtView.setBackgroundColor(Color.parseColor(other.getBoxColor()));
            }

            if (i <= 3) {
                tr1.addView(txtView);
            } else {
                tr2.addView(txtView);
            }
        }

        tl.addView(tr1, layoutParams);
        if (offer.getOther().size() > 3) {
            tl.addView(tr2, layoutParams);
        }

        Button btnLink = (Button) findViewById(R.id.goto_url);
        btnLink.setText("Ir a la web");
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uriUrl = Uri.parse(offer.getUrl());
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
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

    @Override
    public void onOfferGetResult(Offer offer) {
        //TODO Update pictures.
    }
}
