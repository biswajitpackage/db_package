package com.wishster.mydatabasecheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class Mydb extends SQLiteOpenHelper {
    String database_name;
    public Mydb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
        this.database_name=name;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String q="CREATE TABLE TABLE_CONTACTS(KEY_ID INTEGER PRIMARY KEY,KEY_NAME TEXT,KEY_PH_NO TEXT)";
        db.execSQL(SettingDb.Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SettingDb.DropTableName);
        onCreate(db);
    }
    Long addData(final String TableName,final JSONObject jsonObject2) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Iterator<String> iter = jsonObject2.keys();
        while(iter.hasNext()) {
            String key = iter.next();
            Log.d("ppdata",key+" "+jsonObject2.get(key));
            values.put(key, jsonObject2.get(key).toString());
        }
       // values.put("KEY_NAME", "hello");
        //values.put("KEY_PH_NO", "123");
        Log.d("ppdata"," Table Name "+TableName);
        Long l=db.insert(TableName, null, values);
        return l;
    }
    int updateData(final String TableName,final JSONObject jsonObject2) throws JSONException {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Iterator<String> iter = jsonObject2.keys();
        while(iter.hasNext()) {
            String key = iter.next();
            Log.d("ppdata",key+" "+jsonObject2.get(key));
            values.put(key, jsonObject2.get(key).toString());
        }
       // values.put("KEY_NAME", "hello");
        //values.put("KEY_PH_NO", "123");
        Log.d("ppdata"," Table Name "+TableName);
        int l=db.update(TableName,  values,"id=1 and name='Biswajit'",null);
        return l;
        /*
        String strSQL = "UPDATE myTable SET Column1 = someValue WHERE columnId = "+ someValue;

        myDataBase.execSQL(strSQL);
         */
    }

    public int geTotalItemCount(final String TableName) {
        String countQuery = "SELECT  * FROM " + TableName;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();

        // return count
        return count;
    }
     public int getLastId(final String TableName,String orderbycoluml) {
         String selectQuery = "SELECT  * FROM " + TableName;
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);
         cursor.moveToLast();
         return Integer.parseInt(cursor.getString(0));
    }
    public Cursor getAllRow(final String TableName,String orderbycoluml) {

         String selectQuery = "SELECT  * FROM " + TableName;
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    // code to update the single contact
    public int updateData(String TableName,Map<String,String> map,Map<String,String> map2) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] condition=new String[map2.size()];
        ContentValues values = new ContentValues();
        for(Map.Entry m:map.entrySet()){
            values.put(m.getKey().toString(), m.getValue().toString());
            //System.out.println(m.getKey()+" "+m.getValue());
        }
        String temp="",temp2="";
        int i=0;
        for(Map.Entry m:map2.entrySet()){
            temp+=""+m.getKey().toString()+" = ? AND ";
            condition[i]=m.getValue().toString();
            i++;
            temp2+="\""+m.getValue().toString()+"\",";
            //values.put(m.getKey().toString(), m.getValue().toString());
            //System.out.println(m.getKey()+" "+m.getValue());
        }

        StringBuffer sb= new StringBuffer(temp);
        //if (map.size()>2)
        sb.deleteCharAt(sb.lastIndexOf("A"));
        sb.deleteCharAt(sb.lastIndexOf("N"));
        sb.deleteCharAt(sb.lastIndexOf("D"));
        StringBuffer sb2= new StringBuffer(temp2);
        sb2.deleteCharAt(sb2.lastIndexOf(","));

        /*values.put("","");
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());*/
        Log.d("testdata","Kol\t"+sb+" UPDATE CHECK : "+sb2);
        // updating row

        return db.update(TableName, values, sb.toString(),condition);
        //Write whatever to want to do after delay specified (1 sec)
    }
    // Deleting single contact
    public int deleteData(String TableName,Map<String,String> map2) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] condition=new String[map2.size()];
        String temp="";
        int i=0;
        for(Map.Entry m:map2.entrySet())
        {
            temp+=""+m.getKey().toString()+" = ? AND ";
            condition[i]=m.getValue().toString();
            i++;
        }
        StringBuffer sb= new StringBuffer(temp);
        sb.deleteCharAt(sb.lastIndexOf("A"));
        sb.deleteCharAt(sb.lastIndexOf("N"));
        sb.deleteCharAt(sb.lastIndexOf("D"));
        int l=db.delete(TableName, sb.toString(),condition);
        db.close();
        return l;
    }
 // Deleting single contact
    public int deleteDataAll(String TableName) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TableName);
       return 1;
    }
// Get All Data
    public Cursor getAllData(String TableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Map<String,String> allMap=new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TableName;
        Cursor cursor = db.rawQuery(selectQuery, null);
        /*String[] columnNames = cursor.getColumnNames();
        int i=0;
        if (cursor != null)
        if (cursor.moveToFirst()) {
            do {
                if (i==0)
                for (i=0;i<=(columnNames.length-1);i++){
                    allMap.put(columnNames[i],cursor.getString(i));
                    Log.d("aaaaRRR11",columnNames[i]+"\t"+cursor.getString(i)+"\t"+i);
                }

               *//* if (i<=(columnNames.length-1)){
                    allMap.put(columnNames[i],cursor.getString(i));
                    Log.d("aaaaRRR22",columnNames[i]+"\t"+cursor.getString(i)+" "+i);
                    i++;
                }*//*
               *//* contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);*//*
                Log.d("aaaaRRR11","-------------------------");
            } while (cursor.moveToNext());
        }*/
        return cursor;
    }

    // Get selected  Data
    public Cursor getSelectedData(String TableName,Map<String,String> map2) {
        SQLiteDatabase db = this.getWritableDatabase();
        Map<String,String> allMap=new HashMap<String,String>();

        String temp="";

        for(Map.Entry m:map2.entrySet())
        {
            temp+=""+m.getKey().toString()+" = '"+m.getValue().toString().trim()+"' AND ";

        }
        StringBuffer sb= new StringBuffer(temp);
        sb.deleteCharAt(sb.lastIndexOf("A"));
        sb.deleteCharAt(sb.lastIndexOf("N"));
        sb.deleteCharAt(sb.lastIndexOf("D"));

        //db.rawQuery("SELECT * FROM myTable WHERE id = 4  ", null);
        String selectQuery="SELECT * FROM myTable WHERE name = 'Biswajit1'  ";
        //String selectQuery = "SELECT  * FROM " + TableName+" WHERE "+sb.toString()+"";
        Log.d("testdata","A : "+selectQuery);
                Cursor cursor = db.rawQuery(selectQuery, null);
        /*String[] columnNames = cursor.getColumnNames();
        int i=0;
        if (cursor != null)
            if (cursor.moveToFirst()) {
                do {

                    for (i=0;i<=(columnNames.length-1);i++){
                        allMap.put(columnNames[i],cursor.getString(i));
                        Log.d("aaaaRRR",columnNames[i]+"\n"+cursor.getString(i));
                        }
               *//* contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);*//*
                } while (cursor.moveToNext());
            }*/
        return cursor;
    }
}
