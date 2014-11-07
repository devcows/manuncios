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

import android.os.AsyncTask;

import com.devcows.manuncios.models.Category;

import java.util.List;

public class CategoriesGetTask extends AsyncTask<CategoriesTaskListener, Void, List<Category>> {
    private final ApiMilAnuncios mApi = ApiMilAnuncios.getInstance();
    private CategoriesTaskListener[] listeners;

    @Override
    protected List<Category> doInBackground(CategoriesTaskListener... listeners) {
        this.listeners = listeners;

        if (mApi.getCategories().isEmpty()) {
            mApi.loadCategories();
        }

        return mApi.getCategories();
    }

    @Override
    protected void onPostExecute(List<Category> categories) {
        for (CategoriesTaskListener listener : listeners) {
            listener.onCategoriesGetResult(categories);
        }
    }

}
