package com.digitalplanet.digitalplanet.dal;

import android.content.Context;
import android.util.Log;

import com.digitalplanet.digitalplanet.LogInActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        String url = APIConstants.SERVICE_ROOT + APIConstants.SIGNUP_REQUEST;
        try {
            if (hasConnection()) {
                Gson json = new Gson();
                URL url_obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) url_obj.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);

                String userString = APIConstants.EMAIL_PARAM + user.getEmail() +
                        "&" + APIConstants.PASSWORD_PARAM + user.getPassword() +
                        "&" + APIConstants.FIRSTNAME_PARAM + user.getFirstName() +
                        "&" + APIConstants.LASTNAME_PARAM + user.getLastName() +
                        "&" + APIConstants.ADDRESS_PARAM + user.getAddress() +
                        "&" + APIConstants.PHONE_PARAM + user.getPhone();
                userString.replaceAll(" ", "%20");
                byte[] postData = userString.getBytes(Charset.forName("UTF-8"));
                int postDataLength = postData.length;
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);

                wr.flush();
                wr.close();
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
        String url = APIConstants.SERVICE_ROOT + APIConstants.LOGIN_REQUEST;
        try {
            if (hasConnection()) {
                Gson json = new Gson();
                URL url_obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) url_obj.openConnection();
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestMethod("POST");
                conn.setUseCaches(false);

                String userString = APIConstants.EMAIL_PARAM + user.getEmail() +
                        "&" + APIConstants.PASSWORD_PARAM + user.getPassword();
                userString.replaceAll(" ", "%20");
                byte[] postData = userString.getBytes(Charset.forName("UTF-8"));
                int postDataLength = postData.length;
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                wr.write(postData);

                wr.flush();
                wr.close();
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
