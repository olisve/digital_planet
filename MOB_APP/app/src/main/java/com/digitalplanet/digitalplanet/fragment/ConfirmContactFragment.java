package com.digitalplanet.digitalplanet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.digitalplanet.digitalplanet.R;
import com.digitalplanet.digitalplanet.dal.BasketItem;
import com.digitalplanet.digitalplanet.dal.ItemDbLoader;
import com.digitalplanet.digitalplanet.dal.OrderLoader;
import com.digitalplanet.digitalplanet.dal.User;
import com.digitalplanet.digitalplanet.dal.UserDbLoader;

import java.util.List;


/**
 * Created by marija.savtchouk on 19.12.2017.
 */

public class ConfirmContactFragment extends BaseFragment {

    EditText nameText;
    EditText emailText;
    EditText phoneText;
    EditText postalText;
    public Button bConfirm;

    public ConfirmContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.confirm_form, container, false);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();
        String postal = postalText.getText().toString();

        if (name.isEmpty() || name.length() < 1) {
            nameText.setError("не менее 1 буквы");
            valid = false;
        } else {
            nameText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("введите правильный email");
            valid = false;
        } else {
            emailText.setError(null);
        }
        phone = phone.replaceAll("\\(", "");
        phone = phone.replaceAll("\\)", "");
        phone = phone.replaceAll("-", "");
        phone = phone.trim();
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


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) this.getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.contact_confirm));
        bConfirm = view.findViewById(R.id.btn_confirm);
        nameText = view.findViewById(R.id.input_name_confirm);
        emailText = view.findViewById(R.id.input_email_confirm);
        phoneText = view.findViewById(R.id.input_phone_confirm);
        postalText = view.findViewById(R.id.input_postal_confirm);
        UserDbLoader loader = new UserDbLoader(getContext());
        User user = loader.getUser();
        nameText.setText(user.getFirstName());
        emailText.setText(user.getEmail());
        phoneText.setText(user.getPhone());
        postalText.setText(user.getAddress());
        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    String name = nameText.getText().toString();
                    String email = emailText.getText().toString();
                    String phone = phoneText.getText().toString();
                    String postal = postalText.getText().toString();
                    UserDbLoader loader = new UserDbLoader(getContext());
                    User user = loader.getUser();
                    user.setPhone(phone);
                    user.setEmail(email);
                    user.setAddress(postal);
                    user.setFirstName(name);
                    ItemDbLoader dbLoader = new ItemDbLoader(ConfirmContactFragment.this.getContext());
                    List<BasketItem> basketItems = dbLoader.getBasketItems();
                    dbLoader.cleanBasket();
                    OrderLoader orderLoader = new OrderLoader(ConfirmContactFragment.this.getContext());
                    orderLoader.sendOrder(user, basketItems);
                    BasketFragment catalogListFragment = new BasketFragment();
                    FragmentTransaction fragmentTransaction = ConfirmContactFragment.this.getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(((View) ConfirmContactFragment.this.getView().getParent()).getId(), catalogListFragment);
                    getFragmentManager().popBackStack();
                    fragmentTransaction.commit();

                    Toast.makeText(getContext(), "Заказ оформлен!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.action_search).setVisible(false);
        menu.findItem(R.id.action_filter).setVisible(false);
        menu.findItem(R.id.action_basket).setVisible(false);
    }
}
