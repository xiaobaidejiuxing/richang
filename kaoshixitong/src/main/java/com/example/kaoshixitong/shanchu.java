package com.example.kaoshixitong;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class shanchu extends AppCompatActivity {
    private List<Map<String,Object>> timulist;
    public shujuku dbhelper;
    private ListView sclv;
    public EditText cxgjc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//保持界面不被压缩
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_shanchu);dbhelper = new shujuku(this, "tiku.db", null, 1);
        dbhelper.getWritableDatabase();//建立数据库

        sclv= (ListView) findViewById(R.id.scchaxunlistview);
        cxgjc= (EditText) findViewById(R.id.sceditText);
        
        Button cx= (Button) findViewById(R.id.scchaxun);
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timulist=new ArrayList<Map<String,Object>>();
                SimpleAdapter sim=new SimpleAdapter(shanchu.this,gettimuliat(), R.layout.layout,new String[]{"tg","cxa","cxb","cxc","cxd","cxaq"},new int[]{R.id.cxtigan, R.id.cxxuanxiang_a,R.id.cxxuanxiang_b,R.id.cxxuanxiang_c,
                        R.id.cxxuanxiang_d,R.id.cxxuanxiang_zq,});
                sclv.setAdapter(sim);
            }
        });
        sclv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                
                AlertDialog.Builder builder=new AlertDialog.Builder(shanchu.this);
                builder.setMessage("确认删除这道题目吗？");
                builder.setTitle("提示");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {                                        
                        
                        SQLiteDatabase db=dbhelper.getWritableDatabase();
                        db.delete("tiku","tigan=?", new String[]{timulist.get(position).get("tg").toString()});
                        
                       //获取删除后的数据
                        timulist=new ArrayList<Map<String,Object>>();
                        SimpleAdapter sim=new SimpleAdapter(shanchu.this,gettimuliat(), R.layout.layout,new String[]{"tg","cxa","cxb","cxc","cxd","cxaq"},new int[]{R.id.cxtigan, R.id.cxxuanxiang_a,R.id.cxxuanxiang_b,R.id.cxxuanxiang_c,
                                R.id.cxxuanxiang_d,R.id.cxxuanxiang_zq,});
                        sclv.setAdapter(sim);
                        

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });
    }


    //按条件查询的数据生成
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
