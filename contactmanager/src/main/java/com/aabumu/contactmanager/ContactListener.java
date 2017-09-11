package com.aabumu.contactmanager;

import java.util.List;

/**
 * Created by umesh on 9/11/17.
 */

public interface ContactListener {
    void onContactFetched(List<Contact> contactList);
}
