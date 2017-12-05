package com.example.christinebpolesti.busarteryadmin.Adapter;

import android.content.Context;
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
 * Created by christine B. Polesti on 9/19/2016.
 */

public class AllHistoryAdapter extends RecyclerView.Adapter<AllHistoryAdapter.HistoryViewHolder> {

    private List<History> historyList;
    private Context context;
    private ItemClickListener clickListener;

    public AllHistoryAdapter(List<History> historyList) {
        this.historyList = historyList;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout, initialize the View Holder
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new AllHistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllHistoryAdapter.HistoryViewHolder holder, int position) {
        History history = historyList.get(position);

        holder.busnum.setText(history.getBusNumber());
        holder.time.setText(history.getTimeSarted() + " - " + history.getTimeEnded());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView busnum;
        TextView time;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            busnum = (TextView) itemView.findViewById(R.id.edtItem1);
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
