package com.shivam.menu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class databaseHelper extends SQLiteOpenHelper {

    public static final String DB_name="JustForFun.db";
    public static final String tab1_name="SignUp";
    public static final String tab1_attr1="ID";
    public static final String tab1_attr2="Name";
    public static final String tab1_attr3="Email";
    public static final String tab1_attr4="Password";
    public static final String tab1_attr5="Mob_No";


    public databaseHelper(@Nullable Context context) {
        super(context, DB_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tab1_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT, Email TEXT," +
                "Password TEXT,Mob_No TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+ tab1_name);
        onCreate(db);
    }

    public boolean insertdata(String name, String email, String pass, String mob)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(tab1_attr2,name);
        contentValues.put(tab1_attr3,email);
        contentValues.put(tab1_attr4,pass);
        contentValues.put(tab1_attr5,mob);
        long result=db.insert(tab1_name,null,contentValues);

        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+tab1_name, null);
        return res;
    }

    public void updateData(String id, String name, String email, String pass, String mob)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(tab1_attr1,id);
        values.put(tab1_attr2,name);
        values.put(tab1_attr3,email);
        values.put(tab1_attr4,pass);
        values.put(tab1_attr5,mob);
        db.update(tab1_name,values,"ID = ?",new String[]{id});
    }

    public Integer deleteData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(tab1_name,"ID = ?",new String[]{id});

    }
}
