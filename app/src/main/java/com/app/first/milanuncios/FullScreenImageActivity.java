package com.app.first.milanuncios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class FullScreenImageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_view);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        Intent intent = getIntent();
        final Offer offer = (Offer) intent.getSerializableExtra("selected_offer");


        FullScreenImageAdapter adapter = new FullScreenImageAdapter(this, offer.getSecondaryImages());

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(0);
	}
}
