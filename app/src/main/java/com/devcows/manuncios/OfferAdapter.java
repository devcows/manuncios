//Copyright (C) 2014  Guillermo G. (info@devcows.com)
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

package com.devcows.manuncios;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    private TextView setOtherField(LayoutInflater mInflater, OfferOtherField other) {
        View viewAux = mInflater.inflate(R.layout.layout_other_list_item, null);
        TextView txtView = (TextView) viewAux.findViewById(R.id.other_field);
        txtView.setText(other.getText());

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(1, 0, 1, 0); // llp.setMargins(left, top, right, bottom);
        txtView.setLayoutParams(llp);

        if (other.getBoxColor() != null && other.getBoxColor().length() > 0) {
            GradientDrawable bgShape = (GradientDrawable) txtView.getBackground();
            bgShape.setColor(Color.parseColor(other.getBoxColor()));
        }

        return txtView;
    }

    public void fillOffer() {
        fillOffer(false);
    }

    public void fillOffer(boolean setOtherFieldsOneRow) {

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

            int totalElements = 4;

            if (setOtherFieldsOneRow) {
                totalElements = 3;
            }

            if (holderOffer.lLayoutRow1 != null) {
                holderOffer.lLayoutRow1.removeAllViews();

                for (int j = 0; j < offer.getOther().size() && j < totalElements; j++) {
                    OfferOtherField other = offer.getOther().get(j);

                    TextView txtView = setOtherField(mInflater, other);
                    holderOffer.lLayoutRow1.addView(txtView);
                }

                if (setOtherFieldsOneRow) {
                    if (offer.getOther().size() >= totalElements) {
                        TextView txtView = new TextView(context);
                        txtView.setPadding(3, 0, 3, 0);

                        txtView.setText("...");

                        holderOffer.lLayoutRow1.addView(txtView);
                    }

                    if (holderOffer.lLayoutRow2 != null) {
                        holderOffer.lLayoutRow2.setVisibility(View.GONE);
                    }

                } else {

                    if (holderOffer.lLayoutRow2 != null) {
                        if (offer.getOther().size() < totalElements) {
                            holderOffer.lLayoutRow2.setVisibility(View.GONE);
                        } else {

                            for (int j = totalElements; j < offer.getOther().size(); j++) {
                                OfferOtherField other = offer.getOther().get(j);

                                TextView txtView = setOtherField(mInflater, other);
                                holderOffer.lLayoutRow2.addView(txtView);
                            }
                        }
                    }
                }
            }

            if (holderOffer.imageView != null) {
                if (offer.getImageUri() != null && !offer.getImageUri().isEmpty()) {
                    ImageLoader imgLoader = Utils.getImageLoaderInstance(context);
                    imgLoader.displayImage(offer.getImageUri(), holderOffer.imageView);
                } else {
                    holderOffer.imageView.setVisibility(View.GONE);
                }
            }

        }
    }
}
