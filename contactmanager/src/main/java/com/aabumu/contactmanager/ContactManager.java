package com.aabumu.contactmanager;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umesh on 9/10/17.
 */

public class ContactManager {

    private final Context context;

    public ContactManager(Context context) {
        this.context = context;
    }

    /**
     * Get contact from device using background thread
     * @param contactListener callback for the contact list
     */
    public void fetchContact(final ContactListener contactListener) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                contactListener.onContactFetched(getContacts());
            }
        });
    }

    /**
     * Get contact in same thread
     * @return List of @{@link Contact}
     */
    public List<Contact> fetchContact(){
        return getContacts();
    }

    private List<Contact> getContacts() {

        List<Contact> contactList = new ArrayList<>();

        String[] projection = new String[]{
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        Cursor cursorContact = context
                .getContentResolver()
                .query(ContactsContract.Contacts.CONTENT_URI, projection, null, null,
                        ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        if (cursorContact != null && cursorContact.moveToFirst()) {
            while (!cursorContact.isAfterLast()) {
                String id = cursorContact.getString(
                        cursorContact.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursorContact.getString(cursorContact.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                Contact contact = new Contact();
                contact.setName(name);

                if (cursorContact.getInt(cursorContact.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    contact.setPhoneNumber(getPhoneNumber(id));
                } else {
                    contact.setPhoneNumber(new ArrayList<String>());
                }
                contactList.add(contact);
                cursorContact.moveToNext();
            }
            cursorContact.close();
        }

        return contactList;
    }

    private List<String> getPhoneNumber(String id) {
        Cursor cursorInfo = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER},
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{id}, null);
        List<String> phoneNumberList = new ArrayList<>();
        if (cursorInfo != null && cursorInfo.moveToFirst()) {
            while (!cursorInfo.isAfterLast()) {
                String phoneNo = cursorInfo.getString(cursorInfo.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneNumberList.add(phoneNo);
                cursorInfo.moveToNext();
            }
            cursorInfo.close();
        }
        return phoneNumberList;
    }
}
