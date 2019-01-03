package com.saneesh.serviceapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by saNeesH on 2018-10-06.
 */

public class BankServiceModel implements Serializable
{

    @SerializedName("img_name")
    String imgPath;

    @SerializedName("bank_name")
    String bankName;

    @SerializedName("bank_balance")
    BankInfoModel bankbalance;

    @SerializedName("mini_statement")
    BankInfoModel miniStatement;

    @SerializedName("customer_no")
    String customerNo;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BankInfoModel getBankbalance() {
        return bankbalance;
    }

    public void setBankbalance(BankInfoModel bankbalance) {
        this.bankbalance = bankbalance;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public BankInfoModel getMiniStatement() {
        return miniStatement;
    }

    public void setMiniStatement(BankInfoModel miniStatement) {
        this.miniStatement = miniStatement;
    }
}
