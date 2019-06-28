package com.example.afriwark.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.afriwark.Interface.ClickListener;
import com.example.afriwark.Models.SearchClasses.Datum;
import com.example.afriwark.R;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Datum> dataList = new ArrayList<>();
    private Context context;
    private ClickListener clickListener;

    public ClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public RestaurantAdapter(List<Datum> dataList, Context context) {
        this.context = context;
        this.dataList = dataList;

    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.restaurant_list, viewGroup, false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, final int i) {

        final Datum data = dataList.get(i);
        restaurantViewHolder.resname.setText(data.getResturantName());
        restaurantViewHolder.address.setText(data.getCountry() + "," + data.getCity());
        restaurantViewHolder.resdistance.setText(data.getDistance());
        restaurantViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(data);
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();

    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private TextView resname;
        private TextView address;
        private TextView resdistance;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            resname = itemView.findViewById(R.id.resname);
            address = itemView.findViewById(R.id.address);
            resdistance = itemView.findViewById(R.id.resdistance);
        }
    }
}
