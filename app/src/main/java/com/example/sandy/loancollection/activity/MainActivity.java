package com.example.sandy.loancollection.activity;

import java.util.Locale;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.adapter.viewpager.ViewPagerAdapter;
import com.example.sandy.loancollection.fragments.AddCustomerFragment;
import com.example.sandy.loancollection.fragments.HomeFragment;
import com.example.sandy.loancollection.fragments.ReportsFragment;
import com.example.sandy.loancollection.fragments.SearchCustomerFragment;
import com.example.sandy.loancollection.printClasses.MP100MainFragment;
import com.example.sandy.loancollection.session.SessionManager;
import com.ngx.mp100sdk.Intefaces.INGXCallback;
import com.ngx.mp100sdk.NGXPrinter;

public class MainActivity extends RuntimePermissionActivity
{
    private static final int REQUEST_PERMISSIONS = 20;
    SessionManager session;
    Locale myLocale;
    public static ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;

    HomeFragment homeFragment;
    public static SearchCustomerFragment searchCustomerFragment;
    ReportsFragment reportsFragment;
    AddCustomerFragment addCustomerFragment;
    public static NGXPrinter ngxPrinter = NGXPrinter.getNgxPrinterInstance();
    private INGXCallback ingxCallback;
    MP100MainFragment nm;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session=new SessionManager(this);
        permission();
        // Check either login or not
        if(!SessionManager.getInstance(this).isLoggedIn())
        {
            startActivity(new Intent(this, LoginActivity.class));
            finish();

        }
        else {
            nm=new MP100MainFragment(this);
            viewPager = findViewById(R.id.viewpager);
            //Initializing the bottomNavigationView
            bottomNavigationView = findViewById(R.id.navigation);
            bottomNavigation();
            ngxPrinter = NGXPrinter.getNgxPrinterInstance();
            initPrinter();

        }
    }
    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            //  Toast.makeText(this,"Destroy Called",Toast.LENGTH_SHORT).show();
            if(nm.ngxPrinter!=null) {
                nm.ngxPrinter.onActivityDestroy();
                // mBtp.onActivityDestroy();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    void initPrinter()
    {
        try{
            ingxCallback = new INGXCallback() {
                @Override
                public void onRunResult(boolean isSuccess) {
                    Log.e("NGX", "onRunResult:" + isSuccess);

                }

                @Override
                public void onReturnString(String result) {
                    Log.e("NGX", "onReturnString:" + result);
                }

                @Override
                public void onRaiseException(int code, String msg) {
                    Log.e("NGX", "onRaiseException:" + code + ":" + msg);
                }
            };
            ngxPrinter.initService(this,ingxCallback);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    // Runtime Permission
    @Override
    public void onPermissionsGranted(final int requestCode) {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }

    void permission() {
        MainActivity.super.requestAppPermissions(new
                        String[]{Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION}, R.string
                        .runtime_permissions_txt
                        , REQUEST_PERMISSIONS);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_logout:
//                SessionManager.getInstance(this).logout();
//                finish();
                alertForLogout();
                break;
            case R.id.action_marathi:
                setLocale("mr");
                break;
            case R.id.action_english:
                setLocale("en");
                break;
            /*case R.id.action_refresh:
                searchCustomerFragment.SearchAccountNumber();
                break;*/
        }

        return super.onOptionsItemSelected(item);
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

        //finish();
    }


    public void setFragmentWithAddToBackStack(Fragment targetFragment)
    {
        try
        {
            Fragment fragment = targetFragment;
            if (fragment != null)
            {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment)
                        .commit();
            }
            else
                {

                }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Set Languages
    public void setLocale(String lang)
    {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);
    }

    // Set ViewPager
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment=new HomeFragment();
        searchCustomerFragment = new SearchCustomerFragment();
        reportsFragment=new ReportsFragment();
        addCustomerFragment=new AddCustomerFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(searchCustomerFragment);
        adapter.addFragment(reportsFragment);
        adapter.addFragment(addCustomerFragment);
        viewPager.setAdapter(adapter);
    }

    // Bottom Navigation
    private void bottomNavigation()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_collection:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_report:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.navigation_add_customer:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        setupViewPager(viewPager);

    }

    void alertForLogout()
    {
        AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);

        aBuilder.setMessage("Are you sure, You want to LogOut");
        aBuilder.setTitle("Logout Alert");
        aBuilder.setIcon(R.drawable.warning);
        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SessionManager.getInstance(MainActivity.this).logout();
                finish();
            }
        });
        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
            }
        });
        AlertDialog alertDialog = aBuilder.create();
        alertDialog.show();
    }


}
