package com.digitalplanet.digitalplanet.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.MainActivity;

/**
 * Created by marija.savtchouk on 05.12.2017.
 */

public abstract class BaseFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {

    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_TITLE = "extra_title";


    private MainActivity mainActivity;

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(getContext(), "Backarrow pressed", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();

        getFragmentManager().addOnBackStackChangedListener(this);
        mainActivity.getSupportFragmentManager().addOnBackStackChangedListener(this);
        int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
        mainActivity.setDrawer(backStackEntryCount == 0);
    }

    @Override
    public void onBackStackChanged() {
        if (getFragmentManager() != null) {
            int backStackEntryCount = getFragmentManager().getBackStackEntryCount();
            mainActivity.setDrawer(backStackEntryCount == 0);
        } else {
            int backStackEntryCount = mainActivity.getSupportFragmentManager().getBackStackEntryCount();
            mainActivity.setDrawer(backStackEntryCount == 0);
        }
    }
}