package com.devcows.manuncios;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class SearchOffersListAdapter extends BaseAdapter {

    private Context context;
    private List<Offer> objects;

    public SearchOffersListAdapter(Context context, List<Offer> objects) {
        this.context = context;
        this.objects = objects;
    }

    public void setObjects(List<Offer> objects) {
        this.objects = objects;
    }

    public void addAllObjects(List<Offer> objects) {
        this.objects.addAll(objects);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int position) {
        return objects.indexOf(getItem(position));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_offer_list, null);
            holder = new ViewHolder();

            holder.txtFirstTitle = (TextView) view.findViewById(R.id.firstTitle);
            holder.txtSecondTitle = (TextView) view.findViewById(R.id.secondTitle);
            holder.txtDescription = (TextView) view.findViewById(R.id.description);
            holder.imageView = (ImageView) view.findViewById(R.id.icon);
            holder.lLayout = (LinearLayout) view.findViewById(R.id.othersList);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Offer rowItem = (Offer) getItem(i);

        holder.txtFirstTitle.setText(rowItem.getFirstTitle());
        holder.txtSecondTitle.setText(rowItem.getSecondTitle());
        holder.txtDescription.setText(rowItem.getDescription());

        holder.lLayout.removeAllViews();
        for (int j = 0; j < rowItem.getOther().size() && j < 4; j++) {
            OfferOtherField other = rowItem.getOther().get(j);

            View viewAux = mInflater.inflate(R.layout.layout_other_list, null);
            TextView txtView = (TextView) viewAux.findViewById(R.id.other_field);
            txtView.setText(other.getText());

            if (other.getBoxColor() != null && other.getBoxColor().length() > 0) {
                GradientDrawable bgShape = (GradientDrawable)txtView.getBackground();
                bgShape.setColor(Color.parseColor(other.getBoxColor()));
            }

            holder.lLayout.addView(txtView);
        }

        if (rowItem.getOther().size() >= 4) {
            TextView txtView = new TextView(context);
            txtView.setPadding(3, 0, 3, 0);

            txtView.setText("...");

            holder.lLayout.addView(txtView);
        }

        if (rowItem.getImageUri() != null && !rowItem.getImageUri().isEmpty()) {
            ImageLoader imgLoader = ImageLoader.getInstance();
            imgLoader.displayImage(rowItem.getImageUri(), holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }

        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtFirstTitle;
        TextView txtSecondTitle;
        TextView txtDescription;
        LinearLayout lLayout;
    }

}
