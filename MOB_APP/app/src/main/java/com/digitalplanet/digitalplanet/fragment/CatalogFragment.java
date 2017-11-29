package com.digitalplanet.digitalplanet.fragment;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.MainActivity;
import com.digitalplanet.digitalplanet.R;
import com.digitalplanet.digitalplanet.fragment.*;
import java.util.ArrayList;

/**
 * Created by marija.savtchouk on 29.11.2017.
 */

public class CatalogFragment  extends Fragment {
    public CatalogFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> drowable_ids = new ArrayList<Integer>();
        names.add("Бытовая техника");
        names.add("Мобильные телефоны");
        names.add("TV");
        names.add("Камеры");
        drowable_ids.add(R.drawable.ic_menu_camera);
        drowable_ids.add(R.drawable.ic_menu_gallery);
        drowable_ids.add(R.drawable.ic_menu_send);
        drowable_ids.add(R.drawable.ic_menu_share);
        View v = inflater.inflate(R.layout.content_main, container, false);
        LinearLayout layout = v.findViewById(R.id.button_layout_add);
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        for(int i = 0; i < names.size(); i++) {
            //set the properties for button
            Button btnTag = new Button(layout.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, (int) (metrics.density * 24.0f + 0.5f));
            btnTag.setLayoutParams(params);
            btnTag.setText(names.get(i));
            btnTag.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
            btnTag.setPadding(0, (int) (metrics.density * 10.0f + 0.5f), 0, (int) (metrics.density * 10.0f + 0.5f));
            btnTag.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_item_shape));
            btnTag.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(drowable_ids.get(i)), null, null, null);

            btnTag.setId(i);
            btnTag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CatalogListView catalogListFragment = new CatalogListView();

                    FragmentTransaction fragmentTransaction = CatalogFragment.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(((View)CatalogFragment.this.getView().getParent()).getId(), catalogListFragment);
                    Toast.makeText(getContext(), "Found"+String.valueOf(CatalogFragment.this.getFragmentManager()), Toast.LENGTH_LONG).show();
                    fragmentTransaction.commit();
                }
            });
            //add button to the layout
            layout.addView(btnTag);
        }
        for(int i = 0; i < names.size(); i++) {
            //set the properties for button
            Button btnTag = new Button(layout.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, (int) (metrics.density * 24.0f + 0.5f));
            btnTag.setLayoutParams(params);
            btnTag.setText(names.get(i));
            btnTag.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f);
            btnTag.setPadding(0, (int) (metrics.density * 10.0f + 0.5f), 0, (int) (metrics.density * 10.0f + 0.5f));
            btnTag.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_item_shape));
            btnTag.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(drowable_ids.get(i)), null, null, null);

            btnTag.setId(i*10);
            //add button to the layout
            layout.addView(btnTag);
        }
        return v;
    }


}
