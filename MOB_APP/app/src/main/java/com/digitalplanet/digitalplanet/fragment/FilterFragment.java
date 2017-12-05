package com.digitalplanet.digitalplanet.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.R;
import com.digitalplanet.digitalplanet.dal.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marija.savtchouk on 05.12.2017.
 */

public class FilterFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<String> producers = new ArrayList<>();
    public String categoryName;
    public String categoryId;

    public FilterFragment() {
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
        categoryName = getArguments().getString("Category_Name");
        categoryId = getArguments().getString("Category_ID");
        return inflater.inflate(R.layout.filter_view, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.producers_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new FilterFragment.ProducersAdapter(producers);
        mRecyclerView.setAdapter(mAdapter);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.filter));
        searchProducers();
    }

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_basket).setVisible(false);
    }

    public void searchProducers() {
        FilterFragment.LoadTask ps = new FilterFragment.LoadTask(getContext());
        ps.execute("Category");
    }

    public void showProducers(List<String> _producers) {
        producers.clear();
        if (_producers.size() > 0) {
            producers.addAll(_producers);
            mAdapter.notifyDataSetChanged();
            return;
        }
    }

    public class ProducersAdapter extends RecyclerView.Adapter<FilterFragment.ProducersAdapter.ViewHolder> {
        private List<String> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public CheckBox tvTitle;

            public ViewHolder(View v) {
                super(v);
                tvTitle = (CheckBox) v.findViewById(R.id.tv_producer_title);
            }
        }

        public ProducersAdapter(List<String> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public FilterFragment.ProducersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.producer_filter_layout, parent, false);
            FilterFragment.ProducersAdapter.ViewHolder vh = new FilterFragment.ProducersAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(FilterFragment.ProducersAdapter.ViewHolder holder, final int position) {
            String f = mDataset.get(position);
            holder.tvTitle.setText(f);
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private class LoadTask extends AsyncTask<String, Void, List<String>> {
        private final Context context;
        private ProgressDialog progress;

        LoadTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= new ProgressDialog(this.context);
            progress.setMessage("Загрузка параметров...");
            progress.show();
        }

        @Override
        protected void onPostExecute(List<String> items) {
            super.onPostExecute(items);
            progress.dismiss();
            showProducers(items);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            ArrayList<String> producers = new ArrayList<String>();
            producers.add("Samsung");
            producers.add("Bosh");
            producers.add("Nokia");
            producers.add("LG");
            producers.add("Apple");
            try {
                Thread.sleep(1000);
            }catch (Exception e){}
            return producers;
        }
    }
}
