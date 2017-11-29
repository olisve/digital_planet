package com.digitalplanet.digitalplanet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.digitalplanet.digitalplanet.R;

import java.util.ArrayList;

/**
 * Created by marija.savtchouk on 29.11.2017.
 */

public class CatalogListView   extends Fragment {
    public CatalogListView() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.catalog_list_main, container, false);
    }

}
