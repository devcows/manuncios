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
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = mInflater.inflate(R.layout.layout_drawer_list_item, null);
        }

        ViewHolder holder = new ViewHolder(view);

        String rowItem = (String) getItem(i);
        holder.txtTitle.setText(rowItem);

        //set icon.
        if (i > objectsFirst.size() - 1) {
            int newI = i - objectsFirst.size();

            switch (newI) {
                case DrawerActivity.DRAWER_RATE_POSITION:
                    holder.imageView.setBackgroundResource(R.drawable.ic_action_edit);

                    if (android.os.Build.VERSION.SDK_INT > 15) {
                        view.setBackgroundResource(R.drawable.drawer_style_first_bottom);
                    }
                    break;

                case DrawerActivity.DRAWER_SHARE_POSITION:
                    holder.imageView.setBackgroundResource(R.drawable.ic_action_share);

                    if (android.os.Build.VERSION.SDK_INT > 15) {
                        view.setBackgroundResource(R.drawable.drawer_style_other_bottom);
                    }
                    break;

                default:
                    holder.imageView.setVisibility(View.GONE);
                    break;
            }
        } else {
            switch (i) {
                case DrawerActivity.DRAWER_IMG_POSITION:

                    holder.txtTitle.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.GONE);
                    holder.appIcon.setVisibility(View.VISIBLE);
                    break;

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

        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        ImageView appIcon;
        TextView txtTitle;

        public ViewHolder(View view) {
            this.txtTitle = (TextView) view.findViewById(R.id.title);
            this.imageView = (ImageView) view.findViewById(R.id.icon);
            this.appIcon = (ImageView) view.findViewById(R.id.app_icon);

            this.txtTitle.setVisibility(View.VISIBLE);
            this.imageView.setVisibility(View.VISIBLE);
            this.appIcon.setVisibility(View.GONE);
        }
    }

}

