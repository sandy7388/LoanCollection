package com.example.sandy.loancollection.model;

/**
 * Created by sandy on 17/1/18.
 */

public class CustomerList
{

    public CustomerList() {
    }

    private String amount;

    private String collection_address;

    private String remaining_amount;

    private String final_am;

    private String deposited;

    private String final_si;

    private String final_amount;

    private String customer_name;

    private String account;

    private String imageUrl;

    private String loan_id;

    public CustomerList(String amount, String collection_address,
                        String remaining_amount, String final_am,
                        String deposited, String final_si,
                        String final_amount, String customer_name,
                        String account, String imageUrl,
                        String loan_id) {

        this.amount = amount;
        this.collection_address = collection_address;
        this.remaining_amount = remaining_amount;
        this.final_am = final_am;
        this.deposited = deposited;
        this.final_si = final_si;
        this.final_amount = final_amount;
        this.customer_name = customer_name;
        this.account = account;
        this.imageUrl = imageUrl;
        this.loan_id = loan_id;
    }

    public CustomerList(CustomerList customerList) {
        this.amount = customerList.amount;
        this.collection_address = customerList.collection_address;
        this.remaining_amount = customerList.remaining_amount;
        this.final_am = customerList.final_am;
        this.deposited = customerList.deposited;
        this.final_si = customerList.final_si;
        this.final_amount = customerList.final_amount;
        this.customer_name = customerList.customer_name;
        this.account = customerList.account;
        this.imageUrl = customerList.imageUrl;
        this.loan_id = customerList.loan_id;

    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getCollection_address ()
    {
        return collection_address;
    }

    public void setCollection_address (String collection_address)
    {
        this.collection_address = collection_address;
    }

    public String getRemaining_amount ()
    {
        return remaining_amount;
    }

    public void setRemaining_amount (String remaining_amount)
    {
        this.remaining_amount = remaining_amount;
    }

    public String getFinal_am ()
    {
        return final_am;
    }

    public void setFinal_am (String final_am)
    {
        this.final_am = final_am;
    }

    public String getDeposited ()
    {
        return deposited;
    }

    public void setDeposited (String deposited)
    {
        this.deposited = deposited;
    }

    public String getFinal_si ()
    {
        return final_si;
    }

    public void setFinal_si (String final_si)
    {
        this.final_si = final_si;
    }

    public String getFinal_amount ()
    {
        return final_amount;
    }

    public void setFinal_amount (String final_amount)
    {
        this.final_amount = final_amount;
    }

    public String getCustomer_name ()
    {
        return customer_name;
    }

    public void setCustomer_name (String customer_name)
    {
        this.customer_name = customer_name;
    }

    public String getAccount ()
    {
        return account;
    }

    public void setAccount (String account)
    {
        this.account = account;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }
}
