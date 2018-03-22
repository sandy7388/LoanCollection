package com.example.sandy.loancollection.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.example.sandy.loancollection.fragments.SearchCustomerFragment;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.printClasses.MP100MainFragment;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.volley.VolleySingleton;
import com.ngx.mp100sdk.NGXPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView_customer_name,textView_customer_account_number,
            textView_customer_address,textView_total_amount,
            textView_customer_loan_number;
    EditText editText_amount;
    Button button_add_money;
    String strDate,name,account,address,installament,loan,loan_number,remaining_amount;
    public NGXPrinter ngxPrinter = MainActivity.ngxPrinter;
    private ProgressDialog progressDialog;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        initialization();
    }

    @SuppressLint("SimpleDateFormat")
    public void initialization()
    {
        editText_amount = findViewById(R.id.edt_amount);
        textView_customer_name = findViewById(R.id.textView_customer_name);
        textView_customer_account_number = findViewById(R.id.textView_customer_number);
        textView_customer_address = findViewById(R.id.textView_customer_address);
        textView_total_amount = findViewById(R.id.textView_loan_amount);
        textView_customer_loan_number = findViewById(R.id.textView_loan_number);
        button_add_money = findViewById(R.id.button_add_money);

        button_add_money.setOnClickListener(this);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strDate = simpleDateFormat.format(date);
        session=new SessionManager(this);

        if(!SessionManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Collection");

        try {
            Intent intent = this.getIntent();
            name = intent.getExtras().getString("NAME_KEY");
            loan_number = intent.getExtras().getString("LOAN_ID_KEY");
            account = intent.getExtras().getString("ACCOUNT_KEY");
            address = intent.getExtras().getString("ADDRESS_KEY");
            installament = intent.getExtras().getString("INSTALLAMENT_KEY");
            loan = intent.getExtras().getString("LOAN_KEY");
            remaining_amount = intent.getExtras().getString("REMAINING_KEY");

            textView_customer_name.setText(name);
            textView_customer_account_number.setText(account);
            textView_customer_address.setText(address);
            textView_total_amount.setText(loan);
            textView_customer_loan_number.setText(loan_number);
            editText_amount.setText(installament);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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

    public void initAdapter()
    {
        installament = editText_amount.getText().toString();
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
                        Toast.makeText(CollectionActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(CollectionActivity.this,SearchCustomerFragment.class);
                        startActivity(intent);
                        /*startActivity(new Intent(CollectionActivity.this,SearchCustomerFragment.class));
                        finish();*/
                    }
                    if(jsonObject.getString("success").equals("1"));
                    {
                        progressDialog.dismiss();

                        try {
                            progressDialog.dismiss();

                            if(ngxPrinter!=null) {
                                MP100MainFragment mp = new MP100MainFragment(getApplicationContext());
                                mp.printEnglishBill1(account, name, address, installament,strDate);
                                progressDialog.dismiss();

                            }
                            Toast.makeText(CollectionActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(CollectionActivity.this,MainActivity.class));
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                progressDialog.dismiss();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("dateTime",strDate);
                params.put("c_id",account);
                params.put("final_am",installament);
                params.put("agent_id",session.getUserID());
                params.put("loan_id",loan_number);
                params.put("remaining_amount",remaining_amount);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
