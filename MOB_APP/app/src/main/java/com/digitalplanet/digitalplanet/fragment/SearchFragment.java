package com.digitalplanet.digitalplanet.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.digitalplanet.digitalplanet.dal.BasketItem;
import com.digitalplanet.digitalplanet.dal.ConnectionException;
import com.digitalplanet.digitalplanet.dal.ItemDbLoader;
import com.digitalplanet.digitalplanet.dal.Product;
import com.digitalplanet.digitalplanet.dal.ProductLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by marija.savtchouk on 21.12.2017.
 */

public class SearchFragment extends BaseFragment implements SearchView.OnQueryTextListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MenuItem searchItem;

    private List<Product> products = new ArrayList<>();
    public Set<String> itemsInBasket = new HashSet<>();

    private String word;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        word = getArguments().getString("Search_Word");
        return inflater.inflate(R.layout.catalog_list_main, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.catalog_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SearchFragment.ProductAdapter(products);
        mRecyclerView.setAdapter(mAdapter);
        ((AppCompatActivity) this.getActivity()).getSupportActionBar().setTitle(getString(R.string.app_name));
        //loadBasket();
        //searchItems();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(true);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_basket).setVisible(true);
        menu.findItem(R.id.action_basket).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                BasketFragment basketFragment = new BasketFragment();
                FragmentTransaction fragmentTransaction = SearchFragment.this.getFragmentManager().beginTransaction();
                fragmentTransaction.replace(((View) SearchFragment.this.getView().getParent()).getId(), basketFragment);
                fragmentTransaction.addToBackStack(String.valueOf(R.id.nav_basket));
                fragmentTransaction.commit();
                return false;
            }
        });
        searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(this);
        searchView.setQuery(word, true);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        this.word = query;
        loadBasket();
        searchItems();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }

    void loadBasket() {
        SearchFragment.LoadBasketTask ps = new SearchFragment.LoadBasketTask(getContext());
        ps.execute();
    }

    void showBasket(List<String> basket) {
        itemsInBasket.clear();
        itemsInBasket.addAll(basket);
        mAdapter.notifyDataSetChanged();
    }

    public void searchItems() {
        LoadTask ps = new LoadTask(getContext());
        ps.execute(word);
    }

    public void showItems(List<Product> _products, Context context) {
        products.clear();
        searchItem.expandActionView();
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQuery(word, false);
        if (_products.size() > 0) {
            products.addAll(_products);
            mAdapter.notifyDataSetChanged();
            return;
        }
        mAdapter.notifyDataSetChanged();
        Toast.makeText(context, "Товары не найдены.", Toast.LENGTH_LONG).show();
    }

    public class ProductAdapter extends RecyclerView.Adapter<SearchFragment.ProductAdapter.ViewHolder> {
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
        public SearchFragment.ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                           int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);
            SearchFragment.ProductAdapter.ViewHolder vh = new SearchFragment.ProductAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(final SearchFragment.ProductAdapter.ViewHolder holder, final int position) {
            Product f = mDataset.get(position);
            holder.tvDescription.setText(f.getDescription());
            holder.tvTitle.setText(f.getName());
            holder.tvPrice.setText(String.valueOf(f.getPrice()) + " бел. руб.");
            final String id = products.get(position).get_id();

            if (!itemsInBasket.contains(id)) {
                holder.bBasket.setText(getString(R.string.to_basket));
            } else {
                holder.bBasket.setText(getString(R.string.from_basket));
            }

            holder.bBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemDbLoader loader = new ItemDbLoader(SearchFragment.this.getContext());
                    if (!itemsInBasket.contains(id)) {
                        loader.setBasketItem(id, 1);
                        itemsInBasket.add(id);
                        holder.bBasket.setText(getString(R.string.from_basket));
                        Toast.makeText(getContext(), "Товар добавлен в корзину!", Toast.LENGTH_LONG).show();
                    } else {
                        loader.removeBasketItem(id);
                        itemsInBasket.remove(id);
                        holder.bBasket.setText(getString(R.string.to_basket));
                        Toast.makeText(getContext(), "Товар удалён из корзины!", Toast.LENGTH_LONG).show();
                    }
                }
            });
            holder.cardView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ProductFragment productFragment = new ProductFragment();

                            FragmentTransaction fragmentTransaction = SearchFragment.this.getFragmentManager().beginTransaction();
                            Bundle bundle = new Bundle();
                            bundle.putString("Product_ID", id); // ID
                            productFragment.setArguments(bundle);
                            fragmentTransaction.replace(((View) SearchFragment.this.getView().getParent()).getId(), productFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    }
            );
            if (f.getImagePath() != null && !f.getImagePath().isEmpty()) {
                String url = APIConstants.SERVICE_ROOT + APIConstants.IMAGE_REQUEST + "?" + APIConstants.URL_PARAM + f.getImagePath();
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

    private class LoadTask extends AsyncTask<String, Void, List<Product>> {
        private final Context context;
        private ProgressDialog progress;

        LoadTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(this.context);
            progress.setMessage("Загрузка товаров...");
            progress.show();
        }

        @Override
        protected void onPostExecute(List<Product> items) {
            super.onPostExecute(items);
            progress.dismiss();
            showItems(items, context);
        }

        @Override
        protected List<Product> doInBackground(String... params) {
            ArrayList<Product> products = new ArrayList<Product>();
            ProductLoader loader = new ProductLoader(context);
            String word = params[0];
            try {
                products = loader.getProductsBySearch(word);
            } catch (ConnectionException e) {
                return new ArrayList<>();
            }

            return products;
        }
    }

    private class LoadBasketTask extends AsyncTask<String, Void, List<String>> {
        private final Context context;
        private ProgressDialog progress;

        LoadBasketTask(Context c) {
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
        protected void onPostExecute(List<String> basket) {
            super.onPostExecute(basket);
            progress.dismiss();
            showBasket(basket);
        }

        @Override
        protected List<String> doInBackground(String... params) {
            List<String> products = new ArrayList<String>();
            ItemDbLoader loader = new ItemDbLoader(context);
            List<BasketItem> basket = loader.getBasketItems();
            for (BasketItem b : basket) {
                products.add(b.get_id());
            }
            return products;
        }
    }
}

