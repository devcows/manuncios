package com.devcows.manuncios;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OfferHolder {
    public ImageView imageView;
    public TextView txtFirstTitle;
    public TextView txtSecondTitle;
    public TextView txtDescription;
    public LinearLayout lLayoutRow1;
    public LinearLayout lLayoutRow2;

    public OfferHolder(View view) {
        this.imageView = (ImageView) view.findViewById(R.id.icon);
        this.txtFirstTitle = (TextView) view.findViewById(R.id.firstTitle);
        this.txtSecondTitle = (TextView) view.findViewById(R.id.secondTitle);
        this.txtDescription = (TextView) view.findViewById(R.id.description);
        this.lLayoutRow1 = (LinearLayout) view.findViewById(R.id.othersListRow1);
        this.lLayoutRow2 = (LinearLayout) view.findViewById(R.id.othersListRow2);
        ;
    }
}
