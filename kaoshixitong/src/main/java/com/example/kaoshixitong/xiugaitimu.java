package com.example.kaoshixitong;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.kaoshixitong.R.id.spinner;

public class xiugaitimu extends AppCompatActivity {
    private List<Map<String,Object>> timulist;
    public shujuku dbhelper;
    private ListView sclv;
    public EditText cxgjc;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//保持界面不被压缩
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_xiugaitimu);dbhelper = new shujuku(this, "tiku.db", null, 1);
        dbhelper.getWritableDatabase();//建立数据库

        sclv= (ListView) findViewById(R.id.xgchaxunlistview);
        cxgjc= (EditText) findViewById(R.id.xgeditText);

        Button cx= (Button) findViewById(R.id.xgchaxun);
        cx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timulist=new ArrayList<Map<String,Object>>();
                SimpleAdapter sim=new SimpleAdapter(xiugaitimu.this,gettimuliat(), R.layout.layout,new String[]{"tg","cxa","cxb","cxc","cxd","cxaq"},new int[]{R.id.cxtigan, R.id.cxxuanxiang_a,R.id.cxxuanxiang_b,R.id.cxxuanxiang_c,
                        R.id.cxxuanxiang_d,R.id.cxxuanxiang_zq,});
                sclv.setAdapter(sim);
            }
        });
        sclv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
          
                //此处实现弹出对话框
                LayoutInflater inflater=LayoutInflater.from(xiugaitimu.this);
                View view1=inflater.inflate(R.layout.xiugai,null);
                AlertDialog.Builder bu=new AlertDialog.Builder(xiugaitimu.this);
                bu.setTitle("修改");//设置比标题
                bu.setView(view1);
                
                //spinner******************************************************************
                final String[] xx_zq = new String[1];
                Spinner spinner= (Spinner) view1.findViewById(R.id.xgspinner);
                data_list = new ArrayList<String>();
                data_list.add("A");
                data_list.add("B");
                data_list.add("C");
                data_list.add("D");
                //适配器
                arr_adapter= new ArrayAdapter<String>(xiugaitimu.this, android.R.layout.simple_spinner_item, data_list);
                //设置样式
                arr_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);//样式为单选按钮
                //加载适配器
                spinner.setAdapter(arr_adapter);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        xx_zq[0] =arr_adapter.getItem(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //************************************************************************
             

                final EditText xgxx_a= (EditText) view1.findViewById(R.id.xgxuanxiang_a);
                final EditText xgxx_b= (EditText) view1.findViewById(R.id.xgxuanxiang_b);
                final EditText xgxx_c= (EditText) view1.findViewById(R.id.xgxuanxiang_c);
                final EditText xgxx_d= (EditText) view1.findViewById(R.id.xgxuanxiang_d);
                final EditText xgtg= (EditText) view1.findViewById(R.id.xgtigan);
                spinner = (Spinner) view1.findViewById(R.id.xgspinner);
                String daan=timulist.get(position).get("cxaq").toString();
                String[] dana1= new String[] {"A","B","C","D"};
                int a = 0;
                for(int i=0;i<4;i++){
                    if(dana1[i].equals(daan))
                    {
                        a=i;
                    }
                }
                spinner.setSelection(a,true);
                final String tgbc;
                tgbc=timulist.get(position).get("tg").toString();
                xgtg.setText(timulist.get(position).get("tg").toString());
                xgxx_a.setText(timulist.get(position).get("cxa").toString());
                xgxx_b.setText(timulist.get(position).get("cxb").toString());
                xgxx_c.setText(timulist.get(position).get("cxc").toString());
                xgxx_d.setText(timulist.get(position).get("cxd").toString());
                
                
                bu.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        if(!xgxx_a.getText().toString().equals("") && !xgxx_b.getText().toString().equals("") && !xgxx_c.getText().toString().equals("") && !xgxx_d.getText().toString().equals("")
                                && !xgtg.getText().toString().equals("")){
                            SQLiteDatabase db=dbhelper.getWritableDatabase();
                            ContentValues values=new ContentValues();
                            values.put("tigan",xgtg.getText().toString());
                            values.put("daan_a",xgxx_a.getText().toString());
                            values.put("daan_b",xgxx_b.getText().toString());
                            values.put("daan_c",xgxx_c.getText().toString());
                            values.put("daan_d",xgxx_d.getText().toString());
                            values.put("dana_zq",xx_zq[0]);
                            db.update("tiku",values,"tigan=?",new String[]{tgbc});
                            Toast.makeText(xiugaitimu.this,"题目修改成功",Toast.LENGTH_SHORT).show();
                            //修改删除后的数据
                            timulist=new ArrayList<Map<String,Object>>();
                            SimpleAdapter sim=new SimpleAdapter(xiugaitimu.this,gettimuliat(), R.layout.layout,new String[]{"tg","cxa","cxb","cxc","cxd","cxaq"},new int[]{R.id.cxtigan, R.id.cxxuanxiang_a,R.id.cxxuanxiang_b,R.id.cxxuanxiang_c,
                                    R.id.cxxuanxiang_d,R.id.cxxuanxiang_zq,});
                            sclv.setAdapter(sim);
                            
                        }
                        else{
                            Toast.makeText(xiugaitimu.this,"请将空格处完善",Toast.LENGTH_SHORT).show();
                        }
                    }
                        
                    
                });
                bu.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = bu.create();//获取一个dialog
                dialog.show();//显示dialog

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
