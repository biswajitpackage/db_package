package com.wishster.mydatabasecheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static  int DATABASE_VERSION = 1;
    //private static final String DATABASE_NAME = "contactsManager";
    private static  String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    JSONObject jsonObject;
    ArrayList tablefield;
    public DatabaseHandler(Context context,String DATABASE_NAME,int ver,JSONObject jsonObject,String table_name) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.DATABASE_VERSION=ver;
        this.jsonObject=jsonObject;
        this.TABLE_CONTACTS=table_name;
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        /*String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";*/
        Iterator<String> iter = jsonObject.keys(); //This should be the iterator you want.
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + "TABLE_CONTACTS" + " ( ";
        while(iter.hasNext()){
            String key = iter.next();
            try {
                if (jsonObject.get(key).equals("auto"))
                    CREATE_CONTACTS_TABLE+=key+" INTEGER PRIMARY KEY , ";
                else
                    CREATE_CONTACTS_TABLE+=key+" "+jsonObject.get(key)+" , ";
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        int lastCommaIndex = CREATE_CONTACTS_TABLE.lastIndexOf(",");
        String finalquary = CREATE_CONTACTS_TABLE.substring(0, lastCommaIndex -1);

        finalquary+= " ) ";
        Log.d("abckol","aa "+finalquary);
        db.execSQL(finalquary);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }
    // code to add the new contact
    long addData(final JSONObject jsonObject2) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        Iterator<String> iter = jsonObject.keys();
        ContentValues values = new ContentValues();
        while(iter.hasNext()) {
            String key = iter.next();

            values.put(key, jsonObject2.get(key).toString());
        }
         // Contact Name
        //values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        for (int i=0;i<values.size();i++){


        }
        // Inserting Row
        long l=db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return l;
    }
    // code to add the new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone

        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    // code to get all contacts in a list view
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

    // code to update the single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }


    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
