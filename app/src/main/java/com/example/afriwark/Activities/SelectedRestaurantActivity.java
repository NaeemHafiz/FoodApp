package com.example.afriwark.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.afriwark.Fragments.BasketFragment;
import com.example.afriwark.Fragments.OrderFragment;
import com.example.afriwark.Fragments.RestaurantFragment;
import com.example.afriwark.Models.SearchClasses.Datum;
import com.example.afriwark.R;
import com.example.afriwark.UtilityClasses.DBManager;

import java.util.ArrayList;
import java.util.List;

public class SelectedRestaurantActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private List<Datum> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_restaurant);
        actionBar = getSupportActionBar();
        if (getIntent().getExtras() != null) {
            data = (ArrayList<Datum>) getIntent().getSerializableExtra("resdata");
            setFragment(data);
        }
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_restaurant:
                        setFragment(data);
                        Toast.makeText(SelectedRestaurantActivity.this, "Restaurants", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.basket:
                        fragment = new BasketFragment();
                        loadFragment(fragment);
                        Toast.makeText(SelectedRestaurantActivity.this, "Baskets", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_logout:
                        logout();
                        Toast.makeText(SelectedRestaurantActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_order:
                        fragment = new OrderFragment();
                        loadFragment(fragment);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    private void logout() {
        DBManager.removeAllPreferencesData(SelectedRestaurantActivity.this);
        startActivity(new Intent(SelectedRestaurantActivity.this, SigninActivity.class));
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setFragment(List<Datum> data2) {
        RestaurantFragment frag = new RestaurantFragment();
        Bundle b = new Bundle();
// put stuff into bundle...
        b.putSerializable("valuesArray", (ArrayList) data2);

// Pass the bundle to the Fragment
        frag.setArguments(b);

// Use Fragment Transaction
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, frag, "tag");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
