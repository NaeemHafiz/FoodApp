package com.example.afriwark.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.afriwark.Adapters.RestaurantAdapter;
import com.example.afriwark.Interface.ClickListener;
import com.example.afriwark.Models.SearchClasses.Datum;
import com.example.afriwark.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment implements ClickListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;
    private List<Datum> dataList = new ArrayList<>();


    public RestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        setHasOptionsMenu(true);
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle b = getArguments();
        if (b != null)
            dataList = (ArrayList<Datum>) getArguments().getSerializable("valuesArray");
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        toolbar = view.findViewById(R.id.toolbarrestaurant);
        recyclerView = view.findViewById(R.id.restaurantrecyclerview);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //noinspection ConstantConditions
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        restaurantAdapter = new RestaurantAdapter(dataList, getActivity());
        restaurantAdapter.setClickListener(this);
        recyclerView.setAdapter(restaurantAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClick(Datum data) {
        Bundle args = new Bundle();
        args.putString("restaurant_name", data.getResturantName());
        args.putString("address_country", data.getResturantName());
        args.putString("address_city", data.getCity());
        int id = Integer.parseInt(data.getResturantId());
        args.putInt("restaurant_id", id);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}
