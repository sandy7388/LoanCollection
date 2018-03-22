package com.example.sandy.loancollection.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.model.CustomerReport;

import java.util.ArrayList;

/**
 * Created by sandy on 2/1/18.
 */

public class CustomerReportAdapter extends RecyclerView.Adapter<CustomerReportAdapter.RecyclerViewHolder>
{

    private ArrayList<CustomerReport> collectionList;

    public CustomerReportAdapter(ArrayList<CustomerReport> collectionList)
    {
        this.collectionList = collectionList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerViewHolder recyclerViewHolder = null;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_customer_report, parent, false);
        recyclerViewHolder = new RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomerReportAdapter.RecyclerViewHolder holder, int position)
    {
        try
        {
            CustomerReport collection=collectionList.get(position);
            holder.tx_date.setText(collection.getDateTime());
            holder.tx_collected_amount.setText(collection.getFinal_am());
            holder.tx_remaining_amount.setText(collection.getRemainingAmount());

          /*  holder.tx_date.setText(date[position]);
            holder.tx_collected_amount.setText(collected_amount[position]);
            holder.tx_remaining_amount.setText(remaining_amount[position]);*/
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
        e.printStackTrace();
        }
    }

    @Override
    public int getItemCount()
    {

            return collectionList.size();

        //return (collectionList == null) ? 0 : collectionList.size();

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {

        public TextView tx_date, tx_collected_amount, tx_remaining_amount;

        public RecyclerViewHolder(View itemView)
        {
            super(itemView);

            try
            {
                tx_date = itemView.findViewById(R.id.textView_date);
                tx_collected_amount = itemView.findViewById(R.id.textView_collected_amount);
                tx_remaining_amount = itemView.findViewById(R.id.textView_remaining_amount);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                e.printStackTrace();
            }
        }
    }
}
