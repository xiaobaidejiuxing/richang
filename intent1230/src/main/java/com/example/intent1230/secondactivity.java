package com.example.intent1230;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class secondactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondactivity);
        final Intent intent=getIntent();
        String b=intent.getStringExtra("cd");
        Toast.makeText(secondactivity.this,b,Toast.LENGTH_SHORT).show();

        //通过按钮返回数据
        Button btfh= (Button) findViewById(R.id.button7);
        btfh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.putExtra("fhsz","通过按钮从上一个活动返回数据");
                setResult(RESULT_OK,intent1);
                finish();
            }
        });
      
        
    }
    //重写返回键的方法
    public void onBackPressed() {
    Intent intent1=new Intent();
    intent1.putExtra("fhsz","通过返回键从上一个活动返回数据");
    setResult(RESULT_OK,intent1);
    finish();
}
}
