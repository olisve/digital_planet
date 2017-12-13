package com.digitalplanet.digitalplanet.dal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by marija.savtchouk on 13.12.2017.
 */

public class UserLoader  extends BaseLoader {

    public UserLoader(Context context) {
        super(context);
    }

    public User signUpUser(User user) throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT; //?
        try {
            if (hasConnection()) {
                Gson json = new Gson();
                OutputStream out = null;
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                out = new BufferedOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(json.toJson(user, User.class));
                writer.flush();
                writer.close();
                out.close();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                User postUser = json.fromJson(lineResponse, User.class);
                return postUser;
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

    public User logInUser(User user) throws ConnectionException {
        String url = APIConstants.SERVICE_ROOT; //?
        try {
            if (hasConnection()) {
                Gson json = new Gson();
                OutputStream out = null;
                URL url_obj = new URL(url);
                URLConnection conn = url_obj.openConnection();
                out = new BufferedOutputStream(conn.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(json.toJson(user, User.class));
                writer.flush();
                writer.close();
                out.close();
                Scanner stream = new Scanner(conn.getInputStream());
                String lineResponse = stream.nextLine();
                User postUser = json.fromJson(lineResponse, User.class);
                return postUser;
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
