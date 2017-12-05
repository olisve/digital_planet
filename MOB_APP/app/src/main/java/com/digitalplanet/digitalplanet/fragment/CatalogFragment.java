package com.digitalplanet.digitalplanet.fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.MainActivity;
import com.digitalplanet.digitalplanet.R;
import com.digitalplanet.digitalplanet.dal.Category;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marija.savtchouk on 29.11.2017.
 */

public class CatalogFragment  extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Category> categories = new ArrayList<>();

    public CatalogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.category_menu_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CatalogFragment.CategoryAdapter(categories);
        mRecyclerView.setAdapter(mAdapter);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        searchCategories();
    }
    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_basket).setVisible(true);
    }

    public void searchCategories() {
        CatalogFragment.LoadTask ps = new CatalogFragment.LoadTask(getContext());
        ps.execute();
    }

    public void showCategories(List<Category> _categories) {
        categories.clear();
        if (_categories.size() > 0) {
            categories.addAll(_categories);
            mAdapter.notifyDataSetChanged();
            return;
        }
        Toast.makeText(getContext(), "Каталог пуст.", Toast.LENGTH_LONG).show();
    }

    public class CategoryAdapter extends RecyclerView.Adapter<CatalogFragment.CategoryAdapter.ViewHolder> {
        private List<Category> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public Button bCategory;

            public ViewHolder(View v) {
                super(v);
                bCategory = (Button) v.findViewById(R.id.bt_category);
            }
        }

        public CategoryAdapter(List<Category> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public CatalogFragment.CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_layout, parent, false);
            CatalogFragment.CategoryAdapter.ViewHolder vh = new CatalogFragment.CategoryAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(CatalogFragment.CategoryAdapter.ViewHolder holder, final int position) {
            final Category f = mDataset.get(position);
            holder.bCategory.setText(f.getName());
            final String id = categories.get(position).get_id();
            ArrayList<Integer> drowable_ids = new ArrayList<Integer>();
            drowable_ids.add(R.drawable.ic_weekend_black_24dp);
            drowable_ids.add(R.drawable.ic_phone_android_black_24dp);
            drowable_ids.add(R.drawable.ic_tv_black_24dp);
            drowable_ids.add(R.drawable.ic_photo_camera_black_24dp);
            int i = Integer.parseInt(Character.toString(id.charAt(id.length()-1)));
            holder.bCategory.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(drowable_ids.get(i)), null, null, null);
            holder.bCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CatalogListView catalogListFragment = new CatalogListView();

                    FragmentTransaction fragmentTransaction = CatalogFragment.this.getFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("Category_Name", f.getName()); // Name
                    bundle.putString("Category_ID", f.get_id()); // ID
                    catalogListFragment.setArguments(bundle);
                    fragmentTransaction.replace(((View)CatalogFragment.this.getView().getParent()).getId(), catalogListFragment);
                    fragmentTransaction.commit();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
    private class LoadTask extends AsyncTask<String, Void, List<Category>> {
        private final Context context;
        private ProgressDialog progress;

        LoadTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= new ProgressDialog(this.context);
            progress.setMessage("Загрузка каталога...");
            progress.show();
        }

        @Override
        protected void onPostExecute(List<Category> items) {
            super.onPostExecute(items);
            progress.dismiss();
            showCategories(items);
        }

        @Override
        protected List<Category> doInBackground(String... params) {
            ArrayList<String> names = new ArrayList<String>();
            names.add("Бытовая техника");
            names.add("Мобильные телефоны");
            names.add("TV");
            names.add("Камеры");
            ArrayList<Category> categories = new ArrayList<Category>();
            for(int i = 0; i < names.size(); i++) {
                Category c = new Category();
                c.set_id("ID#"+i);
                c.setName(names.get(i));
                categories.add(c);
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e){}
            return categories;
        }
    }


}
