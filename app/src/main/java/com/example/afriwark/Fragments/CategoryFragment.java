package com.example.afriwark.Fragments;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afriwark.Models.CategoryClasses.Category;
import com.example.afriwark.R;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {
    private Toolbar toolbar;
    private TextView textView_restaurant_name, textView_restaurant_address, textView_category_name;
    private String name, country, city;
    private ApiInterface apiInterface;
    private int restaurant_id;
    private LinearLayout linearlayout;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_category, container, false);
        textView_restaurant_name = view.findViewById(R.id.res_name);
        textView_restaurant_address = view.findViewById(R.id.res_address);
        textView_category_name = view.findViewById(R.id.cat_name);
        toolbar = view.findViewById(R.id.categorytoolbar);
        linearlayout = view.findViewById(R.id.textviewlistener);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //noinspection ConstantConditions
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bundle b = getArguments();
        if (b != null) {
            name = b.getString("restaurant_name");
            city = b.getString("address_city");
            country = b.getString("address_country");
            textView_restaurant_name.setText(name);
            textView_restaurant_address.setText(city + "," + country);
            restaurant_id = b.getInt("restaurant_id");
            Log.i("java", restaurant_id + "");
            setData(restaurant_id);
        }
        return view;
    }

    private void setData(int id) {

        Call<Category> categoryCall = apiInterface.showCategoryData(id);
        categoryCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, final Response<Category> response) {
                if (response.body().getCode() == 0) {
                    textView_category_name.setText(response.body().getData().getCategoryInfo().get1().getCategoryName());
                    linearlayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Bundle args = new Bundle();
                            args.putString("res_name", response.body().getData().getResturantName());
                            args.putString("res_country", response.body().getData().getCountry());
                            args.putString("res_city", response.body().getData().getCity());
                            args.putString("category_name", response.body().getData().getCategoryInfo().get1().getCategoryName());
                            int id = Integer.parseInt(response.body().getData().getCategoryInfo().get1().getCategoryId());
                            args.putInt("cat_id", id);
                            CategoryDetailFragment fragment = new CategoryDetailFragment();
                            fragment.setArguments(args);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_container, fragment);
                            transaction.commit();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }

    private void changeFragment() {
        CategoryDetailFragment fragment = new CategoryDetailFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
