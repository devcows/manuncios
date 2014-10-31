package com.devcows.manuncios;

import android.app.Fragment;
import android.content.Context;

public abstract class FragmentReturn extends Fragment {
    protected Context context;

    public FragmentReturn() {
    }

    public abstract String getReturnName();
}
