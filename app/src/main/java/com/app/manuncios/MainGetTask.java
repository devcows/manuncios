package com.app.manuncios;

import android.content.Context;
import android.os.AsyncTask;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
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
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();


        ImageLoader imgLoader = ImageLoader.getInstance();
        imgLoader.init(config);

        ApiMilAnuncios.getInstance();
        SearchQuery.getInstance();

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        for (MainTaskListener listener : listeners) {
            listener.onMainGetResult();
        }
    }
}
