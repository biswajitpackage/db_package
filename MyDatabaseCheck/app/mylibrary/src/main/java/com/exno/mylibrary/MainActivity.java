package com.exno.mylibrary;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    JSONObject jsonObject=new JSONObject();
    JSONObject jsonObject2=new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        try {
            jsonObject.put("id","auto");
            jsonObject.put("name","TEXT");
            jsonObject.put("address","TEXT");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatabaseHandler db = new DatabaseHandler(
                getApplicationContext(),
                "db2",
                1,
                jsonObject,
                "table_name"
        );

        try {

            jsonObject2.put("name","Biswajit");
            jsonObject2.put("address","Naihati");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            Log.d("printall",""+db.addData(jsonObject2));
            ;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // Inserting Contacts
      /*  Log.d("Insert: ", "Inserting ..");
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));

        // Reading all contacts
        Log.d("Reading: ", "Reading all contacts..");
        List<Contact> contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " +
                    cn.getPhoneNumber();
            // Writing Contacts to log
            Log.d("Name: ", log);
        }*/
        Toast.makeText(this, ""+db.getContactsCount(), Toast.LENGTH_SHORT).show();
    }
}