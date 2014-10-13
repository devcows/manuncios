package com.app.first.milanuncios;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.category_list, null);
            holder = new ViewHolder();

            holder.txtTitle = (TextView) view.findViewById(R.id.title);
            holder.imageView = (ImageView) view.findViewById(R.id.icon);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Category rowItem = (Category) getItem(i);

        holder.txtTitle.setText(rowItem.getName());
        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(rowItem.getImageUri(), holder.imageView);

        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

}

