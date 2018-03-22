package com.example.sandy.loancollection.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.adapter.CustomerReportAdapter;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.model.CustomerReport;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.volley.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomerReportActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private CustomerReportAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<CustomerReport> collectionList;
    private String strDate,strAmount,strRemainingAmount,strTotalAmount;
    private TextView textView_total_amount;
    private String account_numberStr;
    private EditText editText_cust_acc_number;
    private Button button_search_report;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_report);
        controller();
        initiateRecyclerview();
//        InputMethodManager inputManager =
//                (InputMethodManager) this.
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (inputManager != null) {
//            inputManager.hideSoftInputFromWindow(
//                    this.getCurrentFocus().getWindowToken(),
//                    InputMethodManager.HIDE_NOT_ALWAYS);
//        }

        //hideSoftKeyboard(this);

        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        View focusedView = this.getCurrentFocus();

        if (focusedView != null) {
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    void controller()
    {
        textView_total_amount = findViewById(R.id.total_collection);
        editText_cust_acc_number = findViewById(R.id.editText_search_customer_report);
        button_search_report = findViewById(R.id.button_search_customer_report);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        session = new SessionManager(this);
        button_search_report.setOnClickListener(this);

    }
    public void initiateRecyclerview()
    {
        recyclerView = findViewById(R.id.recycler_View);
        collectionList = new ArrayList<>();
        adapter = new CustomerReportAdapter(collectionList);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_search_customer_report:
                initAdapter();
                break;
        }
    }

    void initAdapter()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        account_numberStr = editText_cust_acc_number.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL.CUSTOMER_REPORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("success").equals("0"))
                {
                    progressDialog.dismiss();
                    Toast.makeText(CustomerReportActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    //adapter.notifyDataSetChanged();
                    initiateRecyclerview();
                }

                    JSONArray jsonArray = jsonObject.getJSONArray("get_customer_report");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject report = jsonArray.getJSONObject(i);

                            strDate = report.getString("dateTime");
                            strAmount = report.getString("final_am");
                            strRemainingAmount = report.getString("amount");

                            if (jsonObject.getString("success").equals("1"))
                            {
                                CustomerReport customerReport = new CustomerReport();

                                customerReport.setDateTime(strDate);
                                customerReport.setFinal_am(strAmount);
                                customerReport.setAmount(strRemainingAmount);
                                collectionList.add(customerReport);

                                //adapter.notifyDataSetChanged();

                                    int totalCollection = 0;
                                    int remaining = Integer.parseInt(strRemainingAmount);
                                    //System.out.println(remaining+"");
                                    for (int j=0;j<collectionList.size();j++)
                                    {
                                        totalCollection = totalCollection + Integer.parseInt(collectionList.get(j).getFinal_am());

                                        textView_total_amount.setText(totalCollection + "");

                                    }
                                    int remain=0;
                                    int loanAmount=Integer.parseInt(collectionList.get(0).getAmount());
                                    for (CustomerReport item:collectionList)
                                    {
//                                        item.setRemainingAmount(totalCollection-
//                                                Integer.parseInt(item.getAmount())+"");
//                                        totalCollection=totalCollection-Integer.parseInt(item.getAmount());
                                        remain=remain+Integer.parseInt(item.getFinal_am());
                                        item.setRemainingAmount(Integer.parseInt(item.getAmount())-remain+"");
                                    }
                                    //System.out.println(remaining+"");

                                    //customerReport.setAmount(remaining+"");

                                progressDialog.dismiss();
                                //Toast.makeText(CustomerReportActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();

                                //initiateRecyclerview();
                                editText_cust_acc_number.setText("");

                                adapter.notifyDataSetChanged();

                            }
                            else if (jsonObject.getString("success").equals("0"))
                            {
                                progressDialog.dismiss();
                                Toast.makeText(CustomerReportActivity.this, jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                                editText_cust_acc_number.setText("");
                            }
                    }

                    //initiateRecyclerview();
                }

                catch (JSONException e)
                {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("c_id",account_numberStr);
                params.put("agent_id",session.getUserID());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        initiateRecyclerview();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }
    }
}
