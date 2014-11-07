package com.devcows.manuncios;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class DrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<String> objectsFirst;
    private List<String> objectsSecond;

    public DrawerListAdapter(Context context, List<String> objectsFirst, List<String> objectsSecond) {
        this.context = context;

        this.objectsFirst = objectsFirst;
        this.objectsSecond = objectsSecond;
    }

    @Override
    public int getCount() {
        return objectsFirst.size() + objectsSecond.size();
    }

    @Override
    public Object getItem(int i) {
        if (i > objectsFirst.size() - 1) {
            int newI = i - objectsFirst.size();
            return objectsSecond.get(newI);
        } else {
            return objectsFirst.get(i);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position > objectsFirst.size() - 1) {
            long j = objectsFirst.indexOf(getItem(objectsFirst.size() - 1));
            j += objectsSecond.indexOf(getItem(position));

            return j;
        } else {
            return objectsFirst.indexOf(getItem(position));
        }
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

        if (i == DrawerActivity.DRAWER_IMG_POSITION) {
            if (view == null) {
                view = mInflater.inflate(R.layout.layout_drawer_list_item_img, null);
            }
        } else {

            if (view == null) {
                view = mInflater.inflate(R.layout.layout_drawer_list_item, null);
            }

            holder = new ViewHolder(view);

            String rowItem = (String) getItem(i);
            holder.txtTitle.setText(rowItem);

            //set icon.
            if (i > objectsFirst.size() - 1) {
                int newI = i - objectsFirst.size();

                switch (newI) {
                    case DrawerActivity.DRAWER_RATE_POSITION:
                        holder.imageView.setBackgroundResource(R.drawable.ic_action_edit);
                        view.setBackgroundResource(R.drawable.drawer_style_first_bottom);
                        break;

                    case DrawerActivity.DRAWER_SHARE_POSITION:
                        holder.imageView.setBackgroundResource(R.drawable.ic_action_share);
                        view.setBackgroundResource(R.drawable.drawer_style_other_bottom);
                        break;

                    default:
                        holder.imageView.setVisibility(View.GONE);
                        break;
                }
            } else {
                switch (i) {
                    case DrawerActivity.DRAWER_START_POSITION:
                        holder.imageView.setBackgroundResource(android.R.drawable.ic_input_get);
                        break;

                    case DrawerActivity.DRAWER_FAVOURITE_POSITION:
                        holder.imageView.setBackgroundResource(R.drawable.ic_action_important);
                        break;

                    case DrawerActivity.DRAWER_HISTORY_POSITION:
                        holder.imageView.setBackgroundResource(R.drawable.ic_action_time);
                        break;

                    case DrawerActivity.DRAWER_RETURN_POSITION:
                        holder.imageView.setBackgroundResource(R.drawable.ic_action_undo);
                        break;
                    default:
                        holder.imageView.setVisibility(View.GONE);
                        break;
                }
            }
        }

        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;

        public ViewHolder(View view) {
            this.txtTitle = (TextView) view.findViewById(R.id.title);
            this.imageView = (ImageView) view.findViewById(R.id.icon);
        }
    }

}

