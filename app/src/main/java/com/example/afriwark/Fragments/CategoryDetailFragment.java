package com.example.afriwark.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.afriwark.R;
import com.example.afriwark.UtilityClasses.DBManager;

import static com.example.afriwark.UtilityClasses.Tags.USER_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryDetailFragment extends Fragment {

    private int cat_id,loginid;
    private String res_name, res_city, res_country, cate_name;
    private TextView textViewrestaurant_name, textview_city, country_name, cat_name;
    private Button btnmoveToNextFragment;

    public CategoryDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_detail, container, false);
        textViewrestaurant_name = view.findViewById(R.id.restaurant_name);
        textview_city = view.findViewById(R.id.textview_city);
        country_name = view.findViewById(R.id.country_name);
        cat_name = view.findViewById(R.id.cat_name);
        btnmoveToNextFragment = view.findViewById(R.id.moveToNextFragment);
        Bundle bundle = getArguments();
        if (bundle != null) {
            cat_id = bundle.getInt("cat_id");
            res_name = bundle.getString("res_name");
            res_city = bundle.getString("res_city");
            res_country = bundle.getString("res_country");
            cate_name = bundle.getString("category_name");
            textViewrestaurant_name.setText(res_name);
            textview_city.setText(res_city);
            country_name.setText(res_country);
            cat_name.setText(cate_name);
//            Log.d("cat_id", id + "");
            //
            btnmoveToNextFragment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle args = new Bundle();
                    int loginid = DBManager.getIntPrefs(getActivity(), USER_ID);
                    args.putInt("loginid", loginid);
                    args.putInt("category_id", cat_id);
                    BasketFragment fragment = new BasketFragment();
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, fragment);
                    transaction.commit();
                }
            });
        }
        return view;
    }
}
