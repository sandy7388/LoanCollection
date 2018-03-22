package com.example.sandy.loancollection.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.activity.CollectionActivity;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.model.CustomerReport;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CollectionFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener

{
    TextView textView_customer_name,textView_customer_account_number;
    EditText editText_amount;
    Button button_add_money;
    Spinner spinner;
    private String strDate,strAmount,spinner_reasonStr;

    // Reason spinner
    ArrayList<String> reason = new ArrayList<>();

    SessionManager session;

    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_collection, container, false);
        editText_amount = view.findViewById(R.id.edt_amount);
        textView_customer_name = view.findViewById(R.id.textView_customer_name);
        textView_customer_account_number = view.findViewById(R.id.textView_customer_number);
        button_add_money = view.findViewById(R.id.button_add_money);
        spinner = view.findViewById(R.id.spinner_reason);

        button_add_money.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strDate = simpleDateFormat.format(date);

        //spinner.setEnabled(true);
        reasonSpinner(HttpURL.LOAN_REASON);

        session=new SessionManager(getActivity());
        return view;
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_add_money:
                initAdapter();
                break;
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void initAdapter()
    {
        strAmount = editText_amount.getText().toString();

        if (editText_amount.getText().toString().length()<=0)
        {
            spinner.setVisibility(View.VISIBLE);
        }
        else if (editText_amount.getText().toString().length()>0)
        {
            spinner.setVisibility(View.GONE);
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL.ADD_BALANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equals("0"))
                    {
                        Toast.makeText(getActivity(),"Sucess",Toast.LENGTH_SHORT).show();

                        //collectionList.add(new CustomerReport(strDate,strAmount,strRemainingAmount));
                        editText_amount.setText("");
                        //adapter.notifyDataSetChanged();

                    }
                    else if (jsonObject.getString("success").equals("1"));
                    {
                        Toast.makeText(getActivity(),"Fail",Toast.LENGTH_SHORT).show();

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("date",strDate);
                params.put("amount",strAmount);
                params.put("reason",spinner_reasonStr);
                params.put("user_id",session.getUserID());

                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    private void reasonSpinner(String reason_url)
    {

        StringRequest stringRequest=new StringRequest(reason_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String reason_name = jsonObject.getString("reason_name");

                        reason.add(reason_name);


                    }
                    spinner.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,reason));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();

            }
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }
}
