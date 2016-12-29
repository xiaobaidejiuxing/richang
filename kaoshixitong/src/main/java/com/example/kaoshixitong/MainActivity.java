package com.example.kaoshixitong;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private shujuku dbhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Button tiku= (Button) findViewById(R.id.tiku);
        Button dati= (Button) findViewById(R.id.dati);
        tongzhi();

        dbhelp=new shujuku(this,"tiku.db",null,1);
        dbhelp.getWritableDatabase();//建立数据库





        tiku.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               showdialog();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
            }
        });
        dati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dati();
            }
        });


    }
    
    private void tongzhi(){
        NotificationManager manager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("小白题库")
                .setContentText("程序在后台运行")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.tubiaso1)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.tubiaso1))
                .build();
        manager.notify(1,notification);
    }
    
 
    private  void showdialog(){
        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.denglulog,null);
        final AlertDialog.Builder bu=new AlertDialog.Builder(this);
        bu.setTitle("登陆");//设置比标题
        bu.setIcon(R.drawable.tubiaso1);//设置图标
        bu.setView(view);
         final EditText password1=(EditText) view.findViewById(R.id.password);//获取自定义布局里的edittext数值必须在findviewbyid前加view
         final EditText username1= (EditText) view.findViewById(R.id.dati_username);
         final CheckBox rember= (CheckBox) view.findViewById(R.id.checkBox);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isrember=pref.getBoolean("rember_password",false);
        if(isrember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            username1.setText(account);
            password1.setText(password);
            rember.setChecked(true);
        }
        bu.setPositiveButton("登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name=username1.getText().toString();
                String mima=password1.getText().toString();
                if(name.equals("admin") && mima.equals("123456")){
                    editor=pref.edit();
                    if(rember.isChecked()){
                        editor.putBoolean("rember_password",true);
                        editor.putString("account",name);
                        editor.putString("password",mima);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent inter=new Intent(MainActivity.this,tikuguanli.class);
                    startActivity(inter);
                }else {
                    Toast.makeText(MainActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
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
    
    public void dati(){

        LayoutInflater inflater=LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.datilog,null);
        AlertDialog.Builder bu=new AlertDialog.Builder(this);
        bu.setTitle("考生登陆");//设置比标题
        bu.setIcon(R.drawable.tubiaso1);//设置图标
        bu.setView(view);
        final EditText shuliang=(EditText) view.findViewById(R.id.dati_shuliang);//获取自定义布局里的edittext数值必须在findviewbyid前加view
        final EditText dati_name= (EditText) view.findViewById(R.id.dati_username);
        bu.setPositiveButton("登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int sl=0;
                int sl2=0;
                String datiname=dati_name.getText().toString();
                String datishuliang=shuliang.getText().toString();
                if(!datiname.equals("") && !datishuliang.equals(""))
                {
                    SQLiteDatabase db=dbhelp.getWritableDatabase();
                    Cursor cursor=db.query("tiku",null,null,null,null,null,null);
                    if(cursor.moveToFirst()){
                        do{
                            if(cursor.getString(cursor.getColumnIndex("tigan"))!=""){
                                sl++;
                            }
                        }while (cursor.moveToNext());
                    }
                    cursor.close();
                    sl2=Integer.parseInt(datishuliang);
                    if(sl2>sl){
                        Toast.makeText(MainActivity.this,"题库数量不足，请重新输入",Toast.LENGTH_SHORT).show();
                        shuliang.setText("");
                    }
                    else if(sl2>=1 && sl2<=30){
                        Intent inter=new Intent(MainActivity.this,dati.class);
                        inter.putExtra("kaosheng",datiname);
                        inter.putExtra("timushuliang",datishuliang);
                        startActivity(inter);
                    }
                    else {
                        Toast.makeText(MainActivity.this,"题目数量要小于30题且大于0题",Toast.LENGTH_SHORT).show();
                        shuliang.setText("");
                    }

                }
                else {
                    Toast.makeText(MainActivity.this,"用户名或答题数量为空",Toast.LENGTH_SHORT).show();
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
  
  


    
}
