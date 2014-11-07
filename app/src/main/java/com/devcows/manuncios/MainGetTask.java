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

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;

import com.devcows.manuncios.persistent.MyFavourites;
import com.devcows.manuncios.persistent.MyHistory;

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
