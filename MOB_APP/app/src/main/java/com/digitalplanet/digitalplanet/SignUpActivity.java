package com.digitalplanet.digitalplanet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.dal.ConnectionException;
import com.digitalplanet.digitalplanet.dal.Product;
import com.digitalplanet.digitalplanet.dal.ProductLoader;
import com.digitalplanet.digitalplanet.dal.User;
import com.digitalplanet.digitalplanet.dal.UserDbLoader;
import com.digitalplanet.digitalplanet.dal.UserLoader;

public class SignUpActivity extends AppCompatActivity {

    EditText nameText;
    EditText lastNameText;
    EditText emailText;
    EditText phoneText;
    EditText postalText;
    EditText passwordText;
    Button signupButton;
    TextView loginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameText = findViewById(R.id.input_name);
        lastNameText = findViewById(R.id.input_last_name);
        emailText = findViewById(R.id.input_email);
        passwordText = findViewById(R.id.input_password);
        phoneText = findViewById(R.id.input_phone);
        postalText = findViewById(R.id.input_postal);
        signupButton = findViewById(R.id.btn_signup);
        loginLink = findViewById(R.id.link_login);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signup() {

        if (!validate()) {
            return;
        }

        String name = nameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String phone = phoneText.getText().toString();
        String postal = postalText.getText().toString();
        User user = new User();
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setAddress(postal);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        PostSignupTask task = new PostSignupTask(SignUpActivity.this);
        task.execute(user);
    }

    public void onSignupFailed() {
        Toast.makeText(this, "Не верно введены данные", Toast.LENGTH_LONG).show();
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String lastname = lastNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String phone = phoneText.getText().toString();
        String postal = postalText.getText().toString();

        if (name.isEmpty() || name.length() < 1) {
            nameText.setError("не менее 1 буквы");
            valid = false;
        } else {
            nameText.setError(null);
        }
        if (lastname.isEmpty() || lastname.length() < 1) {
            lastNameText.setError("не менее 1 буквы");
            valid = false;
        } else {
            lastNameText.setError(null);
        }


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

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            phoneText.setError("введите правильный телефон");
            valid = false;
        } else {
            phoneText.setError(null);
        }


        if (postal.isEmpty()) {
            postalText.setError("введите домашний адрес");
            valid = false;
        } else {
            postalText.setError(null);
        }

        return valid;
    }

    void gotoCatalog(User user) {
        if(user!=null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        else{
            onSignupFailed();
        }
    }

    private class PostSignupTask extends AsyncTask<User, Void, User> {
        private final Context context;
        private ProgressDialog progress;

        PostSignupTask(Context c) {
            this.context = c;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(this.context);
            progress.setMessage("Регистрация...");
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
                resultUser = loader.signUpUser(params[0]);
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
