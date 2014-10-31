package com.devcows.manuncios;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.devcows.manuncios.models.Offer;
import com.devcows.manuncios.models.OfferOtherField;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OfferAdapter {

    private OfferHolder holderOffer;
    private Offer offer;
    private Context context;

    public OfferAdapter(OfferHolder holderOffer, Offer offer, Context context) {
        this.holderOffer = holderOffer;
        this.offer = offer;
        this.context = context;
    }

    public void fillOffer() {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (holderOffer != null && offer != null) {

            if (holderOffer.txtFirstTitle != null) {
                holderOffer.txtFirstTitle.setText(offer.getFirstTitle());
            }

            if (holderOffer.txtSecondTitle != null) {
                holderOffer.txtSecondTitle.setText(offer.getSecondTitle());
            }

            if (holderOffer.txtDescription != null) {
                holderOffer.txtDescription.setText(offer.getDescription());
            }

            if (holderOffer.lLayoutRow1 != null) {
                holderOffer.lLayoutRow1.removeAllViews();

                for (int j = 0; j < offer.getOther().size() && j < 4; j++) {
                    OfferOtherField other = offer.getOther().get(j);

                    View viewAux = mInflater.inflate(R.layout.layout_other_list_item, null);
                    TextView txtView = (TextView) viewAux.findViewById(R.id.other_field);
                    txtView.setText(other.getText());

                    if (other.getBoxColor() != null && other.getBoxColor().length() > 0) {
                        GradientDrawable bgShape = (GradientDrawable) txtView.getBackground();
                        bgShape.setColor(Color.parseColor(other.getBoxColor()));
                    }

                    holderOffer.lLayoutRow1.addView(txtView);
                }

                if (offer.getOther().size() >= 4) {
                    TextView txtView = new TextView(context);
                    txtView.setPadding(3, 0, 3, 0);

                    txtView.setText("...");

                    holderOffer.lLayoutRow1.addView(txtView);
                }

            }

            //TODO lLayoutRow2


            if (holderOffer.imageView != null) {
                if (offer.getImageUri() != null && !offer.getImageUri().isEmpty()) {
                    ImageLoader imgLoader = ImageLoader.getInstance();
                    imgLoader.displayImage(offer.getImageUri(), holderOffer.imageView);
                } else {
                    holderOffer.imageView.setVisibility(View.GONE);
                }
            }

        }
    }
}
