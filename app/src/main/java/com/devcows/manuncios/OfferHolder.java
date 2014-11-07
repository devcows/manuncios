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
