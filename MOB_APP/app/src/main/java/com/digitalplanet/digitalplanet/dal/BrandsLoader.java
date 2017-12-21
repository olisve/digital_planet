package com.digitalplanet.digitalplanet.dal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by marija.savtchouk on 21.12.2017.
 */

public class BrandsLoader extends BaseLoader {

    public BrandsLoader(Context context) {
        super(context);
    }

    public ArrayList<String> getBrandsByCategory(String category) throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT + APIConstants.BRANDS_REQUEST + "?" + APIConstants.CATEGORY_PARAM + category;
        try {
            if (hasConnection()) {
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                RestTemplate restTemplate = new RestTemplate(true);
                Gson json = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();
                ArrayList<String> brands = json.fromJson(lineResponse, type);
                return brands;
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

