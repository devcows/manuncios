package com.devcows.manuncios;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.devcows.manuncios.models.Offer;

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
        OfferAdapter adapter = null;
        Offer rowItem = (Offer) getItem(i);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_offer_list_item, null);

            OfferHolder holder = new OfferHolder(view);
            adapter = new OfferAdapter(holder, rowItem, context);

            view.setTag(holder);
        } else {
            adapter = (OfferAdapter) view.getTag();
        }

        adapter.fillOffer();

//
//        holder.txtFirstTitle.setText(rowItem.getFirstTitle());
//        holder.txtSecondTitle.setText(rowItem.getSecondTitle());
//        holder.txtDescription.setText(rowItem.getDescription());
//
//        holder.lLayout.removeAllViews();
//        for (int j = 0; j < rowItem.getOther().size() && j < 4; j++) {
//            OfferOtherField other = rowItem.getOther().get(j);
//
//            View viewAux = mInflater.inflate(R.layout.layout_other_list_item, null);
//            TextView txtView = (TextView) viewAux.findViewById(R.id.other_field);
//            txtView.setText(other.getText());
//
//            if (other.getBoxColor() != null && other.getBoxColor().length() > 0) {
//                GradientDrawable bgShape = (GradientDrawable) txtView.getBackground();
//                bgShape.setColor(Color.parseColor(other.getBoxColor()));
//            }
//
//            holder.lLayout.addView(txtView);
//        }
//
//        if (rowItem.getOther().size() >= 4) {
//            TextView txtView = new TextView(context);
//            txtView.setPadding(3, 0, 3, 0);
//
//            txtView.setText("...");
//
//            holder.lLayout.addView(txtView);
//        }
//
//        if (rowItem.getImageUri() != null && !rowItem.getImageUri().isEmpty()) {
//            ImageLoader imgLoader = ImageLoader.getInstance();
//            imgLoader.displayImage(rowItem.getImageUri(), holder.imageView);
//        } else {
//            holder.imageView.setVisibility(View.GONE);
//        }

        return view;
    }

}

