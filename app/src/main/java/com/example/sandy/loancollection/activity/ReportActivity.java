package com.example.sandy.loancollection.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.sandy.loancollection.R;
import com.example.sandy.loancollection.session.SessionManager;

public class ReportActivity extends AppCompatActivity
{
   /* static final int DATE_DIALOG_ID = 0;

    Button button_agent_report,button_customer_report,button_customer_number,button_report_date;
    EditText editText_cust_acc_number,editText_agent_report_date;
    SessionManager session;
    DatePickerDialog datePickerDialog;
    private int date_Year,date_Month,date_Day;
    Calendar calendar;*/

    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        /*button_agent_report = findViewById(R.id.btn_agent_report);
        button_customer_report = findViewById(R.id.btn_customer_report);
        button_customer_number = findViewById(R.id.btn_getReport);
        button_report_date = findViewById(R.id.btn_getReport_agent);
        editText_agent_report_date = findViewById(R.id.edt_report_agent_date);
        editText_cust_acc_number = findViewById(R.id.edt_report_customer_number);

        button_customer_report.setOnClickListener(this);
        button_agent_report.setOnClickListener(this);
        button_customer_number.setOnClickListener(this);
        button_report_date.setOnClickListener(this);
        session=new SessionManager(this);

        if(!SessionManager.getInstance(this).isLoggedIn())
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        calendar=Calendar.getInstance();
        date_Year=calendar.get(Calendar.YEAR);
        date_Month=calendar.get(Calendar.MINUTE);
        date_Day=calendar.get(Calendar.DAY_OF_MONTH);

        //Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        editText_agent_report_date.setText( sdf.format(calendar.getTime()));

        editText_agent_report_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    protected Dialog onCreateDialog(int id)
    {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        date_Year, date_Month, date_Day);

        }

        return null;

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener()
    {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            date_Year = year;
            date_Month = monthOfYear;
            date_Day = dayOfMonth;
            editText_agent_report_date.setText(new StringBuilder().append(date_Day).append("/").append(date_Month+1).append("/").append(date_Year));

        }

    };

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_agent_report:
                getAgentReport();

                break;

            case R.id.btn_customer_report:
                customerReport();
                break;

            case R.id.btn_getReport:
                //getCustomerReport();
                startActivity(new Intent(ReportActivity.this,CustomerReportActivity.class));
                finish();
                break;

            case R.id.btn_getReport_agent:
                startActivity(new Intent(ReportActivity.this,AgentReportActivity.class));
               finish();
               break;
        }
    }

    private void getCalender()
    {
        datePickerDialog=new DatePickerDialog(ReportActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editText_agent_report_date.setText(dayOfMonth + "/" + (month +1) + "/" + year);
            }
        },date_Year,date_Month,date_Day);
        datePickerDialog.show();

    }

    private void getAgentReport()
    {
        button_report_date.setVisibility(View.VISIBLE);
        editText_agent_report_date.setVisibility(View.VISIBLE);

    }

    private void customerReport()
    {
        button_customer_number.setVisibility(View.VISIBLE);
        editText_cust_acc_number.setVisibility(View.VISIBLE);
    }




    @Override
    public void onBackPressed() {
        //super.onBackPressed();*/
    }

}
