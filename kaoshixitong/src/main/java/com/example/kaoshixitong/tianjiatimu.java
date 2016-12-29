package com.example.kaoshixitong;

import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.kaoshixitong.R.id.spinner;

public class tianjiatimu extends AppCompatActivity {
    public Spinner sp;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    public shujuku dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);//保持界面不被压缩
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tianjiatimu);

        dbhelper=new shujuku(this,"tiku.db",null,1);
        dbhelper.getWritableDatabase();//建立数据库
  
        //获取正确选项
        final String[] xx_zq = new String[1];
        sp= (Spinner) findViewById(spinner);
        data_list = new ArrayList<String>();
        data_list.add("A");
        data_list.add("B");
        data_list.add("C");
        data_list.add("D");
        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);//样式为单选按钮
        //加载适配器
        sp.setAdapter(arr_adapter);
        
       
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xx_zq[0] =arr_adapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //***************************************************************************************//
        
        final EditText xx_a= (EditText) findViewById(R.id.xuanxiang_a);
        final EditText xx_b= (EditText) findViewById(R.id.xuanxiang_b);
        final EditText xx_c= (EditText) findViewById(R.id.xuanxiang_c);
        final EditText xx_d= (EditText) findViewById(R.id.xuanxiang_d);
        final EditText tg= (EditText) findViewById(R.id.tigan);

        Button bc= (Button) findViewById(R.id.baocun);
        bc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!xx_a.getText().toString().equals("") && !xx_b.getText().toString().equals("") && !xx_c.getText().toString().equals("") && !xx_d.getText().toString().equals("")
                        && !tg.getText().toString().equals("")){
                    SQLiteDatabase db=dbhelper.getWritableDatabase();
                    ContentValues values=new ContentValues();
                    values.put("tigan",tg.getText().toString());
                    values.put("daan_a",xx_a.getText().toString());
                    values.put("daan_b",xx_b.getText().toString());
                    values.put("daan_c",xx_c.getText().toString());
                    values.put("daan_d",xx_d.getText().toString());
                    values.put("dana_zq",xx_zq[0]);
                    db.insert("tiku",null,values);
                    Toast.makeText(tianjiatimu.this,"题目添加成功",Toast.LENGTH_SHORT).show();
                    xx_a.setText("");
                    xx_b.setText("");
                    xx_c.setText("");
                    xx_d.setText("");
                    tg.setText("");
                    
                }
                else{
                    Toast.makeText(tianjiatimu.this,"请将空格处完善",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button quxiao= (Button) findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("input keyevent " + KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
