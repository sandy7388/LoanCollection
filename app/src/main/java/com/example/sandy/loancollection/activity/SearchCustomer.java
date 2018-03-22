package com.example.sandy.loancollection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.session.SessionManager;

/**
 * Created by sandy on 2/1/18.
 */

public class SearchCustomer extends AppCompatActivity  {
    /*private EditText editText_account_number;
    private Button button_search;
    private String account_numberStr;
    private SessionManager session;*/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_customer);
        /*editText_account_number = findViewById(R.id.editText_search_customer);
        button_search = findViewById(R.id.button_search_customer);

        button_search.setOnClickListener(this);

        session=new SessionManager(this);

        if(!SessionManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.button_search_customer:
                SearchAccountNumber();
                break;
        }

    }

    private void SearchAccountNumber()
    {
        startActivity(new Intent(SearchCustomer.this, CollectionActivity.class));
        finish();
*//*
       account_numberStr = editText_account_number.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL.SEARCH_ACCOUNT_NO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("success").equals("0"))
                    {
                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SearchCustomer.this,CollectionActivity.class);

                        Bundle b = new Bundle();
                        b.putString("response",response);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();

                    }
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("file_number",account_numberStr);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);*//*
    }

    *//*@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_logout:
                SessionManager.getInstance(this).logout();
                finish();
                break;
            case R.id.action_language:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
*//*
    @Override
    public void onBackPressed() {
        //super.onBackPressed();*/
    }

}
