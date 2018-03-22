package com.example.sandy.loancollection.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.model.AdminLogin;
import com.example.sandy.loancollection.model.LoginUser;
import com.example.sandy.loancollection.session.AdminSession;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.volley.VolleySingleton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandy on 2/1/18.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private ProgressDialog progressDialog;
    private EditText editText_user,editText_pass;
    private Button button_login;
    private String username,password,name,user_id,role_id;
    private JSONObject jsonObject;
    SessionManager session;
    AdminSession adminSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_user = findViewById(R.id.edt_userid);
        editText_pass = findViewById(R.id.edt_password);
        button_login = findViewById(R.id.btn_login);
        progressDialog = new ProgressDialog(this);
        button_login.setOnClickListener(this);

        session=new SessionManager(this);
        adminSession=new AdminSession(this);

        setTitle("Login.....");

        if (SessionManager.getInstance(LoginActivity.this).isLoggedIn()) {
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        if (AdminSession.getInstance(LoginActivity.this).isAdminLoggedIn()) {
            finish();
            startActivity(new Intent(LoginActivity.this, AdminActivity.class));

        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_login:
            AgentLogin();
            break;
        }

    }

    private void AgentLogin()
    {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(true);

        username=editText_user.getText().toString();
        password=editText_pass.getText().toString();

        if (username.equals(""))
        {
            editText_user.setError("Please enter your username");
            editText_user.requestFocus();
            return;
        }
        if (password.equals(""))
        {
            editText_pass.setError("Please enter your password");
            editText_pass.requestFocus();
            return;
        }

        StringRequest stringRequest=new StringRequest(Request.Method.POST, HttpURL.LOGIN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response)
            {
                try
                {
                    jsonObject=new JSONObject(response);
                    if(jsonObject.getString("success").equals("2"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        editText_user.setText("");
                        editText_pass.setText("");
                    }
                    name = jsonObject.getString("username");
                    user_id = jsonObject.getString("id");
                    role_id = jsonObject.getString("role_id");

                    if (jsonObject.getString("success").equals("0"))
                    {
                        if (jsonObject.getString("role_id").equals("3")) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            LoginUser loginUser = new LoginUser(username, password, user_id);
                            session.userLogin(loginUser);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                       else if (jsonObject.getString("role_id").equals("1"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            AdminLogin adminLogin = new AdminLogin(username, password, user_id);
                            adminSession.adminUserLogin(adminLogin);
                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            finish();

                        }

                        if (jsonObject.getString("role_id").equals("2"))
                        {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this,"Invalid user type", Toast.LENGTH_SHORT).show();
                            editText_pass.setText("");
                            editText_user.setText("");


                        }
                    }
                    if(jsonObject.getString("success").equals("1"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        editText_user.setText("");
                        editText_pass.setText("");
                    }
                    if(jsonObject.getString("success").equals("2"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        editText_user.setText("");
                        editText_pass.setText("");
                    }
                }
                catch (JSONException e)
                {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    editText_pass.setText("");
                    editText_user.setText("");

                }

            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressDialog.dismiss();
                        //Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        editText_pass.setText("");
                        editText_user.setText("");
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

        };
        VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(stringRequest);
    }

    // onBack pressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
