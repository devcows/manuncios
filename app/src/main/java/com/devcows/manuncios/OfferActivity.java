package com.devcows.manuncios;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devcows.manuncios.models.Favourite;
import com.devcows.manuncios.models.Offer;
import com.devcows.manuncios.other_controls.FullScreenImageActivity;
import com.devcows.manuncios.persistent.MyFavourites;
import com.nostra13.universalimageloader.core.ImageLoader;


public class OfferActivity extends DrawerActivity {
    private Offer offer;

    private Drawable iconStarOn, iconStarOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iconStarOn = getResources().getDrawable(android.R.drawable.btn_star_big_on);
        iconStarOff = getResources().getDrawable(android.R.drawable.btn_star_big_off);

        Intent intent = getIntent();
        this.offer = (Offer) intent.getSerializableExtra(Utils.SELECTED_OFFER);

        Bundle bundle = new Bundle();
        bundle.putSerializable(Utils.SELECTED_OFFER, offer);

        Fragment fragment = new OfferFragment();
        fragment.setArguments(bundle);

        showFragment(fragment, -1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_offer, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MyFavourites myFavourites = MyFavourites.getInstance();

        if (myFavourites.containsFavourite(offer)) {
            menu.findItem(R.id.favourite_offer).setIcon(iconStarOn);
        } else {
            menu.findItem(R.id.favourite_offer).setIcon(iconStarOff);
        }

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.favourite_offer:
                Favourite fa = new Favourite(offer);
                MyFavourites myFavourites = MyFavourites.getInstance();

                Toast toast;
                if (myFavourites.containsFavourite(offer)) {
                    myFavourites.delFavourite(fa);
                    item.setIcon(iconStarOff);
                    toast = Toast.makeText(this, "Anuncio borrado de la lista de favoritos.", Toast.LENGTH_SHORT);
                } else {
                    myFavourites.addFavourite(fa);
                    item.setIcon(iconStarOn);
                    toast = Toast.makeText(this, "Anuncio añadido a la lista de favoritos.", Toast.LENGTH_SHORT);
                }

                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class OfferFragment extends FragmentReturn implements OfferTaskListener {
        private ProgressBar progressBar;
        private Offer offer;
        private View rootView;

        public OfferFragment() {
        }

        @Override
        public String getReturnName() {
            return "al anuncio";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            this.rootView = inflater.inflate(R.layout.activity_offer, container, false);
            this.context = getActivity();
            this.offer = (Offer) getArguments().getSerializable(Utils.SELECTED_OFFER);

            progressBar = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            OfferGetTask offerGetTask = new OfferGetTask();
            offerGetTask.setOffer(offer);
            offerGetTask.execute(this);

            OfferHolder holder = new OfferHolder(rootView);
            OfferAdapter adapter = new OfferAdapter(holder, offer, context);
            adapter.fillOffer();

            Button btnLink = (Button) rootView.findViewById(R.id.goto_url);
            btnLink.setText("Ir a la web");
            btnLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uriUrl = Uri.parse(offer.getUrl());
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                }
            });

            btnLink = (Button) rootView.findViewById(R.id.contact);
            btnLink.setText("Contactar");
            btnLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ContactActivity.class);

                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("contact_url", "http://www.milanuncios.com/datos-contacto/?id=" + offer.getId());
                    intent.putExtras(mBundle);

                    startActivity(intent);

                }
            });

            if (offer.getSecondaryImages().isEmpty()) {
                HorizontalScrollView hsv = (HorizontalScrollView) rootView.findViewById(R.id.hsv1);
                hsv.setVisibility(View.GONE);
            }

            return rootView;
        }

        //web view client implementation
        private class CustomWebViewClient extends WebViewClient {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //do whatever you want with the url that is clicked inside the webview.
                //for example tell the webview to load that url.
                view.loadUrl(url);
                //return true if this method handled the link event
                //or false otherwise
                return true;
            }
        }


        @Override
        public void onOfferGetResult(Offer offer) {
            progressBar.setVisibility(View.INVISIBLE);

            if (!offer.getSecondaryImages().isEmpty()) {
                HorizontalScrollView hsv = (HorizontalScrollView) rootView.findViewById(R.id.hsv1);
                hsv.setVisibility(View.VISIBLE);
            }

            for (String imageUri : offer.getSecondaryImages()) {

                LinearLayout lLayout = (LinearLayout) rootView.findViewById(R.id.images_layout);
                ImageView imgView = new ImageView(context);

                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(Utils.getPixels(context.getResources(), 300), ViewGroup.LayoutParams.FILL_PARENT);
                imgView.setLayoutParams(layoutParams);
                imgView.setScaleType(ImageView.ScaleType.FIT_XY);

                final Offer selectedOffer = offer;
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, FullScreenImageActivity.class);

                        Bundle mBundle = new Bundle();
                        mBundle.putSerializable("selected_offer", selectedOffer);
                        intent.putExtras(mBundle);

                        startActivity(intent);
                    }
                });

                ImageLoader imgLoader = Utils.getImageLoaderInstance(context);
                imgLoader.displayImage(imageUri, imgView);

                lLayout.addView(imgView);
            }
        }
    }
}
