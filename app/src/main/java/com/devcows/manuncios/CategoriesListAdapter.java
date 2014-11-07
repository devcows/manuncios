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

import com.devcows.manuncios.models.Category;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class CategoriesListAdapter extends BaseAdapter {

    private Context context;
    private List<Category> objects;

    public void setObjects(List<Category> objects) {
        this.objects = objects;
    }

    public CategoriesListAdapter(Context context, List<Category> objects) {
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
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_category_item, null);
        }

        ViewHolder holder = new ViewHolder(view);

        Category rowItem = (Category) getItem(i);
        holder.txtTitle.setText(rowItem.getName());

        ImageLoader imgLoader = Utils.getImageLoaderInstance(context);
        imgLoader.displayImage(rowItem.getImageUri(), holder.imageView);

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

