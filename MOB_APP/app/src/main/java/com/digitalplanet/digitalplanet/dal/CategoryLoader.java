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
 * Created by marija.savtchouk on 06.12.2017.
 */

public class CategoryLoader extends BaseLoader{

    public CategoryLoader(Context context){
        super(context);
    }

    public ArrayList<Category> getAllCategories() throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT+APIConstants.ALL_CATEGORIES_REQUEST;
        try {
            if(hasConnection()) {
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                RestTemplate restTemplate = new RestTemplate(true);
                Gson json = new Gson();
                Type type = new TypeToken<ArrayList<Category>>(){}.getType();
                ArrayList<Category> categories  = json.fromJson(lineResponse, type);
                return categories;
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
