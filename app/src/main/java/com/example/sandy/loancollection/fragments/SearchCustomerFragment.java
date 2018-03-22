package com.example.sandy.loancollection.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.adapter.SearchCustomerAdapter;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.interfaces.RecyclerItemTouchHelperListener;
import com.example.sandy.loancollection.model.CustomerList;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.swiper.SwipeHelper;
import com.example.sandy.loancollection.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchCustomerFragment extends Fragment implements RecyclerItemTouchHelperListener, SearchView.OnQueryTextListener {
    SwipeHelper swipeHelper;
    ItemTouchHelper itemTouchHelper;
    private EditText edt_acc_number;
    private Button btn_go_collection;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    public SearchCustomerAdapter adapter;
    private ArrayList<CustomerList> customerListArrayList;
    private String account_numberStr,remainingAmt;
    private String loan_id,name,address,account,interest,final_amount,total_amount,collection_amount;
    private JSONObject jsonObject,jsonObject1;
    private JSONArray jsonArray;
    private LinearLayout linearLayout;
    SessionManager session;
    private int flag=0;
    //private SwipeRefreshLayout swipeRefreshLayout;
    public static ArrayList<String> indexArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_search_customer, container, false);
        indexArray=new ArrayList<String>();
        //edt_acc_number = view.findViewById(R.id.editText_search_customer_fragment);
        //swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        //btn_go_collection = view.findViewById(R.id.button_search_customer_fragment);
        recyclerView = view.findViewById(R.id.recycler_View_search);
        linearLayout = view.findViewById(R.id.linear_layout);
        customerListArrayList=new ArrayList<>();
        session = new SessionManager(getActivity());
        //swipeRefreshLayout.setOnRefreshListener(this);
        //initiateRecyclerview();
        SearchAccountNumber();
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                SearchAccountNumber();
//            }
//        });
        return view;

    }

    public SearchCustomerFragment()
    {

    }

    public void SearchAccountNumber()
    {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpURL.SEARCH_ACCOUNT_NO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    customerListArrayList=new ArrayList<CustomerList>();
                    jsonObject1 = new JSONObject(response);
                    if (jsonObject1.getString("success").equals("0"))
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(),jsonObject1.getString("message"),Toast.LENGTH_SHORT).show();
                        //adapter.notifyDataSetChanged();
                        initiateRecyclerview();
                    }
                    jsonArray = jsonObject1.getJSONArray("get_primary_information");

                    for (int i=0;i<jsonArray.length();i++)
                    {
                        jsonObject = jsonArray.getJSONObject(i);

                        name = jsonObject.getString("customer_name");
                        address = jsonObject.getString("collection_address");
                        account = jsonObject.getString("account");
                        interest = jsonObject.getString("final_si");
                        final_amount = jsonObject.getString("final_amount");
                        total_amount = jsonObject.getString("amount");
                        collection_amount = jsonObject.getString("final_am");
                        remainingAmt = jsonObject.getString("remaining_amount");
                        loan_id = jsonObject.getString("loan_id");
                        if (jsonObject1.getString("success").equals("1"))
                        {
                            //CustomerList customerList = new CustomerList(name,address,account,interest,final_amount,total_amount,collection_amount);

                            CustomerList customerList = new CustomerList();

                            customerList.setCustomer_name(name);
                            customerList.setCollection_address(address);
                            customerList.setAccount(account);
                            customerList.setFinal_si(interest);
                            customerList.setFinal_amount(final_amount);
                            customerList.setAmount(total_amount);
                            customerList.setFinal_am(collection_amount);
                            customerList.setRemaining_amount(remainingAmt);
                            customerList.setLoan_id(loan_id);
                            customerListArrayList.add(customerList);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),jsonObject1.getString("message"),Toast.LENGTH_SHORT).show();
                            //adapter.notifyDataSetChanged();

                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(),jsonObject1.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                    }

                    initiateRecyclerview();
                    //swipeRefreshLayout.setRefreshing(false);

                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

                //adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();
                //swipeRefreshLayout.setRefreshing(false);

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("agent_id",session.getUserID());
                //params.put("agent_id",account_numberStr);
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onResume() {
        super.onResume();
        //adapter.notifyDataSetChanged();
    }

    void initiateRecyclerview()
    {
        callSwaping();

        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter=new SearchCustomerAdapter(itemTouchHelper,customerListArrayList,getContext());
        swipeHelper = new SwipeHelper(adapter,this);
        itemTouchHelper = new ItemTouchHelper(swipeHelper);
        adapter.setTouchHelper(itemTouchHelper);
        recyclerView.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //btn_go_collection.setOnClickListener(this);

    }

    private void callSwaping() {
        int newPosition=0;
        ArrayList<CustomerList> customerListsTemp=new ArrayList<CustomerList>();
        //customerListsTemp=customerListArrayList;
        indexArray=session.getIndexSerialItem();
        if(indexArray==null)
        {
            indexArray=new ArrayList<>();
        }
        if(indexArray.size()==0)
        {
            for(CustomerList item:customerListArrayList)
            {
                indexArray.add(item.getAccount());
            }
            session.setIndexSerialItem(indexArray);
        }
        else {
            for (CustomerList itemCustomr : customerListArrayList)
            {
                int count = 0;
                for (String itemStr : indexArray) {
                    if (itemStr.equals(itemCustomr.getAccount()))
                    {
                        customerListsTemp.add(itemCustomr);
                        break;
                    }
                    if(count==indexArray.size()-1)
                    {
                        customerListsTemp.add(itemCustomr);
                    }
                    count++;
                }
            }
        }
        customerListArrayList=customerListsTemp;
        //System.out.println("session.setIndexSerialItem(indexArray)"+session.getIndexSerialItem());
    }

    public ArrayList<CustomerList> onItemMove(int oldPosition, int newPosition,
                           ArrayList<CustomerList> customerListsTemp) {
        CustomerList targetUser = customerListsTemp.get(oldPosition);
        CustomerList user = new CustomerList(targetUser);
        customerListsTemp.remove(oldPosition);
        customerListsTemp.add(newPosition, user);

        return customerListsTemp;

    }
    public void onSwiped(RecyclerView.ViewHolder viewHolder,int direction, int position) {
        if (viewHolder instanceof SearchCustomerAdapter.RecyclerViewHolder) {
            // get the removed item name to display it in snack bar
            String name = customerListArrayList.get(viewHolder.getAdapterPosition()).getCustomer_name();

            // backup of removed item for undo purpose
            final CustomerList deletedItem = customerListArrayList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();
            SearchCustomerAdapter.customerListRemoved=deletedItem;
            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(linearLayout, name + " moving to collection!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // undo is selected, restore the deleted item
                    flag=1;
                    adapter.restoreItem(deletedItem, deletedIndex);

                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
            startProgress(direction,position);
        }
    }

    public void startProgress(final int direction,final int position)
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                if(flag==0){
                    openActivity(direction,position);
                }
                else {
                    flag=0;
                }
            }
        }, 3000);
    }

    private void openActivity(int direction,int position)
    {
        //SystemClock.sleep(3000);
        if (direction == ItemTouchHelper.RIGHT)
        {
            adapter.openCollectionActivity();
            adapter.notifyDataSetChanged();
        }
        else if (direction == ItemTouchHelper.LEFT)
        {
            adapter.openReasonActivity();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu,inflater);
        MenuItem item = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_refresh:
                SearchAccountNumber();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Searchview option in toolbar
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    // Searchview option for searching specific item in toolbar
    @Override
    public boolean onQueryTextChange(String newText) {

        newText = newText.toLowerCase();

        ArrayList<CustomerList> custList = new ArrayList<>();

        for (CustomerList customerList : customerListArrayList)
        {
            String name = customerList.getCustomer_name().toLowerCase();
            if (name.contains(newText))
            {
                custList.add(customerList);
            }
            String address = customerList.getCollection_address().toLowerCase();
            if (address.contains(newText))
            {
                custList.add(customerList);
            }
            String account = customerList.getAccount();
            if (account.contains(newText))
            {
                custList.add(customerList);
            }
        }

        adapter.setFilter(custList);

        return true;
    }

//    @Override
//    public void onRefresh() {
//        SearchAccountNumber();
//    }
}
