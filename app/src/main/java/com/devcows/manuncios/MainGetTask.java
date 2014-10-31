package com.devcows.manuncios;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

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
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .defaultDisplayImageOptions(defaultOptions)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCacheSize(5 * 1024 * 1024) // 1 Mb
                .diskCacheSize(20 * 1024 * 1024) // 20 Mb
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .build();

        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.init(config);

        ApiMilAnuncios.getInstance();
        SearchQuery.getInstance();

        AssetManager assetManager = context.getAssets();
        //file:///android_asset/web_style.css
        Utils.loadCss(assetManager, "web_style.css");

        MyFavourites.setContext(context);
        MyFavourites.getInstance();
        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        for (MainTaskListener listener : listeners) {
            listener.onMainGetResult();
        }
    }
}
