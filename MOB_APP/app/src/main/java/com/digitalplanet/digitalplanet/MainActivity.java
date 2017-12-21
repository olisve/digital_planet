package com.digitalplanet.digitalplanet;

import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import com.digitalplanet.digitalplanet.dal.User;
import com.digitalplanet.digitalplanet.dal.UserDbLoader;
import com.digitalplanet.digitalplanet.dal.UserLoader;
import com.digitalplanet.digitalplanet.fragment.*;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    public MenuItem searchItem;
    public MenuItem filterItem;
    public MenuItem basketItem;
    private MenuItem menuItem;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        CatalogFragment catalogFragment = new CatalogFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, catalogFragment);
        fragmentTransaction.commit();
        View headerview = navigationView.getHeaderView(0);
        ImageButton btn_header = (ImageButton) headerview.findViewById(R.id.btn_login_action);
        final TextView txt_header = (TextView) headerview.findViewById(R.id.text_login_action);
        UserDbLoader loader = new UserDbLoader(getApplicationContext());
        User user = loader.getUser();
        if (user == null) {
            txt_header.setText(getString(R.string.log_in));
        } else {
            txt_header.setText(user.getFirstName() + " " + user.getLastName());
        }

        btn_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDbLoader loader = new UserDbLoader(getApplicationContext());
                User user = loader.getUser();
                if (user == null) {
                    Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                    startActivity(intent);
                } else {
                    loader.removeUser();
                    txt_header.setText(getString(R.string.log_in));
                }
            }
        });

        txt_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDbLoader loader = new UserDbLoader(getApplicationContext());
                User user = loader.getUser();
                if (user == null) {
                    Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                    startActivity(intent);
                } else {
                    loader.removeUser();
                    txt_header.setText(getString(R.string.log_in));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setDrawer(boolean enable) {
        //drawerToggle.setDrawerIndicatorEnabled(enable);
        if (!enable) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            //show hamburger
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            drawerToggle.syncState();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        searchItem = menu.findItem(R.id.action_search);
        filterItem = menu.findItem(R.id.action_filter);
        basketItem = menu.findItem(R.id.action_basket);
        basketItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                BasketFragment basketFragment = new BasketFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, basketFragment);
                fragmentTransaction.addToBackStack(String.valueOf(R.id.nav_basket));
                fragmentTransaction.commit();
                return false;
            }
        });
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Search_Word", query); // Name
        searchFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, searchFragment);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
        //Toast.makeText(this, "Поиск по: " + query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        // User changed the text
        return false;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        int index = this.getFragmentManager().getBackStackEntryCount() - 1;
        android.support.v4.app.FragmentTransaction fragmentTransaction = null;
        if (id == R.id.nav_basket) {
            BasketFragment basketFragment = new BasketFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, basketFragment);
        } else if (id == R.id.nav_catalog) {
            CatalogFragment catalogFragment = new CatalogFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, catalogFragment);

        } else if (id == R.id.nav_delivery) {
            DeliveryFragment deliveryFragment = new DeliveryFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, deliveryFragment);

        } else if (id == R.id.nav_discount) {
            DiscountFragment discountFragment = new DiscountFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, discountFragment);
        } else if (id == R.id.nav_contacts) {
            ContactFragment contactFragment = new ContactFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, contactFragment);
        }
        if (fragmentTransaction != null) {
            if (index >= 0) {
                FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(index);
                String tag = backEntry.getName();
                if (tag != String.valueOf(id)) {
                    fragmentTransaction.addToBackStack(String.valueOf(id));
                }
            }
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
