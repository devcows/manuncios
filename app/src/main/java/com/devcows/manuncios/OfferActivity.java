package com.devcows.manuncios;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.devcows.manuncios.other_controls.FullScreenImageActivity;
import com.nostra13.universalimageloader.core.ImageLoader;


public class OfferActivity extends Activity implements OfferTaskListener {
    private ProgressBar progressBar;
    private Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        progressBar = (ProgressBar) findViewById(R.id.pbHeaderProgress);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        this.offer = (Offer) intent.getSerializableExtra(Utils.SELECTED_OFFER);

        OfferGetTask offerGetTask = new OfferGetTask();
        offerGetTask.setOffer(offer);
        offerGetTask.execute(this);

        TextView txtFirstTitle = (TextView) findViewById(R.id.firstTitle);
        txtFirstTitle.setText(offer.getFirstTitle());
        TextView txtSecondTitle = (TextView) findViewById(R.id.secondTitle);
        txtSecondTitle.setText(offer.getSecondTitle());
        TextView txtDescription = (TextView) findViewById(R.id.description);
        txtDescription.setText(offer.getDescription());

        TableLayout tl = (TableLayout) findViewById(R.id.othersList);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(layoutParams);

        TableRow tr2 = new TableRow(this);
        tr2.setLayoutParams(layoutParams);

        for (int i = 0; i < offer.getOther().size(); i++) {
            OfferOtherField other = offer.getOther().get(i);
            TextView txtView = new TextView(this);

            txtView.setText(other.getText());
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            params.setMargins(Utils.getPixels(getResources(), 10), 5, 5, 5); // setMargins(left, top, right, bottom);
//            txtView.setLayoutParams(params);

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

        btnLink = (Button) findViewById(R.id.contact);
        btnLink.setText("Contactar");
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ContactActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable("contact_url", "http://www.milanuncios.com/datos-contacto/?id=" + offer.getId());
                intent.putExtras(mBundle);

                startActivity(intent);

            }
        });

        if (offer.getSecondaryImages().isEmpty()) {
            HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv1);
            hsv.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_offer, menu);
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
    public void onOfferGetResult(Offer offer) {
        progressBar.setVisibility(View.INVISIBLE);

        if (!offer.getSecondaryImages().isEmpty()) {
            HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.hsv1);
            hsv.setVisibility(View.VISIBLE);
        }

        for (String imageUri : offer.getSecondaryImages()) {

            LinearLayout lLayout = (LinearLayout) findViewById(R.id.images_layout);
            ImageView imgView = new ImageView(this);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(Utils.getPixels(getResources(), 300), ViewGroup.LayoutParams.FILL_PARENT);
            imgView.setLayoutParams(layoutParams);
            imgView.setScaleType(ImageView.ScaleType.FIT_XY);

            final Offer selectedOffer = offer;
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), FullScreenImageActivity.class);

                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("selected_offer", selectedOffer);
                    intent.putExtras(mBundle);

                    startActivity(intent);
                }
            });

            ImageLoader imgLoader = ImageLoader.getInstance();
            imgLoader.displayImage(imageUri, imgView);

            lLayout.addView(imgView);
        }
    }
}
