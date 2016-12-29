package com.example.kaoshixitong;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class chaxuntimu extends AppCompatActivity {
    private List<Map<String,Object>> timulist;
    public shujuku dbhelper;
    private ListView lv;
    public  EditText cxgjc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//保持界面不被压缩
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaxuntimu);

        dbhelper = new shujuku(this, "tiku.db", null, 1);
        dbhelper.getWritableDatabase();//建立数据库

        lv= (ListView) findViewById(R.id.chaxunlistview);
        cxgjc= (EditText) findViewById(R.id.editText);
        Button cx= (Button) findViewById(R.id.chaxun);
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timulist=new ArrayList<Map<String,Object>>();
                SimpleAdapter sim=new SimpleAdapter(chaxuntimu.this,gettimuliat(), R.layout.layout,new String[]{"tg","cxa","cxb","cxc","cxd","cxaq"},new int[]{R.id.cxtigan, R.id.cxxuanxiang_a,R.id.cxxuanxiang_b,R.id.cxxuanxiang_c,
                        R.id.cxxuanxiang_d,R.id.cxxuanxiang_zq,});
                lv.setAdapter(sim);
            }
        });
      

    }
    
    private List<Map<String,Object>> gettimuliat()
    {

        SQLiteDatabase db=dbhelper.getWritableDatabase();
        Cursor cursor=db.query("tiku",null,"tigan like?",new String[]{"%"+cxgjc.getText().toString()+"%"},null,null,null);
        if(cursor.moveToFirst()){
            do{
                Map<String,Object> timu=new HashMap<String,Object>();
                timu.put("tg",cursor.getString(cursor.getColumnIndex("tigan")));
                timu.put("cxa",cursor.getString(cursor.getColumnIndex("daan_a")));
                timu.put("cxb",cursor.getString(cursor.getColumnIndex("daan_b")));
                timu.put("cxc",cursor.getString(cursor.getColumnIndex("daan_c")));
                timu.put("cxd",cursor.getString(cursor.getColumnIndex("daan_d")));
                timu.put("cxaq",cursor.getString(cursor.getColumnIndex("dana_zq")));
                timulist.add(timu);

            }while (cursor.moveToNext());
        }
        cursor.close();

        return timulist;
    }
}
