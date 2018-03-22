package com.example.sandy.loancollection.model;

/**
 * Created by sandy on 4/1/18.
 */

public class CustomerReport
{
    private String final_am;
    private String dateTime;
    private String amount;
    private String remainingAmount;

    public String getRemainingAmount ()
    {
        return remainingAmount;
    }

    public void setRemainingAmount (String remainingAmount)
    {
        this.remainingAmount = remainingAmount;
    }
    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getFinal_am ()
    {
        return final_am;
    }

    public void setFinal_am (String final_am)
    {
        this.final_am = final_am;
    }

    public String getDateTime ()
    {
        return dateTime;
    }

    public void setDateTime (String dateTime)
    {
        this.dateTime = dateTime;
    }

    @Override
    public String toString()
    {
        return "CustomerReport [remainingAmount = "+remainingAmount+"amount = "+amount+", final_am = "+final_am+", dateTime = "+dateTime+"]";
    }
}
