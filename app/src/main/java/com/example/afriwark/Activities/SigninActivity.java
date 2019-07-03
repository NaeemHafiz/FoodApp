package com.example.afriwark.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afriwark.Models.UserSignInSignUp.Login.SigninUser;
import com.example.afriwark.R;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;
import com.example.afriwark.UtilityClasses.DBManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.afriwark.UtilityClasses.Tags.USER_ID;

public class SigninActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView createclickactivity;
    private EditText edittextemail, edittextpassword;
    private Button btnlogin;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        toolbar = findViewById(R.id.logintoolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        createclickactivity = findViewById(R.id.createclickactivity);
        edittextemail = findViewById(R.id.edittextemail);
        edittextpassword = findViewById(R.id.edittextpassword);
        btnlogin = findViewById(R.id.btnlogin);
        clickListeners();
    }

    private void clickListeners() {
        createclickactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate())
                    login();

            }
        });
    }

    private void login() {
        final String email = edittextemail.getText().toString();
        String password = edittextpassword.getText().toString();
        Call<SigninUser> call = apiInterface.signIn(email, password);
        call.enqueue(new Callback<SigninUser>() {
            @Override
            public void onResponse(Call<SigninUser> call, Response<SigninUser> response) {
                if (response.body().getCode() == 0) {
                    int id = Integer.parseInt(response.body().getData().getUserId());
                    DBManager.setIntPrefs(getApplicationContext(), USER_ID, id);
                    Toast.makeText(SigninActivity.this, "you are login now!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SigninActivity.this, FindRestaurantActivity.class));
                    SigninActivity.this.finish();
                } else if (response.body().getCode() == -1) {
                    Toast.makeText(SigninActivity.this, "user name or password wrong", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SigninActivity.this, "there is something wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SigninUser> call, Throwable t) {
                Toast.makeText(SigninActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean isValidate() {
        String email = edittextemail.getText().toString();
        String password = edittextpassword.getText().toString();
        if (email.equals("")) {
            edittextemail.setError("Please Enter Email");
            edittextemail.requestFocus();
            return false;
        }
        if (password.equals("")) {
            edittextpassword.setError("Please Enter Password");
            edittextpassword.requestFocus();
            return false;
        }
        return true;
    }
}
