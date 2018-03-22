package com.example.sandy.loancollection.session;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.sandy.loancollection.activity.LoginActivity;
import com.example.sandy.loancollection.model.LoginUser;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by sandy on 25/11/17.
 */

@SuppressLint("StaticFieldLeak")
public class SessionManager
{

    private static SessionManager mInstance;
    private static Context mCtx;

    private static final String SHARED_PREF_NAME = "mySharedPref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_NAME = "name";

//    private static final String KEY_LOCATION_NAME = "location";
//    private static final String KEY_WORKING_STATUS = "working_status";

    public SessionManager(Context context)
    {
        mCtx = context;
    }
    public static synchronized SessionManager getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new SessionManager(context);
        }
        return mInstance;
    }

    // for user login to store  the user session
    public boolean userLogin(LoginUser loginUser)
    {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, loginUser.getUsername());
        editor.putString(KEY_USER_PASSWORD,loginUser.getPassword());
        editor.putString(KEY_NAME,loginUser.getName());
        editor.putString(KEY_USER_ID,loginUser.getId());

        editor.apply();
        editor.commit();

        return true;
    }
    //check whether the user is logged in or not
    public boolean isLoggedIn()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USERNAME, null) != null)
        {
            return true;
        }
        return false;
    }

    // Get Username
    public String getUsername()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) ;
    }

    // Get User id
    public String getUserID()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_ID, null) ;
    }

    // Get Users Name
    public String getName()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME,null);
    }

    // this method is used to logout the session
    public void logout()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        editor.commit();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }

    public void setIndexSerialItem(ArrayList<String> items)
    {
        try
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(items);
            prefsEditor.putString("SerializableObject", json);
            prefsEditor.apply();
            prefsEditor.commit();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getIndexSerialItem()
    {
        try
        {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("SerializableObject", "");
            String[] yourSerializableObject = gson.fromJson(json, String[].class);
            ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(yourSerializableObject));

            System.out.println(arrayList);

            return arrayList;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /*public void locationSession(String location)
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_LOCATION_NAME,location);
        editor.commit();
    }
    public String getLocation()
    {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LOCATION_NAME, "") ;
    }

    public void workingStatusSession(String working_status)
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_WORKING_STATUS,working_status);
        editor.commit();
    }

    public String getWorkingStatus()
    {
        SharedPreferences sharedPreferences=mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_WORKING_STATUS,"");
    }*/
}
