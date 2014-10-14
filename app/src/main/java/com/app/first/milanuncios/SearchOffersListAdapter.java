package com.app.first.milanuncios;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
            view = mInflater.inflate(R.layout.offer_list, null);
            holder = new ViewHolder();

            holder.txtFirstTitle = (TextView) view.findViewById(R.id.firstTitle);
            holder.txtSecondTitle = (TextView) view.findViewById(R.id.secondTitle);
            holder.txtDescription = (TextView) view.findViewById(R.id.description);
            holder.imageView = (ImageView) view.findViewById(R.id.icon);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Offer rowItem = (Offer) getItem(i);

        holder.txtFirstTitle.setText(rowItem.getFirstTitle());
        holder.txtSecondTitle.setText(rowItem.getSecondTitle());
        holder.txtDescription.setText(rowItem.getDescription());

        for(OfferOtherField other: rowItem.getOther()){
            TextView txtView = new TextView(context);

            txtView.setText(other.getText());
            if(other.getBoxColor() != null && other.getBoxColor().length() > 0) {
                txtView.setBackgroundColor(Color.parseColor(other.getBoxColor()));
            }

            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.othersList);
            linearLayout.addView(txtView);
        }

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(rowItem.getImageUri(), holder.imageView);

        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtFirstTitle;
        TextView txtSecondTitle;
        TextView txtDescription;
    }

}

