package com.example.sandy.loancollection.model;

/**
 * Created by sandy on 5/1/18.
 */

public class AgentReport
{
    String cust_name,cust_amount,collection_time;

    public AgentReport() {
    }

    public AgentReport(String cust_name, String cust_amount, String collection_time)
    {

        this.cust_name = cust_name;
        this.cust_amount = cust_amount;
        this.collection_time = collection_time;
    }

    public String getCollection_time() {
        return collection_time;
    }

    public void setCollection_time(String collection_time) {
        this.collection_time = collection_time;
    }

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_amount() {
        return cust_amount;
    }

    public void setCust_amount(String cust_amount) {
        this.cust_amount = cust_amount;
    }



}
