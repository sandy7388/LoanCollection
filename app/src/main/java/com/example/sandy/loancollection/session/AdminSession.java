package com.example.sandy.loancollection.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.sandy.loancollection.activity.LoginActivity;
import com.example.sandy.loancollection.model.AdminLogin;
import com.example.sandy.loancollection.model.LoginUser;

/**
 * Created by sandy on 21/2/18.
 */

public class AdminSession
{
    private static AdminSession mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_ADMIN_NAME = "myAdminSharedPref";
    private static final String KEY_ADMIN_USERNAME = "admin_username";
    private static final String KEY_ADMIN_PASSWORD = "admin_password";
    private static final String KEY_ADMIN_ID = "admin_user_id";
    private static final String KEY_ADMIN_NAME = "admin_name";

    public AdminSession(Context context)
    {
        mCtx = context;
    }
    public static synchronized AdminSession getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new AdminSession(context);
        }
        return mInstance;
    }


    // for user login to store  the user session
    public boolean adminUserLogin(AdminLogin adminLogin)
    {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ADMIN_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ADMIN_USERNAME, adminLogin.getAdmin_username());
        editor.putString(KEY_ADMIN_PASSWORD,adminLogin.getAdmin_password());
        editor.putString(KEY_ADMIN_ID,adminLogin.getAdmin_name());
        editor.putString(KEY_ADMIN_NAME,adminLogin.getAdmin_id());

        editor.apply();
        editor.commit();

        return true;
    }

    //check whether the user is logged in or not
    public boolean isAdminLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ADMIN_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_ADMIN_USERNAME, null) != null)
        {
            return true;
        }
        return false;
    }

    // Get Username
    public String getAdminUsername()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ADMIN_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADMIN_USERNAME, null) ;
    }

    // Get User id
    public String getAdminID()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ADMIN_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADMIN_NAME, null) ;
    }

    // Get Users Name
    public String getAdminName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ADMIN_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ADMIN_ID,null);
    }

    // this method is used to logout the session
    public void logoutAdmin()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_ADMIN_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
