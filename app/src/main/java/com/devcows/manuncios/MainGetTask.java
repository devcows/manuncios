package com.devcows.manuncios;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.devcows.manuncios.persistent.MyFavourites;
import com.devcows.manuncios.persistent.MyHistory;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class MainGetTask extends AsyncTask<MainTaskListener, Void, Void> {
    private Context context;
    private MainTaskListener[] listeners;

    public MainGetTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(MainTaskListener... listeners) {
        this.listeners = listeners;

        //initializers
        Utils.getImageLoaderInstance(context);

        ApiMilAnuncios.getInstance();
        SearchQuery.getInstance();

        AssetManager assetManager = context.getAssets();
        //file:///android_asset/web_style.css
        Utils.loadCss(assetManager, "web_style.css");

        MyFavourites.setContext(context);
        MyFavourites.getInstance();

        MyHistory.setContext(context);
        MyHistory.getInstance();
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        for (MainTaskListener listener : listeners) {
            listener.onMainGetResult();
        }
    }
}
