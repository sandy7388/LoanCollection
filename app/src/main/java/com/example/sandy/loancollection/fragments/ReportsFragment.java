package com.example.sandy.loancollection.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.activity.AgentReportActivity;
import com.example.sandy.loancollection.activity.CustomerReportActivity;
import com.example.sandy.loancollection.activity.MainActivity;
import com.example.sandy.loancollection.activity.ReportActivity;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReportsFragment extends Fragment implements View.OnClickListener
{

    private Button button_agent_report,button_customer_report;
    public ReportsFragment()
    {

    }

    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        button_agent_report = view.findViewById(R.id.btn_agent_report);
        button_customer_report = view.findViewById(R.id.btn_customer_report);

        button_customer_report.setOnClickListener(this);
        button_agent_report.setOnClickListener(this);
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_agent_report:
                startActivity(new Intent(getActivity(),AgentReportActivity.class));
                break;

            case R.id.btn_customer_report:
                startActivity(new Intent(getActivity(),CustomerReportActivity.class));
                break;

        }
    }


}
