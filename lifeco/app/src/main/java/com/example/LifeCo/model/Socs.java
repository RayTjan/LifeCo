package com.example.LifeCo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Socs{
    String uid;
    String email;
    String password;
    String name;
    String phoneNumber;
    String account;

    public Socs(){

    }

    public Socs(String uid, String email, String password, String name, String phoneNumber,  String account) {
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.account = account;
    }

    protected Socs(Parcel in) {
        uid = in.readString();
        email = in.readString();
        password = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        account = in.readString();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }


}
