package com.example.sandy.loancollection.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.adapter.AgentReportAdapter;
import com.example.sandy.loancollection.adapter.CustomerReportAdapter;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.model.AgentReport;
import com.example.sandy.loancollection.model.CustomerReport;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AgentReportActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AgentReport> agentReportArrayList;
    private String strDate,strAmount,strDateTime,strName;
    private int date_Year,date_Month,date_Day;
    Calendar calendar;
    private TextView text_agent_report_date,text_agent_collection;
    private Button button_agent_report;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_report);
        controller();
        initiateRecyclerView();
    }
    @SuppressLint("SimpleDateFormat")
    public void controller()
    {
        text_agent_report_date = findViewById(R.id.editText_search_agent_report);
        text_agent_collection = findViewById(R.id.agent_total_collection);
        button_agent_report = findViewById(R.id.button_search_agent_report);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        calendar=Calendar.getInstance();
        date_Year=calendar.get(Calendar.YEAR);
        date_Month=calendar.get(Calendar.MONTH);
        date_Day=calendar.get(Calendar.DAY_OF_MONTH);

        //Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        text_agent_report_date.setText( sdf.format(calendar.getTime()));
        text_agent_report_date.setOnClickListener(this);
        button_agent_report.setOnClickListener(this);
        session = new SessionManager(this);
        //strDate = text_agent_report_date.getText().toString();
        //System.out.println(strDate);
    }

    public void initiateRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerView_agent);
        agentReportArrayList = new ArrayList<>();
        adapter = new AgentReportAdapter(agentReportArrayList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
    {
        month=month+1;
        text_agent_report_date.setText(dayOfMonth + "-" + month
                + "-" + year);
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.editText_search_agent_report:
                getDate();
                break;

            case R.id.button_search_agent_report:
                agentReport();
                break;
        }

    }

    private void getDate()
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,AgentReportActivity.this, date_Year, date_Month, date_Day);
        datePickerDialog.show();
    }

    private void agentReport()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        strDate = text_agent_report_date.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL.AGENT_REPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equals("0")) {
                        progressDialog.dismiss();
                        Toast.makeText(AgentReportActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        //adapter.notifyDataSetChanged();
                        initiateRecyclerView();
                    }
                    JSONArray jsonArray = jsonObject.getJSONArray("get_agent_report");

                    for (int i=0; i<jsonArray.length();i++)
                    {
                        JSONObject report = jsonArray.getJSONObject(i);

                        strAmount = report.getString("final_am");
                        strDateTime = report.getString("dateTime");
                        strName = report.getString("customer_name");

                        if (jsonObject.getString("success").equals("1"))
                        {
                            AgentReport agentReport = new AgentReport();

                            agentReport.setCust_amount(strAmount);
                            agentReport.setCollection_time(strDateTime);
                            agentReport.setCust_name(strName);
                            agentReportArrayList.add(agentReport);
                            try
                            {
                                int total = 0;
                                for (int j=0;j<agentReportArrayList.size();j++)
                                {
                                    total = total + Integer.parseInt(agentReportArrayList.get(j).getCust_amount());
                                    text_agent_collection.setText(total + "");
                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            progressDialog.dismiss();
                            Toast.makeText(AgentReportActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        }
                        else if (jsonObject.getString("success").equals("0"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(AgentReportActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("dateTime",strDate);
                params.put("agent_id",session.getUserID());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        initiateRecyclerView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}