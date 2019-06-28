package com.example.afriwark.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.afriwark.Adapters.RestaurantAdapter;
import com.example.afriwark.Interface.ClickListener;
import com.example.afriwark.Models.SearchClasses.Datum;
import com.example.afriwark.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantFragment extends Fragment implements ClickListener {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RestaurantAdapter restaurantAdapter;
    private List<Datum> dataList = new ArrayList<>();
    private LinearLayout linearLayout;
    private TextView textView, textview_count_res;


    public RestaurantFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        setHasOptionsMenu(true);
//        inflater.inflate(R.menu.search_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        Bundle b = getArguments();
        if (b != null)
            dataList = (ArrayList<Datum>) getArguments().getSerializable("valuesArray");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = view.findViewById(R.id.toolbarrestaurant);
        recyclerView = view.findViewById(R.id.restaurantrecyclerview);
        linearLayout = view.findViewById(R.id.setData);
        textView = view.findViewById(R.id.emptyview);
        textview_count_res = view.findViewById(R.id.textview_count_res);
        if (dataList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            if (dataList.size() == 1)
                textview_count_res.setText(dataList.size() + " " + "Restaurant Deliver To you");
            else
                textview_count_res.setText(dataList.size() + " " + "Restaurants Deliver To you");
        }
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
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
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
