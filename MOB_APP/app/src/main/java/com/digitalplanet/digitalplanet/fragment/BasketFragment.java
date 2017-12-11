package com.digitalplanet.digitalplanet.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.R;
import com.digitalplanet.digitalplanet.dal.APIConstants;
import com.digitalplanet.digitalplanet.dal.BasketItem;
import com.digitalplanet.digitalplanet.dal.Category;
import com.digitalplanet.digitalplanet.dal.ConnectionException;
import com.digitalplanet.digitalplanet.dal.ItemDbLoader;
import com.digitalplanet.digitalplanet.dal.Product;
import com.digitalplanet.digitalplanet.dal.ProductLoader;
import com.squareup.picasso.Picasso;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by marija.savtchouk on 07.12.2017.
 */

public class BasketFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public TextView tvTotal;
    public Button bOrder;

    private List<Map.Entry<Product, BasketItem>> products = new ArrayList<>();

    public BasketFragment() {
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
        return inflater.inflate(R.layout.basket_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.basket_items_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProductAdapter(products);
        mRecyclerView.setAdapter(mAdapter);
        ((AppCompatActivity) this.getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.basket));
        bOrder = view.findViewById(R.id.btn_order_all);
        tvTotal = view.findViewById(R.id.tv_total);
        bOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Заказ оформлен", Toast.LENGTH_LONG).show();
            }
        });
        loadProducts();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_basket).setVisible(false);
    }

    void loadProducts() {
        LoadTask ps = new LoadTask(getContext());
        ps.execute();
    }

    void showProducts(List<Map.Entry<Product, BasketItem>> _products) {
        products.clear();
        if (_products.size() > 0) {
            products.addAll(_products);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Корзина пуста.", Toast.LENGTH_LONG).show();
        }

        double total = 0;
        for (Map.Entry<Product, BasketItem> item : products) {

            total += item.getKey().getPrice() * item.getValue().getCount();
        }
        tvTotal.setText(String.valueOf(total) + " бел. руб.");

    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private List<Map.Entry<Product, BasketItem>> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvTitle, tvPrice, tvCount;
            public ImageView ivPhoto;
            public ImageView deleteItem;
            public Button plusItem, minusItem, countItem;

            public ViewHolder(View v) {
                super(v);
                tvTitle = (TextView) v.findViewById(R.id.tv_name_basket);
                tvPrice = (TextView) v.findViewById(R.id.tv_price_basket);
                plusItem = (Button) v.findViewById(R.id.btn_plus_basket);
                countItem = (Button) v.findViewById(R.id.btn_count_basket);
                minusItem = (Button) v.findViewById(R.id.btn_minus_basket);
                deleteItem = (ImageView) v.findViewById(R.id.iv_delete_basket);
                ivPhoto = (ImageView) v.findViewById(R.id.iv_basket_photo);
            }
        }

        public ProductAdapter(List<Map.Entry<Product, BasketItem>> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.basket_item_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Map.Entry<Product, BasketItem> f = mDataset.get(position);
            holder.tvTitle.setText(f.getKey().getName());
            holder.tvPrice.setText(getString(R.string.price) + String.valueOf(f.getKey().getPrice()) + " бел. руб.");
            holder.countItem.setText(String.valueOf(f.getValue().getCount()));
            final String id = products.get(position).getKey().get_id();
            holder.plusItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Добавить" + id, Toast.LENGTH_LONG).show();
                }
            });
            holder.minusItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Убрать" + id, Toast.LENGTH_LONG).show();
                }
            });
            holder.deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "Удалить всё" + id, Toast.LENGTH_LONG).show();
                }
            });
            if (f.getKey().getImagePath() != null && !f.getKey().getImagePath().isEmpty()) {
                String url = APIConstants.SERVICE_ROOT + APIConstants.IMAGE_REQUEST + "?" + APIConstants.URL_PARAM + f.getKey().getImagePath();
                Picasso.with(getContext()).load(url).into(holder.ivPhoto);
            } else {
                holder.ivPhoto.setBackgroundColor(getResources().getColor(R.color.colorAccentBack));
            }
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private class LoadTask extends AsyncTask<String, Void, List<Map.Entry<Product, BasketItem>>> {
        private final Context context;
        private ProgressDialog progress;

        LoadTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(this.context);
            progress.setMessage("Загрузка корзины...");
            progress.show();
        }

        @Override
        protected void onPostExecute(List<Map.Entry<Product, BasketItem>> items) {
            super.onPostExecute(items);
            progress.dismiss();
            showProducts(items);
        }

        @Override
        protected List<Map.Entry<Product, BasketItem>> doInBackground(String... params) {
            ArrayList<Map.Entry<Product, BasketItem>> products = new ArrayList<Map.Entry<Product, BasketItem>>();
            ItemDbLoader dbLoader = new ItemDbLoader(context);
            ProductLoader loader = new ProductLoader(context);
            List<BasketItem> basketItems = dbLoader.getBasketItems();
            for (BasketItem item : basketItems) {
                try {
                    products.add(new AbstractMap.SimpleEntry<>(loader.getProductById(item.get_id()), item));
                } catch (ConnectionException e) {
                    return new ArrayList<>();
                }
            }
            /*for (int i = 0; i < 5; i++) {
                Product p = new Product();
                p.set_id("#" + i);
                p.setPrice((i + 1) * 10);
                p.setName("Samsung #" + i);
                BasketItem b = new BasketItem();
                b.set_id(p.get_id());
                b.setCount(i + 1);
                products.add(new AbstractMap.SimpleEntry<>(p, b));
            }
            */
            return products;
        }
    }
}