package com.devcows.manuncios;

import android.content.Context;
import android.support.v4.app.Fragment;

public abstract class FragmentReturn extends Fragment {
    protected Context context;

    public FragmentReturn() {
    }

    public abstract String getReturnName();
}
