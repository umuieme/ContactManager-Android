package com.aabumu.contactmanager;

import java.util.List;

/**
 * Created by umesh on 9/11/17.
 */

public class Contact {
    private String name;
    private List<String> phoneNumber;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public List<String> getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(List<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
