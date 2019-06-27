package com.example.afriwark.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afriwark.Models.Payment.Payment;
import com.example.afriwark.R;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;
import com.example.afriwark.UtilityClasses.DBManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.afriwark.UtilityClasses.Tags.USER_ID;

public class PaymentActivity extends AppCompatActivity {

    Button btnpayment, logout;
    Toolbar toolbar;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnpayment = findViewById(R.id.payment);
        toolbar = findViewById(R.id.toolbarfinal);
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBManager.getIntPrefs(PaymentActivity.this, USER_ID);
                startActivity(new Intent(PaymentActivity.this, SignupActivity.class));
            }
        });
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        btnpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayment();
                showDialog();
            }
        });
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alertdialoge);
        Button dialogButton = dialog.findViewById(R.id.btndialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void getPayment() {
        Call<Payment> paymentCall = apiInterface.getPayment();
        paymentCall.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.body().getCode() == 0) {
                    Toast.makeText(PaymentActivity.this, "Payment Added Successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

            }
        });
    }
}
