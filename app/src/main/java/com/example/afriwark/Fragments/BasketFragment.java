package com.example.afriwark.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.afriwark.Activities.PaymentActivity;
import com.example.afriwark.Models.Cart.AddToCart;
import com.example.afriwark.R;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BasketFragment extends Fragment {

    private Toolbar toolbar;
    private EditText editTextqty, editTextprice, editTexttotal;
    private Button btnaddtobasket;
    private ApiInterface apiInterface;
    private int loginid;
    int total;

    public BasketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        editTextqty = view.findViewById(R.id.qty);
        editTextprice = view.findViewById(R.id.price);
        editTexttotal = view.findViewById(R.id.total);
        btnaddtobasket = view.findViewById(R.id.btnbasket);
        Bundle bundle = getArguments();
        toolbar = view.findViewById(R.id.toolbarbasket);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //noinspection ConstantConditions
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (bundle != null) {
            loginid = bundle.getInt("loginid");
            Log.i("loginid", String.valueOf(bundle.getInt("loginid")));
            btnaddtobasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValidate())
                        addToBasket(loginid);
                }
            });
        }
        return view;
    }

    private void addToBasket(int id) {
        int price = Integer.parseInt(editTextprice.getText().toString());
        int quantity = Integer.parseInt(editTextqty.getText().toString());
        int total = Integer.parseInt(editTexttotal.getText().toString());

        Call<AddToCart> cartCall = apiInterface.addToCart(quantity, total, price, String.valueOf(id));
        cartCall.enqueue(new Callback<AddToCart>() {
            @Override
            public void onResponse(Call<AddToCart> call, Response<AddToCart> response) {
                if (response.body().getCode() == 0) {
                    Toast.makeText(getActivity(), "Cart added successfull", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), PaymentActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<AddToCart> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidate() {
        String quantity = editTextqty.getText().toString();
        String total = editTexttotal.getText().toString();
        String price = editTextprice.getText().toString();
        if (quantity.equals("")) {
            editTextqty.setError("Please Enter Quantity");
            editTextqty.requestFocus();
            return false;
        }
        if (total.equals("")) {
            editTexttotal.setError("Please Enter Total");
            editTexttotal.requestFocus();
            return false;
        }
        if (price.equals("")) {
            editTextprice.setError("Please Enter Price");
            editTextprice.requestFocus();
            return false;
        }
        return true;
    }
}
