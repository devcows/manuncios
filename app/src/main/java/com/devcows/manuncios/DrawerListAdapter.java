package com.devcows.manuncios;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devcows.manuncios.models.Offer;
import com.devcows.manuncios.models.OfferOtherField;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<String> objects;

    public DrawerListAdapter(Context context, String[] objects) {
        this.context = context;

        this.objects = new ArrayList<String>();
        for (int i = 0; i < objects.length; i++) {
            this.objects.add(objects[i]);
        }
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
            view = mInflater.inflate(R.layout.layout_drawer_list_item, null);
            holder = new ViewHolder();

            holder.txtTitle = (TextView) view.findViewById(R.id.title);
            holder.imageView = (ImageView) view.findViewById(R.id.icon);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        String rowItem = (String) getItem(i);

        holder.txtTitle.setText(rowItem);

        switch (i) {
            case DrawerActivity.DRAWER_FAVOURITE_POSITION:
                holder.imageView.setBackground(context.getResources().getDrawable(android.R.drawable.btn_star));
                break;

            default:
                holder.imageView.setVisibility(View.GONE);
                break;
        }

        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

}

