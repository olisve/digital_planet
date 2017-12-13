package com.digitalplanet.digitalplanet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.dal.ConnectionException;
import com.digitalplanet.digitalplanet.dal.User;
import com.digitalplanet.digitalplanet.dal.UserDbLoader;
import com.digitalplanet.digitalplanet.dal.UserLoader;

public class LogInActivity extends AppCompatActivity {

    EditText emailText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        signupLink = findViewById(R.id.link_signup);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the Signup activity
                finish();
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    public void login() {

        if (!validate()) {
            return;
        }


        final String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        PostLoginTask task = new PostLoginTask(LogInActivity.this);
        task.execute(user);
    }

    void gotoCatalog(User user) {
        if(user!=null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else{
            onLoginFailed();
        }
    }

    public void onLoginFailed() {
        Toast.makeText(this, "Не верно введены данные.", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("введите правильный email");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 5) {
            passwordText.setError("не менее 5 символов");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }

    private class PostLoginTask extends AsyncTask<User, Void, User> {
        private final Context context;
        private ProgressDialog progress;

        PostLoginTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(this.context);
            progress.setMessage("Верификация...");
            progress.show();
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            progress.dismiss();
            gotoCatalog(user);
        }

        @Override
        protected User doInBackground(User... params) {
            User resultUser = null;
            UserLoader loader = new UserLoader(context);
            try {
                resultUser = loader.logInUser(params[0]);
                UserDbLoader dbLoader = new UserDbLoader(context);
                dbLoader.saveUser(resultUser);
            } catch (ConnectionException e) {
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return resultUser;
        }
    }
}
