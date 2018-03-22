package com.example.sandy.loancollection.model;

/**
 * Created by sandy on 21/2/18.
 */

public class AdminLogin
{
   private String admin_username,admin_password,admin_id,admin_name;

    public AdminLogin(String admin_username, String admin_password, String admin_id, String admin_name) {
        this.admin_username = admin_username;
        this.admin_password = admin_password;
        this.admin_id = admin_id;
        this.admin_name = admin_name;
    }

    public AdminLogin(String admin_username, String admin_password, String admin_id) {
        this.admin_username = admin_username;
        this.admin_password = admin_password;
        this.admin_id = admin_id;
    }

    public String getAdmin_username() {
        return admin_username;
    }

    public void setAdmin_username(String admin_username) {
        this.admin_username = admin_username;
    }

    public String getAdmin_password() {
        return admin_password;
    }

    public void setAdmin_password(String admin_password) {
        this.admin_password = admin_password;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }
}
