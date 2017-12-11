package com.digitalplanet.digitalplanet.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.R;
import com.digitalplanet.digitalplanet.dal.APIConstants;
import com.digitalplanet.digitalplanet.dal.ConnectionException;
import com.digitalplanet.digitalplanet.dal.Product;
import com.digitalplanet.digitalplanet.dal.ProductLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by marija.savtchouk on 07.12.2017.
 */

public class ProductFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Product product;
    public String productId;
    public ImageView product_photo;
    public Button price_button;
    public Button to_basket_button;
    public TextView product_name;

    private List<Map.Entry<String, String>> description = new ArrayList<>();

    public ProductFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        productId = getArguments().getString("Product_ID");
        return inflater.inflate(R.layout.product_layout, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        product_photo = view.findViewById(R.id.iv_product_photo);
        product_name = view.findViewById(R.id.tv_name_product);
        price_button = view.findViewById(R.id.btn_price_basket);
        to_basket_button = view.findViewById(R.id.btn_to_basket);
        price_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Товар в корзину id" + productId, Toast.LENGTH_LONG).show();
            }
        });
        to_basket_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Товар в корзину id" + productId, Toast.LENGTH_LONG).show();
            }
        });
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.description_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProductAdapter(description);
        mRecyclerView.setAdapter(mAdapter);
        loadProduct();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_basket).setVisible(true);
        menu.findItem(R.id.action_basket).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                BasketFragment basketFragment = new BasketFragment();
                FragmentTransaction fragmentTransaction = ProductFragment.this.getFragmentManager().beginTransaction();
                fragmentTransaction.replace(((View) ProductFragment.this.getView().getParent()).getId(), basketFragment);
                fragmentTransaction.addToBackStack(String.valueOf(R.id.nav_basket));
                fragmentTransaction.commit();
                return false;
            }
        });
    }

    void loadProduct() {
        LoadProductTask ps = new LoadProductTask(getContext());
        ps.execute(productId);
    }

    void showProduct(Product _product) {
        if (_product != null) {
            product = _product;
            description.clear();
            if (_product.getCharacteristics().size() > 0) {
                description.addAll(_product.getCharacteristics().entrySet());
                mAdapter.notifyDataSetChanged();
            }
            product_name.setText(product.getName());
            price_button.setText(String.valueOf(product.getPrice()) + " бел. руб.");
            ((AppCompatActivity) this.getActivity()).getSupportActionBar().setTitle(product.getName());
            if (product.getImagePath() != null && !product.getImagePath().isEmpty()) {
                String url = APIConstants.SERVICE_ROOT + APIConstants.IMAGE_REQUEST + "?" + APIConstants.URL_PARAM + product.getImagePath();
                Picasso.with(getContext()).load(url).into(product_photo);
            } else {
                product_photo.setBackgroundColor(getResources().getColor(R.color.colorAccentBack));
            }
        } else {
            Toast.makeText(getContext(), "Network error", Toast.LENGTH_LONG).show();
        }
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
        private List<Map.Entry<String, String>> mDataset;

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView tvName, tvDescription;

            public ViewHolder(View v) {
                super(v);
                tvName = (TextView) v.findViewById(R.id.tv_product_name);
                tvDescription = (TextView) v.findViewById(R.id.tv_product_description);
            }
        }

        public ProductAdapter(List<Map.Entry<String, String>> myDataset) {
            mDataset = myDataset;
        }

        @Override
        public ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_description_layout, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Map.Entry<String, String> f = mDataset.get(position);
            holder.tvName.setText(f.getKey());
            holder.tvDescription.setText(f.getValue());
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private class LoadProductTask extends AsyncTask<String, Void, Product> {
        private final Context context;
        private ProgressDialog progress;

        LoadProductTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(this.context);
            progress.setMessage("Загрузка товара...");
            progress.show();
        }

        @Override
        protected void onPostExecute(Product product) {
            super.onPostExecute(product);
            progress.dismiss();
            showProduct(product);
        }

        @Override
        protected Product doInBackground(String... params) {
            Product product = null;
            ProductLoader loader = new ProductLoader(context);
            try {
                product = loader.getProductById(params[0]);
            } catch (ConnectionException e) {
                return null;
            }
            return product;
        }
    }
}
