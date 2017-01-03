package com.example.kongjian0103;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import static android.R.attr.progressBarStyleHorizontal;

public class MainActivity extends AppCompatActivity {
  private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        Button bt= (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progressBar.getVisibility()==View.GONE){
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        
        Button bt2= (Button) findViewById(R.id.button2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jiazai=progressBar.getProgress();
                jiazai=jiazai+10;
                progressBar.setProgress(jiazai);
            }
        });
        
        Button bt3= (Button) findViewById(R.id.button3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                //progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置为矩形进度条
                progressDialog.setTitle("this is ProgressDialog");
                progressDialog.setMessage("玩命更新中");
                progressDialog.setIndeterminate(false); // 设置进度条是否为不明确
                progressDialog.setCancelable(false);//设置ProgressDialog 是否可以按返回键取消
                progressDialog.setMax(100);
                progressDialog.show();

            //启动一个线程模拟更新
                new Thread(new Runnable() {
                    int i = 0;//初始下载进度
                    @Override
                    public void run() {
                        while(i<progressDialog.getMax()){//设置循环条件,小于progressDialog的最大值
                            progressDialog.setProgress(i=i+1);//setProgress(intCounter);更新进度条，当然一般都需要Handler的结合来更新进度条
                            
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();//进度完成时对话框消失
                    }
                }).start();
                
            }
        });
        
        
        Button bt5= (Button) findViewById(R.id.button5);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置为矩形进度条
                progressDialog.setTitle("this is ProgressDialog");
                progressDialog.setMessage("玩命更新中");
                progressDialog.setIndeterminate(true); // 设置进度条是否为不明确
                progressDialog.setCancelable(false);//设置ProgressDialog 是否可以按返回键取消
                progressDialog.setMax(100);
                progressDialog.show();

                //启动一个线程模拟更新
                new Thread(new Runnable() {
                    int i = 0;//初始下载进度
                    @Override
                    public void run() {
                        while(i<progressDialog.getMax()){//设置循环条件,小于progressDialog的最大值
                            progressDialog.setProgress(i=i+1);//setProgress(intCounter);更新进度条，当然一般都需要Handler的结合来更新进度条

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();//进度完成时对话框消失
                    }
                }).start();
                
            }
        });
        
        Button bt6= (Button) findViewById(R.id.button6);
        bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // 设置为矩形进度条
                progressDialog.setTitle("this is ProgressDialog");
                progressDialog.setMessage("玩命更新中");
                progressDialog.setIndeterminate(false); // 设置进度条是否为不明确
                progressDialog.setCancelable(false);//设置ProgressDialog 是否可以按返回键取消
                progressDialog.setMax(100);
                progressDialog.show();

                //启动一个线程模拟更新
                new Thread(new Runnable() {
                    int i = 0;//初始下载进度
                    @Override
                    public void run() {
                        while(i<progressDialog.getMax()){//设置循环条件,小于progressDialog的最大值
                            progressDialog.setProgress(i=i+1);//setProgress(intCounter);更新进度条，当然一般都需要Handler的结合来更新进度条

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        progressDialog.dismiss();//进度完成时对话框消失
                    }
                }).start();
                
            }
        });
        
        Button bt4= (Button) findViewById(R.id.button4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("this is dialog");
                builder.setMessage("something important");
                builder.setCancelable(false);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"OK",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,"Cancel",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                
            }
        });
        
        
    }
}
