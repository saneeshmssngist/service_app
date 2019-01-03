package com.saneesh.serviceapp.Model;

import java.io.Serializable;

/**
 * Created by saNeesH on 2018-10-13.
 */

public class PhoneServiceModel implements Serializable {

    String title;

    String ussd;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUssd() {
        return ussd;
    }

    public void setUssd(String ussd) {
        this.ussd = ussd;
    }
}
