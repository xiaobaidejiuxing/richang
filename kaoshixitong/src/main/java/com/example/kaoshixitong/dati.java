package com.example.kaoshixitong;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaoshixitong.tool.MathTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dati extends AppCompatActivity {
    public TextView kaoshengname;
    public TextView dangqian;
    public TextView timu;
    private shujuku dbhelp;
    public  int xianshishuliang=0;
    public String[] xx_zq = new String[1];
    public String[] xx_zq1 = new String[30];
    public TextView tg;
    public TextView xxa;
    public TextView xxb;
    public TextView xxc;
    public TextView xxd;
    public Button xiyiti;
    int datishuliang;
    ArrayList<String> xxzqid = new ArrayList();
    int dqsl;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    public Spinner sp;
    public  String[][] a=new String[30][6];
     public  int zongfenshu=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dati);

        dbhelp=new shujuku(this,"tiku.db",null,1);
        dbhelp.getWritableDatabase();//建立数据库
        
        Intent intent=getIntent();
        String shuliang=intent.getStringExtra("timushuliang");
        String name=intent.getStringExtra("kaosheng");
        
        dqsl=1;
        datishuliang=Integer.parseInt(shuliang);
        
        timu= (TextView) findViewById(R.id.timuxingxi);
        dangqian= (TextView) findViewById(R.id.danqian);
        kaoshengname= (TextView) findViewById(R.id.kaosheng);
        kaoshengname.setText(name);
        dangqian.setText("/当前是第"+dqsl+"道题");
        timu.setText("一共有"+datishuliang+"道题");
        
        //所有题目信息保存进二维数组
        
        ArrayList<String> id = new ArrayList();
        
        SQLiteDatabase db=dbhelp.getWritableDatabase();
        Cursor cursor=db.query("tiku",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(cursor.getColumnIndex("tigan"))!=""){
                  id.add(cursor.getString(cursor.getColumnIndex("tigan")));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        List<Integer> indexs = MathTool.getRandomIndexs(datishuliang, id.size()) ;
        ArrayList<String> timuid = new ArrayList();
        //保存题目id的数组随机抽取id加入题目的二维数组里
        //获取到了符合抽取数量的去除空值的题目id
        for(int i = 0; i < indexs.size(); i++){
            timuid.add(id.get(i));
        }

        //查询数据库，将题目信息保存进二维数组
        for (int c=0;c<timuid.size();c++)
        {
            Cursor cursor1=db.query("tiku",null,"tigan like?",new String[]{timuid.get(c)},null,null,null);
            if(cursor1.moveToNext()){
                a[c][0]=cursor1.getString(cursor1.getColumnIndex("tigan"));
                a[c][1]=cursor1.getString(cursor1.getColumnIndex("daan_a"));
                a[c][2]=cursor1.getString(cursor1.getColumnIndex("daan_b"));
                a[c][3]=cursor1.getString(cursor1.getColumnIndex("daan_c"));
                a[c][4]=cursor1.getString(cursor1.getColumnIndex("daan_d"));
                a[c][5]=cursor1.getString(cursor1.getColumnIndex("dana_zq"));
            }
            cursor1.close();
    
        }
         tg= (TextView) findViewById(R.id.dati_tigan);
         xxa= (TextView) findViewById(R.id.dtxuanxiang_a);
        xxb= (TextView) findViewById(R.id.dtxuanxiang_b);
        xxc= (TextView) findViewById(R.id.dtxuanxiang_c);
        xxd= (TextView) findViewById(R.id.dtxuanxiang_d);
        sp= (Spinner) findViewById(R.id.dtspinner);
        xianshi();

       xiyiti= (Button) findViewById(R.id.button7);
        Button shangyiti= (Button) findViewById(R.id.button6);
        if(dqsl==datishuliang){
            xiyiti.setText("提交");
        }
        
        xiyiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dqsl=dqsl+1;
                dangqian.setText("/当前是第"+dqsl+"道题");
                if(dqsl>datishuliang){
                    fenshu(1);
                    new AlertDialog.Builder(dati.this).setTitle("得分情况")//设置对话框标题
                            .setMessage("您的得分是"+zongfenshu+"分")//设置显示的内容
                            .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                    finish();
                                }
                            }).show();//在按键响应事件中显示此对话框
                    
                }else {
                    xianshishuliang=xianshishuliang+1;
                    xx_zq1[dqsl-2]=xx_zq[0];

                    if(dqsl==datishuliang){
                        xiyiti.setText("提交");
                    }
                    xianshi();
                    
                    /*for(int d=0;d<xx_zq1.length;d++){
                        Log.d("dana",xx_zq1[d]);
                    }*/
                    
                }
                
            }
        });
        
        shangyiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(dqsl>1 && dqsl<=datishuliang ){
                    dqsl=dqsl-1;
                    dangqian.setText("/当前是第"+dqsl+"道题");
                    xianshishuliang=xianshishuliang-1;
                    xiyiti.setText("下一题");
                    xianshi();
                }
                else if(dqsl==1){
                    Toast.makeText(dati.this,"现在是第一题",Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        

    }
    public void xianshi(){
        tg.setText(a[xianshishuliang][0]);
        xxa.setText(a[xianshishuliang][1]);
        xxb.setText(a[xianshishuliang][2]);
        xxc.setText(a[xianshishuliang][3]);
        xxd.setText(a[xianshishuliang][4]);
        sp.setSelection(0,true);

        
        data_list = new ArrayList<String>();
        data_list.add("A");
        data_list.add("B");
        data_list.add("C");
        data_list.add("D");
        //适配器
        arr_adapter= new ArrayAdapter<String>(dati.this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);//样式为单选按钮
        //加载适配器
        sp.setAdapter(arr_adapter);

        
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                xx_zq[0] =arr_adapter.getItem(position).toString();
                xx_zq1[xianshishuliang]=xx_zq[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        
        
        //通过一个数组保存选的答案
        xx_zq1[xianshishuliang]=xx_zq[0];
        
        
    }
    
    public int fenshu(int dqid){
        
        for(int i=0;i<datishuliang;i++)
        {
            if(a[i][5].equals(xx_zq1[i]))
            {
                zongfenshu=zongfenshu+1;
            }
        }
        
        return zongfenshu;
    }

}

