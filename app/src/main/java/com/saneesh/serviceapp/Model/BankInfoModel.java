package com.saneesh.serviceapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by saNeesH on 2018-10-07.
 */

public class BankInfoModel implements Serializable
{

    String flag;

    String number;

    String info;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
