package com.example.afriwark.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afriwark.Models.SubCategory.SubCategory;
import com.example.afriwark.R;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubFragment extends Fragment {

    private ApiInterface apiInterface;

    public SubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Bundle bundle = getArguments();
        if (bundle != null) {
            int category_id = bundle.getInt("category_id");
            Log.d("category_id", String.valueOf(bundle.getInt("category_id")));
            showSubCategory(category_id);
        }
        return view;
    }

    public void showSubCategory(int id) {
        Call<SubCategory> subCategoryCall = apiInterface.showSubCategory(id);
        subCategoryCall.enqueue(new Callback<SubCategory>() {
            @Override
            public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {

            }

            @Override
            public void onFailure(Call<SubCategory> call, Throwable t) {

            }
        });
    }

}
