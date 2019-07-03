package com.example.afriwark.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.afriwark.Models.UserSignInSignUp.Register.SignupUser;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;
import com.example.afriwark.UtilityClasses.DBManager;
import com.example.afriwark.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.afriwark.UtilityClasses.Tags.USER_ID;

public class SignupActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText edittextname, edittextemail, edittextpassword, edittextcpassword;
    private Button btnsignup;
    private ApiInterface apiInterface;
    private TextView textviewclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initViews();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        edittextname = findViewById(R.id.edittextname);
        edittextemail = findViewById(R.id.edittextemail);
        edittextpassword = findViewById(R.id.edittextpassword);
        edittextcpassword = findViewById(R.id.edittextcpassword);
        btnsignup = findViewById(R.id.btnsignup);
        textviewclick = findViewById(R.id.textviewclick);
        clickListeners();

    }

    private void clickListeners() {
        textviewclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, SigninActivity.class));
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate())
                    signup();
            }
        });
    }

    private boolean isValidate() {
        String name = edittextname.getText().toString();
        String email = edittextemail.getText().toString();
        String password = edittextpassword.getText().toString();
        String cpassword = edittextcpassword.getText().toString();
        if (name.equals("")) {
            edittextname.setError("Please Enter Name");
            edittextname.requestFocus();
            return false;
        }
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
        if (!isEmailValidate(email)) {
            edittextemail.setError("Please Enter Valid Email");
            edittextemail.requestFocus();
            return false;
        }
        if (cpassword.equals("")) {
            edittextcpassword.setError("Please Enter Confirm Password");
            edittextpassword.requestFocus();
        }
        if (!password.equals(cpassword)) {
            Toast.makeText(SignupActivity.this, "Passwords Does Not Match", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * validate your email address format. Ex-akhi@mani.com
     */
    public static boolean isEmailValidate(String email) {
        if (email != null) {
            Pattern pattern;
            Matcher matcher;
            final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
            return matcher.matches();
        } else {
            return false;
        }
    }

    public void signup() {
        String name = edittextname.getText().toString();
        final String email = edittextemail.getText().toString();
        String password = edittextpassword.getText().toString();
        String cpassword = edittextcpassword.getText().toString();
        //
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<SignupUser> call = apiInterface.signUp(name, email, password, cpassword);
        call.enqueue(new Callback<SignupUser>() {
            @Override
            public void onResponse(Call<SignupUser> call, Response<SignupUser> response) {
                if (response.body().getCode() == 0) {
                    Toast.makeText(SignupActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    int id = Integer.valueOf(response.body().getData().getUserId());
                    DBManager.setIntPrefs(SignupActivity.this, USER_ID, id);
                    startActivity(new Intent(SignupActivity.this, FindRestaurantActivity.class));
                    SignupActivity.this.finish();
                } else if (response.body().getCode() == -1) {
                    Toast.makeText(SignupActivity.this, "UserRegister all ready Exist!Please login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "There is something wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignupUser> call, Throwable t) {
                Toast.makeText(SignupActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
