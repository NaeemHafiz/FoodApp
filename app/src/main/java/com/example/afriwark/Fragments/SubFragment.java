package com.example.afriwark.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afriwark.Models.SubCategory.SubCategory;
import com.example.afriwark.R;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;
import com.google.android.gms.common.data.DataBufferUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubFragment extends Fragment {

    private ApiInterface apiInterface;
    private TextView restaurant_name, subcatname;
    private Button moveToNextFragment;
    int category_id;

    public SubFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        restaurant_name = view.findViewById(R.id.restaurant_name);
        subcatname = view.findViewById(R.id.subcatname);
        moveToNextFragment = view.findViewById(R.id.moveToNextFragment);
        Bundle bundle = getArguments();
        if (bundle != null) {
            category_id = bundle.getInt("category_id");
            Log.d("category_id", String.valueOf(bundle.getInt("category_id")));
            showSubCategory(category_id);
        }
        moveToNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PriceFragment fragment = new PriceFragment();
                Bundle b = new Bundle();
                b.putInt("catid", category_id);
                fragment.setArguments(b);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public void showSubCategory(int id) {
        Call<SubCategory> subCategoryCall = apiInterface.showSubCategory(id);
        subCategoryCall.enqueue(new Callback<SubCategory>() {
            @Override
            public void onResponse(Call<SubCategory> call, Response<SubCategory> response) {

                if (response.body().getCode() == 0) {
                    restaurant_name.setText(response.body().getData().getResturantName());
                    subcatname.setText(response.body().getData().getCategoryInfo().get1().getSubCategoriesName());
                }
            }

            @Override
            public void onFailure(Call<SubCategory> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
