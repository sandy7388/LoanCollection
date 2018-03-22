package com.example.sandy.loancollection.httpHelper;

/**
 * Created by sandy on 1/1/18.
 */

public class HttpURL
{

    // Base URL
    private static final String BASE_URL = "http://microfinance.wayzontech.co.in/webservices/";

    // Login URL
    public static final String LOGIN_URL = BASE_URL + "login";

    // Register URL
    public static final String ADD_CUSTOMER_URL = "";

    // Agent Name
    public static final String AGENT_NAME_URL = "";

    // ID proof
    public static final String ID_PROOF_NAME = "";

    // Search Account Number
    public static final String SEARCH_ACCOUNT_NO = BASE_URL + "get_primary_information";

    // Agent Report
    public static final String AGENT_REPORT = BASE_URL + "get_agent_report";

    // Agent Report
    public static final String CUSTOMER_REPORT = BASE_URL + "get_customer_report";

    // Add Money
    public static final String ADD_BALANCE = BASE_URL + "loan_collection";

    // Display Transactions
    public static final String TRANSACTION_HISTORY = "";

    // Get Remaining Loan Amount
    public static final String REMAINING_AMOUNT = "";

    // Loan Reason
    public static final String LOAN_REASON = BASE_URL + "load_reason";


}
