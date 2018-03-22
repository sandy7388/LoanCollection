package com.example.sandy.loancollection.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.activity.MainActivity;
import com.example.sandy.loancollection.activity.SearchCustomer;
import com.example.sandy.loancollection.session.SessionManager;


public class HomeFragment extends Fragment implements View.OnClickListener
{
    TextView agent_name;
    SessionManager session;
    private LinearLayout linearLayout_add_customer,linearLayout_collections,linearLayout_reports;


    public HomeFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initialisationController(view);

        return view;
    }

    private void initialisationController(View view)
    {
        session=new SessionManager(getActivity());

        //initialization
        agent_name = view.findViewById(R.id.agent_name);
        linearLayout_collections = view.findViewById(R.id.lnr_collections);
        linearLayout_add_customer = view.findViewById(R.id.lnr_add_customer);
        linearLayout_reports = view.findViewById(R.id.lnr_report);

        //setonclick
        linearLayout_collections.setOnClickListener(this);
        linearLayout_add_customer.setOnClickListener(this);
        linearLayout_reports.setOnClickListener(this);

        //setText
        agent_name.setText(session.getUsername());
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.lnr_collections:
                MainActivity.viewPager.setCurrentItem(1);
                break;
            case R.id.lnr_report:
                MainActivity.viewPager.setCurrentItem(2);
                break;
            case R.id.lnr_add_customer:
                MainActivity.viewPager.setCurrentItem(3);
                break;
        }

    }

    public void setFragments(Fragment targetFragment)
    {
        try
        {
            Fragment fragment = targetFragment;
            if (fragment != null)
            {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment).commit();
            }
            else
            {
                // error in creating fragment
                //AppLog.e(TAG, "Error in creating fragment");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
