package com.example.christinebpolesti.busarteryadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.christinebpolesti.busarteryadmin.Interface.ItemClickListener;
import com.example.christinebpolesti.busarteryadmin.Pojo.Driver;
import com.example.christinebpolesti.busarteryadmin.R;

import java.util.List;

/**
 * Created by christine B. Polesti on 2/16/2017.
 */

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {

    private List<Driver> driverlist;
    private Context context;
    private ItemClickListener clickListener;

    public DriverAdapter(List<Driver> driverlist) {
        this.driverlist = driverlist;
    }

    @Override
    public DriverAdapter.DriverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout, initialize the View Holder
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new DriverAdapter.DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DriverAdapter.DriverViewHolder holder, int position) {
        Driver driver = driverlist.get(position);

        holder.edtDriversName.setText(driver.getDriversName());
        holder.edtbusnum.setText(driver.getBusNum());
    }

    @Override
    public int getItemCount() {
        return driverlist.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class DriverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView edtDriversName;
        TextView edtbusnum;

        public DriverViewHolder(View itemView) {
            super(itemView);
            edtDriversName = (TextView) itemView.findViewById(R.id.edtItem1);
            edtbusnum = (TextView) itemView.findViewById(R.id.edtItem2);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClick(v, getAdapterPosition());
            }
        }
    }
}
