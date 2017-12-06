package com.digitalplanet.digitalplanet.dal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by marija.savtchouk on 06.12.2017.
 */

public class ProductLoader extends BaseLoader{

    public ProductLoader(Context context){
        super(context);
    }

    public ArrayList<Product> getProductsByCategory(String category) throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT + APIConstants.PRODUCTS_BY_CATEGORY_REQUEST + "?" + APIConstants.CATEGORY_PARAM + category;
        try {
            if(hasConnection()) {
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                Gson json = new Gson();
                Type type = new TypeToken<ArrayList<Product>>(){}.getType();
                ArrayList<Product> products  = json.fromJson(lineResponse, type);
                return products;
            } else {
                throw new ConnectionException();
            }
        }
        catch (ConnectionException e) {
            throw  e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ConnectionException();
        }
    }
}
