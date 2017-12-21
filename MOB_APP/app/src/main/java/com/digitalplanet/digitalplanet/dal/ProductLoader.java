package com.digitalplanet.digitalplanet.dal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by marija.savtchouk on 06.12.2017.
 */

public class ProductLoader extends BaseLoader {

    public ProductLoader(Context context) {
        super(context);
    }

    public ArrayList<Product> getProductsByCategory(String category) throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT + APIConstants.PRODUCTS_BY_CATEGORY_REQUEST + "?" + APIConstants.CATEGORY_PARAM + category;
        try {
            if (hasConnection()) {
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                Gson json = new Gson();
                Type type = new TypeToken<ArrayList<Product>>() {
                }.getType();
                ArrayList<Product> products = json.fromJson(lineResponse, type);
                return products;
            } else {
                throw new ConnectionException();
            }
        } catch (ConnectionException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }

    public Product getProductById(String id) throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT + APIConstants.PRODUCT_REQUEST + "?" + APIConstants.ID_PARAM + id;
        try {
            if (hasConnection()) {
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                Gson json = new Gson();
                Product product = json.fromJson(lineResponse, Product.class);
                return product;
            } else {
                throw new ConnectionException();
            }
        } catch (ConnectionException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }

    public ArrayList<Product> getProductsByFilter(String category, String brands, String price_from, String price_to) throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT + APIConstants.FILTER_REQUEST + "?"
                + APIConstants.CATEGORY_PARAM + category;
        if (!brands.isEmpty()) {
            url += "&" + APIConstants.BRAND_PARAM + brands;
        }
        if (!price_from.isEmpty()) {
            url += "&" + APIConstants.FROM_PARAM + price_from;
        }
        if (!price_to.isEmpty()) {
            url += "&" + APIConstants.TO_PARAM + price_to;
        }
        try {
            if (hasConnection()) {
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                Gson json = new Gson();
                Type type = new TypeToken<ArrayList<Product>>() {
                }.getType();
                ArrayList<Product> products = json.fromJson(lineResponse, type);
                return products;
            } else {
                throw new ConnectionException();
            }
        } catch (ConnectionException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }

    public ArrayList<Product> getProductsBySearch(String search) throws ConnectionException {
        search = search.replaceAll(" ", "%20");
        String url = APIConstants.SERVICE_ROOT + APIConstants.SEARCH_REQUEST + "?" + APIConstants.WORD_PARAM + search;
        try {
            if (hasConnection()) {
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                Gson json = new Gson();
                Type type = new TypeToken<ArrayList<Product>>() {
                }.getType();
                ArrayList<Product> products = json.fromJson(lineResponse, type);
                return products;
            } else {
                throw new ConnectionException();
            }
        } catch (ConnectionException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }
}
