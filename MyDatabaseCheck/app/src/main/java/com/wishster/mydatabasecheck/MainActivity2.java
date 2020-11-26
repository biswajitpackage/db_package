package com.wishster.mydatabasecheck;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    SettingDb settingDb = new SettingDb();
    JSONObject jsonObject2 = new JSONObject();
    static JSONObject jsonObject = new JSONObject();
    String TableName = "myTable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        try {
            jsonObject.put("id", "auto");
            jsonObject.put("name", "TEXT");
            jsonObject.put("address", "TEXT");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        settingDb.createTable(TableName, jsonObject);
        Mydb mydb = new Mydb(MainActivity2.this, "mydb", null, 4);
        try {

            jsonObject2.put("name", "Biswajit2");
            jsonObject2.put("address", "Naihati2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*try {
            Log.d("testdata", "" + mydb.addData(TableName, jsonObject2));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        Log.d("testdata", "Total Item " + mydb.geTotalItemCount(TableName));
        Log.d("testdata", "Last Item " + mydb.getLastId(TableName, "id"));
        Log.d("testdata", "Cursor " + mydb.getLastId(TableName, "id"));
        Cursor cur = null;
        if (mydb.geTotalItemCount(TableName) > 0)
            cur = mydb.getAllRow(TableName, "id");


        if (cur.moveToFirst()) {
            while (cur.moveToNext())
            {
                Log.d("testdata", "All Ityems " + cur.getString(cur.getColumnIndex("name")));
            }
        }
        try {
            Log.d("testdata", "UPDATE : " + mydb.updateData(TableName, jsonObject2));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Update daata
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "Biswajit Deb");
        map.put("address", "Naihati Kolkata 3");
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("id", "4");
        Map<String, String> map3 = new HashMap<String, String>();
        map3.put("name", "Biswajit2");
        //map2.put("name","Biswajit Deb");
        //Toast.makeText(this, "UPDATE "+mydb.updateData(TableName,map,map2), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Delete "+mydb.deleteData(TableName,map2), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Delete All"+mydb.deleteDataAll(TableName), Toast.LENGTH_SHORT).show();

        //Toast.makeText(this, "" + mydb.getAllData(TableName).get("name"), Toast.LENGTH_SHORT).show();
        //Converting to Set so that we can traverse
        /*Map<String,String> data2=mydb.getAllData(TableName);
        for(Map.Entry m:data2.entrySet())
        {
            Log.d("testdata", "All  data : " + data2.get("name"));
        }
        Map<String,String> data1=mydb.getSelectedData(TableName,map3);
        for(Map.Entry m:data1.entrySet())
        {
            Log.d("testdata", "Selected data : " +data1.get("name"));
        }*/
        if (mydb.geTotalItemCount(TableName) > 0)
            cur = mydb.getAllData(TableName);
        if (cur.moveToFirst()) {
            while (cur.moveToNext())
            {
                Log.d("testdata", "All  data : " + cur.getString(cur.getColumnIndex("name")));
            }
        }
        if (mydb.geTotalItemCount(TableName) > 0)
            cur = mydb.getSelectedData(TableName,map3);
        if (cur.moveToFirst()) {
            while (cur.moveToNext())
            {
                Log.d("testdata", "Selected data : " + cur.getString(cur.getColumnIndex("name")));
            }
        }


    }
}