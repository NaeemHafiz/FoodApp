package com.example.afriwark.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.afriwark.Models.SearchClasses.Datum;
import com.example.afriwark.Models.SearchClasses.SearchRestaurant;
import com.example.afriwark.R;
import com.example.afriwark.Remote.ApiClient;
import com.example.afriwark.Remote.ApiInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindRestaurantActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private Button btnfindrestaurant, btnsearch;
    private EditText editTextsearch;
    private String coordinates;
    private String[] separated;
    private ApiInterface apiInterface;
    private List<Datum> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_restaurant);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        initViews();
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("Selected Location");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(FindRestaurantActivity.this);
    }

    private void doSearch() {
        coordinates = editTextsearch.getText().toString();
        separated = coordinates.split(",");
        LatLng TutorialsPoint = new LatLng(Double.parseDouble(separated[0]), Double.parseDouble(separated[1]));
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title("Valencia Town"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbarsearch);
        editTextsearch = toolbar.findViewById(R.id.rdittextlocation);
        btnsearch = toolbar.findViewById(R.id.btnsearch);
        setSupportActionBar(toolbar);
        btnfindrestaurant = findViewById(R.id.findrestaurant);
        clickListeners();
    }

    private void clickListeners() {
        btnfindrestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate())
                    findRestaurant();
            }
        });
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidate())
                    doSearch();
            }
        });
    }

    private void findRestaurant() {
        coordinates = editTextsearch.getText().toString();
        separated = coordinates.split(",");
        double latitude = Double.parseDouble(separated[0]);
        double longitude = Double.parseDouble(separated[1]);
        Call<SearchRestaurant> call = apiInterface.searchRestaurant(latitude, longitude);
        call.enqueue(new Callback<SearchRestaurant>() {
            @Override
            public void onResponse(Call<SearchRestaurant> call, Response<SearchRestaurant> response) {

                data = response.body().getData();
                Log.d("data1", String.valueOf(data));
                Log.d("nimraname", data.get(0).getResturantName());
                Intent intent = new Intent(FindRestaurantActivity.this, SelectedRestaurantActivity.class);
                intent.putStringArrayListExtra("resdata", (ArrayList) data);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<SearchRestaurant> call, Throwable t) {
                Toast.makeText(FindRestaurantActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    private boolean isValidate() {
        String search = editTextsearch.getText().toString();
        if (search.isEmpty()) {
            editTextsearch.setError("Please Enter Required DataRegister");
            editTextsearch.requestFocus();
            return false;
        }
        return true;
    }
}
