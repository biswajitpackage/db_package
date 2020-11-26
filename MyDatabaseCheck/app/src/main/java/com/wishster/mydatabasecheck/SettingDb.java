package com.wishster.mydatabasecheck;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

class SettingDb extends AppCompatActivity {
    static String Query="";
    static String TableName="";
    static String DropTableName="";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
   public static String createTable(final String TableName,JSONObject jsonObject){
        DropTableName="DROP TABLE IF EXISTS "+TableName;
       Iterator<String> iter = jsonObject.keys();
       String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TableName + " ( ";
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
       Query=finalquary;
       return finalquary;
   }

}
