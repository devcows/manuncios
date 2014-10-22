package com.app.first.milanuncios.other_controls;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.app.first.milanuncios.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class FullScreenImageAdapter extends PagerAdapter {

    private Context context;
    private List<String> images;

    // constructor
    public FullScreenImageAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);

        TouchImageView imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.displayImage(images.get(position), imgDisplay);

        container.addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
