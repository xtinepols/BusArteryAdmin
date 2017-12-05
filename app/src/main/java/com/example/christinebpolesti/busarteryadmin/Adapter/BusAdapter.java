package com.example.christinebpolesti.busarteryadmin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.christinebpolesti.busarteryadmin.Interface.ItemClickListener;
import com.example.christinebpolesti.busarteryadmin.Pojo.Buses;
import com.example.christinebpolesti.busarteryadmin.R;

import java.util.List;

/**
 * Created by christine B. Polesti on 2/16/2017.
 */

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.BusViewHolder> {

    private List<Buses> busNumberList;
    private Context context;
    private ItemClickListener clickListener;

    public BusAdapter(List<Buses> busNumberList) {
        this.busNumberList = busNumberList;
    }

    @Override
    public BusAdapter.BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout, initialize the View Holder
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item1, parent, false);
        return new BusAdapter.BusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusAdapter.BusViewHolder holder, int position) {
        Buses busNumber = busNumberList.get(position);

        holder.edtbusnum.setText(busNumber.getBusNumber());
    }

    @Override
    public int getItemCount() {
        return busNumberList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class BusViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView edtbusnum;

        public BusViewHolder(View itemView) {
            super(itemView);
            edtbusnum = (TextView) itemView.findViewById(R.id.edtItem);
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