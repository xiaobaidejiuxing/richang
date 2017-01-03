package com.example.cehua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public ListView lv;
    private List<Map<String,Object>> shuzhilist;
    public Button button;
    public TextView tv;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        button= (Button) findViewById(R.id.ceshi);
        lv= (ListView) findViewById(R.id.cehua1);
        tv= (TextView) findViewById(R.id.tv);
        shuzhilist=new ArrayList<Map<String,Object>>();
        
        SimpleAdapter simpleAdapter=new SimpleAdapter(MainActivity.this,getshuzhi(), R.layout.cehuaceshi,new String[]{"tv"}, new int[]{R.id.tv});
        lv.setAdapter(simpleAdapter);
        
        
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,shuzhilist.get(position).get("tv").toString(),Toast.LENGTH_SHORT).show();
            }
        });
        
        
        
    }

    private List<Map<String,Object>> getshuzhi(){
       
        for(int i=0;i<30;i++){
            Map<String,Object> shuzhi=new HashMap<String,Object>();
            shuzhi.put("tv",String.valueOf(i));
            shuzhilist.add(shuzhi);
        }
        return shuzhilist;
    }
}
