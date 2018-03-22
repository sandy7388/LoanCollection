package com.example.sandy.loancollection.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.fragments.SearchCustomerFragment;
import com.example.sandy.loancollection.httpHelper.HttpURL;
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

import static android.content.ContentValues.TAG;

public class ReasonActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener
{
    Button button_add_reason;
    Spinner spinner;
    TextView textView_customer_name,textView_customer_account_number,textView_customer_loan_number;
    ArrayList<String> reason = new ArrayList<>();
    private String strDate,name,account,spinner_reasonStr,loan_number;
    private ProgressDialog progressDialog;
    SessionManager session;
    @Override

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reason);
        initialization();

    }

    @SuppressLint("SimpleDateFormat")
    void initialization()
    {
        button_add_reason = findViewById(R.id.button_submit_reason);
        spinner = findViewById(R.id.spinner_reason);
        textView_customer_name = findViewById(R.id.textView_customer_name_reason);
        textView_customer_account_number = findViewById(R.id.textView_customer_number_reason);
        textView_customer_loan_number = findViewById(R.id.textView_loan_number_reason);

        Intent intent=this.getIntent();

        loan_number=intent.getExtras().getString("LOAN_ID_KEY");
        name=intent.getExtras().getString("NAME_KEY");
        account=intent.getExtras().getString("ACCOUNT_KEY");

        textView_customer_name.setText(name);
        textView_customer_account_number.setText(account);
        textView_customer_loan_number.setText(loan_number);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strDate = simpleDateFormat.format(date);
        session=new SessionManager(this);
        button_add_reason.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        reasonSpinner(HttpURL.LOAN_REASON);
        setTitle("Reason");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_submit_reason:
                SubmitReason();
                break;
        }

    }

    private void SubmitReason()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL.ADD_BALANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equals("0"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ReasonActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ReasonActivity.this,MainActivity.class));


                    }
                    if (jsonObject.getString("success").equals("1"));
                    {
                        progressDialog.dismiss();
                        Toast.makeText(ReasonActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ReasonActivity.this,MainActivity.class));


                    }



                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                progressDialog.dismiss();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                HashMap<String, String> params = new HashMap<>();
                //params.put("name",name);
                params.put("c_id",account);
                params.put("id",session.getUserID());
                params.put("reason_name",spinner_reasonStr);
                params.put("dateTime",strDate);
                params.put("loan_id",loan_number);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void reasonSpinner(String reason_url)
    {

        StringRequest stringRequest=new StringRequest(reason_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject1 = new JSONObject(response);
                    JSONArray jsonArray = jsonObject1.getJSONArray("reason");
                    //JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String reason_name = jsonObject.getString("reason_name");

                        reason.add(reason_name);


                    }
                    spinner.setAdapter(new ArrayAdapter<String>(ReasonActivity.this,android.R.layout.simple_spinner_dropdown_item,reason));

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();

            }
        });
        VolleySingleton.getInstance(ReasonActivity.this).addToRequestQueue(stringRequest);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        Spinner spinner = (Spinner) parent;

        switch (spinner.getId())
        {
            case R.id.spinner_reason:

                spinner_reasonStr = spinner.getSelectedItem().toString();

                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }



}
