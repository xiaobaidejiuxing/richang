package com.example.intent1230;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //显式intent
        Button bt= (Button) findViewById(R.id.button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,secondactivity.class);
                startActivity(intent);
            }
        });
        //隐式intent
        Button bt1= (Button) findViewById(R.id.button2);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.activitytest.ACTION_START");
                intent.addCategory("com.example.activitytest.MY_CYTEGORY");
                startActivity(intent);
            }
        });
        //隐式启动浏览器网页
        Button bt2= (Button) findViewById(R.id.button3);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivity(intent);
            }
        });
        //隐式启动拨号界面  
        Button bt3= (Button) findViewById(R.id.button4);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:10086"));
                startActivity(intent);
            }
        });
        //向下一个活动传值
        Button bt4= (Button) findViewById(R.id.button5);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,secondactivity.class);
                String a="从上一个活动传的值";
                intent.putExtra("cd",a);
                startActivity(intent);
            }
        });
        //返回数据给上一个活动
        Button bt5= (Button) findViewById(R.id.button6);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,secondactivity.class);
                startActivityForResult(intent,1);
            }
        });
        
    }
    
    //重写方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnedDate=data.getStringExtra("fhsz");
                    Toast.makeText(MainActivity.this,returnedDate,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
        
    }
}
