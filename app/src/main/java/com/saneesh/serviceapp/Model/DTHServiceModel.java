package com.saneesh.serviceapp.Model;

import java.io.Serializable;

/**
 * Created by saNeesH on 2018-10-14.
 */

public class DTHServiceModel implements Serializable
{
    String title;

    String phone;

    String description;

    String status;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
