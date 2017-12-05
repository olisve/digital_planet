package com.digitalplanet.digitalplanet.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.MainActivity;
import com.digitalplanet.digitalplanet.R;
import com.digitalplanet.digitalplanet.dal.ConnectionException;
import com.digitalplanet.digitalplanet.dal.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marija.savtchouk on 29.11.2017.
 */

public class CatalogListView   extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Product> products = new ArrayList<>();
    public String categoryName;
    public String categoryId;

    public CatalogListView() {
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

        return inflater.inflate(R.layout.catalog_list_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.catalog_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProductAdapter(products);
        mRecyclerView.setAdapter(mAdapter);
        ((AppCompatActivity)this.getActivity()).getSupportActionBar().setTitle(categoryName);
        searchItems();

    }

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_filter).setVisible(true);

        menu.findItem(R.id.action_filter).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                FilterFragment filterFragment = new FilterFragment();

                FragmentTransaction fragmentTransaction = CatalogListView.this.getFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("Category_Name", CatalogListView.this.categoryName); // Name
                bundle.putString("Category_ID", CatalogListView.this.categoryId); // ID
                filterFragment.setArguments(bundle);
                fragmentTransaction.replace(((View) CatalogListView.this.getView().getParent()).getId(), filterFragment);
                fragmentTransaction.commit();
                return false;
            }
        });
    }

    public void searchItems() {
        LoadTask ps = new LoadTask(getContext());
        ps.execute("Category");
    }

    public void showItems(List<Product> _products) {
        products.clear();
        if (_products.size() > 0) {
            products.addAll(_products);
            mAdapter.notifyDataSetChanged();
            return;
        }
        Toast.makeText(getContext(), "Товары не найдены.", Toast.LENGTH_LONG).show();
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private List<Product> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvTitle, tvDescription, tvPrice;
            public ImageView ivPhoto;
            public Button bBasket;
            public CardView cardView;

            public ViewHolder(View v) {
                super(v);
                tvTitle = (TextView) v.findViewById(R.id.tv_item_title);
                tvPrice = (TextView) v.findViewById(R.id.tv_item_price);
                tvDescription = (TextView) v.findViewById(R.id.tv_item_description);
                ivPhoto = (ImageView) v.findViewById(R.id.iv_item_photo);
                bBasket = (Button) v.findViewById(R.id.bt_basket);
                cardView = (CardView) v.findViewById(R.id.card_item_view);
            }
        }

        public ProductAdapter(List<Product> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Product f = mDataset.get(position);
            holder.tvDescription.setText(f.getDescription());
            holder.tvTitle.setText(f.getName());
            holder.tvPrice.setText(String.valueOf(f.getPrice())+" бел. руб.");
            final String id = products.get(position).get_id();
            holder.bBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Товар в корзину id" + id, Toast.LENGTH_LONG).show();
                }
            });
            holder.cardView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Товар детально id" + id, Toast.LENGTH_LONG).show();
                        }
                    }
            );

            holder.ivPhoto.setBackgroundColor(getResources().getColor(R.color.colorAccentBack));
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private class LoadTask extends AsyncTask<String, Void, List<Product>> {
        private final Context context;
        private ProgressDialog progress;

        LoadTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress= new ProgressDialog(this.context);
            progress.setMessage("Загрузка товаров...");
            progress.show();
        }

        @Override
        protected void onPostExecute(List<Product> items) {
            super.onPostExecute(items);
            progress.dismiss();
            showItems(items);
        }

        @Override
        protected List<Product> doInBackground(String... params) {
            ArrayList<Product> productes = new ArrayList<Product>();
            for(int i = 0; i < 5; i++) {
                Product p = new Product();
                p.set_id("ID#"+i);
                p.setDescription("Прекрасный продукт для всей семьи. Излечит вас от всех болезней. Прекрасная цена. Ознакомтесь с отзывами.");
                p.setName("Samsung Galaxy X 10");
                p.setPrice(560);
                productes.add(p);
            }
            try {
                Thread.sleep(1000);
            }catch (Exception e){}
            return productes;
        }
    }


}
