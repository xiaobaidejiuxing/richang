package com.example.kaoshixitong;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;



public class shujuku extends SQLiteOpenHelper {

    public static final String create_tiku="create table tiku("+
            "id integer primary key autoincrement,"
            +"tigan text,"
            +"daan_a text,"
            +"daan_b text,"
            +"daan_c text,"
            +"daan_d text,"
            +"dana_zq text)";
      private Context mcontext;

    public shujuku(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext=context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_tiku);
        Log.d("数据库创建成功","1");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
