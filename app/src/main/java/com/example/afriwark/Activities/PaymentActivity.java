package com.example.afriwark.Activities;

import android.app.Dialog;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
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

    private Button logout;
    private Toolbar toolbar;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
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

    }

    public void showDialog() {
        final Dialog dialog = new Dialog(PaymentActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.alertdialoge);
        dialog.setCancelable(true);
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

    public void onRadioButtonClicked(View view) {
        // Is the view now checked?
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.cashpayment:
                if (checked) {
                    getPayment();
                    showDialog();
                    Toast.makeText(PaymentActivity.this, "Cash Payment", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.onlinepayment:
                if (checked)
                    Toast.makeText(PaymentActivity.this, "online Payment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.paypalpayment:
                if (checked)
                    Toast.makeText(PaymentActivity.this, "paypal Payment", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
