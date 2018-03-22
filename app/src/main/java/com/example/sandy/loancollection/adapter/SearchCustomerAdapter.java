package com.example.sandy.loancollection.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.activity.CollectionActivity;
import com.example.sandy.loancollection.activity.MainActivity;
import com.example.sandy.loancollection.activity.ReasonActivity;
import com.example.sandy.loancollection.fragments.SearchCustomerFragment;
import com.example.sandy.loancollection.httpHelper.HttpURL;
import com.example.sandy.loancollection.interfaces.ItemTouchHelperAdapter;
import com.example.sandy.loancollection.model.CustomerList;
import com.example.sandy.loancollection.printClasses.MP100MainFragment;
import com.example.sandy.loancollection.session.SessionManager;
import com.example.sandy.loancollection.volley.VolleySingleton;
import com.ngx.mp100sdk.NGXPrinter;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by sandy on 17/1/18.
 */

public class SearchCustomerAdapter extends RecyclerView.Adapter < SearchCustomerAdapter.RecyclerViewHolder > implements ItemTouchHelperAdapter {
    private ItemTouchHelper itemTouchHelper;
    public static ArrayList < CustomerList > customerListArrayList;
    private Context context;
    private CustomerList customerList;
    public static CustomerList customerListRemoved;
    private ProgressDialog progressDialog;
    SessionManager session;
    private String strDate;

    public NGXPrinter ngxPrinter = MainActivity.ngxPrinter;

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_name,textView_loan_id, textView_account, textView_address, textView_rem_amount, textView_undo_right, textView_undo_left, textView_collection, textView_total;
        private ImageView imageView;
        public RelativeLayout viewBackground ;
        public LinearLayout viewForeground;
        private RecyclerViewHolder(View itemView) {
            super(itemView);
            textView_loan_id = itemView.findViewById(R.id.text_customer_loan_number_adapter);
            textView_name = itemView.findViewById(R.id.text_customer_name_adapter);
            textView_account = itemView.findViewById(R.id.text_customer_account_adapter);
            textView_address = itemView.findViewById(R.id.text_customer_address_adapter);
            textView_collection = itemView.findViewById(R.id.text_collection_amount_adapter);
            textView_total = itemView.findViewById(R.id.text_customer_loan_amt_adapter);
            textView_rem_amount = itemView.findViewById(R.id.text_remaining_amount_adapter);
            imageView = itemView.findViewById(R.id.imgView);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            itemView.findViewById(R.id.text_customer_name_adapter).setSelected(true);
            session = new SessionManager(context);

        }
    }

    public SearchCustomerAdapter(ItemTouchHelper itemTouchHelper, ArrayList < CustomerList > customerListArrayList, Context context) {
        this.itemTouchHelper = itemTouchHelper;
        this.customerListArrayList = customerListArrayList;
        this.context = context;
    }

    @Override
    public void onItemMove(int oldPosition, int newPosition)
    {
            CustomerList targetUser = customerListArrayList.get(oldPosition);
            CustomerList user = new CustomerList(targetUser);
//            customerListArrayList.remove(oldPosition);
//            customerListArrayList.add(newPosition, user);
            SearchCustomerFragment.indexArray.remove(oldPosition);
            SearchCustomerFragment.indexArray.add(newPosition, user.getAccount());
            session.setIndexSerialItem(SearchCustomerFragment.indexArray);
            notifyItemMoved(oldPosition, newPosition);

    }

    @Override
    public void onItemDismiss(int position) {
  /*customerListArrayList.remove(position);
  notifyItemRemoved(position);*/

    }

    public void openCollectionActivity() {
        try {
            customerList=new CustomerList();
            customerList=customerListRemoved;
            initAdapter(customerList.getCustomer_name(),
                    customerList.getAccount(),customerList.getCollection_address(),
                    customerList.getFinal_am(),
                    customerList.getAmount(),customerList.getRemaining_amount(),
                    customerList.getLoan_id());
            Intent intent = new Intent(context, CollectionActivity.class);

            intent.putExtra("LOAN_ID_KEY",customerList.getLoan_id());
            intent.putExtra("NAME_KEY", customerList.getCustomer_name());
            intent.putExtra("ACCOUNT_KEY", customerList.getAccount());
            intent.putExtra("ADDRESS_KEY", customerList.getCollection_address());
            intent.putExtra("INSTALLAMENT_KEY", customerList.getFinal_am());
            intent.putExtra("LOAN_KEY", customerList.getAmount());
            intent.putExtra("REMAINING_KEY", customerList.getRemaining_amount());
            //context.startActivity(intent);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void openReasonActivity() {
        try {
            customerList=new CustomerList();
            //if(position==customerListArrayList.size()){
                customerList=customerListRemoved;
//            }else {
//                customerList = customerListArrayList.get(position);
//            }
            Intent intent = new Intent(context, ReasonActivity.class);
            intent.putExtra("LOAN_ID_KEY",customerList.getLoan_id());
            intent.putExtra("NAME_KEY", customerList.getCustomer_name());
            intent.putExtra("ACCOUNT_KEY", customerList.getAccount());
            context.startActivity(intent);

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SearchCustomerAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search_customer, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchCustomerAdapter.RecyclerViewHolder holder, final int position) {
        try {
            CustomerList customerList = customerListArrayList.get(position);
            holder.textView_name.setText(customerList.getCustomer_name());
            holder.textView_account.setText(customerList.getAccount());
            holder.textView_address.setText(customerList.getCollection_address());
            holder.textView_collection.setText(customerList.getFinal_am());
            holder.textView_total.setText(customerList.getAmount());
            holder.textView_rem_amount.setText(customerList.getRemaining_amount());
            holder.textView_loan_id.setText(customerList.getLoan_id());
            holder.imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        itemTouchHelper.startDrag(holder);
                    }
                    return false;
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openCollection2Activity(position);

                }
            });
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return customerListArrayList.size();
    }

    public void setTouchHelper(ItemTouchHelper itemTouchHelper) {

        this.itemTouchHelper = itemTouchHelper;
    }

    public void removeItem(int position) {
        customerListArrayList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
    public void restoreItem(CustomerList customerList, int position) {
        customerListArrayList.add(position, customerList);
        // notify item added by position
        notifyItemInserted(position);
    }

    // filter for searchview in toolbar
    public void setFilter(ArrayList<CustomerList> custList)
    {
        customerListArrayList = new ArrayList<>();

        customerListArrayList.addAll(custList);
        notifyDataSetChanged();
    }


    public void openCollection2Activity(int position) {
        try {
            //customerList=new CustomerList();

            //customerList=customerListRemoved;
            CustomerList customerList = new CustomerList();

            customerList = customerListArrayList.get(position);

            Intent intent = new Intent(context, CollectionActivity.class);

            intent.putExtra("LOAN_ID_KEY",customerList.getLoan_id());
            intent.putExtra("NAME_KEY", customerList.getCustomer_name());
            intent.putExtra("ACCOUNT_KEY", customerList.getAccount());
            intent.putExtra("ADDRESS_KEY", customerList.getCollection_address());
            intent.putExtra("INSTALLAMENT_KEY", customerList.getFinal_am());
            intent.putExtra("LOAN_KEY", customerList.getAmount());
            intent.putExtra("REMAINING_KEY", customerList.getRemaining_amount());
            context.startActivity(intent);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }


    public void initAdapter(final String NAME_KEY, final String ACCOUNT_KEY
            , final String ADDRESS_KEY, final String INSTALLAMENT_KEY,
                            String LOAN_KEY, final String REMAINING_KEY,
                            final String LOAN_ID_KEY)
    {
        //name = editText_amount.getText().toString();
        progressDialog = new ProgressDialog(context);
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
                        Toast.makeText(context,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();


                    }
                    if(jsonObject.getString("success").equals("1"));
                    {
                        try {
                            progressDialog.dismiss();

                            if(ngxPrinter!=null) {
                                MP100MainFragment mp = new MP100MainFragment(context);
                                mp.printEnglishBill1(ACCOUNT_KEY, NAME_KEY, ADDRESS_KEY, INSTALLAMENT_KEY,strDate);
                                progressDialog.dismiss();

                            }
                            Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                Date date = new Date();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                strDate = simpleDateFormat.format(date);
                Map<String, String> params = new HashMap<>();
                params.put("dateTime",strDate);
                params.put("c_id",ACCOUNT_KEY);
                params.put("final_am",INSTALLAMENT_KEY);
                params.put("loan_id",LOAN_ID_KEY);
                params.put("remaining_amount",REMAINING_KEY);
                params.put("agent_id",session.getUserID());

                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}