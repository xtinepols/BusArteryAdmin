package com.example.christinebpolesti.busarteryadmin.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.christinebpolesti.busarteryadmin.Interface.ItemClickListener;
import com.example.christinebpolesti.busarteryadmin.Pojo.History;
import com.example.christinebpolesti.busarteryadmin.R;

import java.util.List;

/**
 * Created by christine B. Polesti on 3/2/2017.
 */

public class BusNumHistoryAdapter extends RecyclerView.Adapter<BusNumHistoryAdapter.BusNumHistoryViewHolder>{

    private List<History> historyList;
    private ItemClickListener clickListener;

    public BusNumHistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public BusNumHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout, initialize the View Holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new BusNumHistoryAdapter.BusNumHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BusNumHistoryViewHolder holder, int position) {
        History history = historyList.get(position);

        holder.date.setText(history.getDate());
        holder.time.setText(history.getTimeSarted() + " - " + history.getTimeEnded());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class BusNumHistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView date;
        TextView time;

        public BusNumHistoryViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.edtItem1);
            time = (TextView) itemView.findViewById(R.id.edtItem2);
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