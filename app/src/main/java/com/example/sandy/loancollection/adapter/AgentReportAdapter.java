package com.example.sandy.loancollection.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.model.AgentReport;
import com.example.sandy.loancollection.model.CustomerReport;

import java.util.ArrayList;

/**
 * Created by sandy on 5/1/18.
 */

public class AgentReportAdapter extends RecyclerView.Adapter<AgentReportAdapter.RecyclerViewHolder>
{
    private ArrayList<AgentReport> agentReportArrayList;

    public AgentReportAdapter(ArrayList<AgentReport> agentReportArrayList)
    {
        this.agentReportArrayList = agentReportArrayList;
    }

    @Override
    public AgentReportAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        RecyclerViewHolder recyclerViewHolder = null;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_agent_report, parent, false);
        recyclerViewHolder = new AgentReportAdapter.RecyclerViewHolder(view);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(AgentReportAdapter.RecyclerViewHolder holder, int position)
    {
        try
        {
            AgentReport collection=agentReportArrayList.get(position);
            holder.tx_cust_name.setText(collection.getCust_name());
            holder.tx_cust_amount.setText(collection.getCust_amount());
            holder.tx_collection_time.setText(collection.getCollection_time());

        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return agentReportArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tx_cust_name, tx_cust_amount, tx_collection_time;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tx_cust_name = itemView.findViewById(R.id.textView_cust_name_report);
            tx_cust_amount = itemView.findViewById(R.id.textView_amount_report);
            tx_collection_time = itemView.findViewById(R.id.textView_time_report);
        }
    }
}
