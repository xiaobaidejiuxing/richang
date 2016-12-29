package com.example.kaoshixitong;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

import static com.example.kaoshixitong.R.id.time;

public class tikuguanli extends AppCompatActivity {
     public Button  bt_tianjian;
     public Button  bt_chaxun;
     public Button  bt_shanchu;
     public Button  bt_xiugai;
    public shujuku dbhelper;


    private boolean showDanmaku;

    private DanmakuView danmakuView;

    private DanmakuContext danmakuContext;

    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tikuguanli);

        dbhelper=new shujuku(this,"tiku.db",null,1);
        dbhelper.getWritableDatabase();//建立数据库
        
        bt_tianjian= (Button) findViewById(R.id.button_tianjia);
        bt_chaxun= (Button) findViewById(R.id.button_chaxun);
        bt_shanchu= (Button) findViewById(R.id.button_shanchu);
        bt_xiugai= (Button) findViewById(R.id.button_xiugai);
         
        bt_tianjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tianjia();
            }
        });
        
        bt_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chaxun();
            }
        });
        bt_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shanchu();
            }
        });
        bt_xiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xiugai();
            }
        });
        
        
        //********************************github弹幕实现方法*******************************//

        danmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
                danmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
    }



    /**
     * 向弹幕View中添加一条弹幕
     * @param content
     *          弹幕的具体内容
     * @param  withBorder
     *          弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.WHITE;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.GREEN;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        final String[] damu=new String[]{"第一","开发者是个逗逼","老戴是个gay","作者是脑残","本节目由GTA5翻车大队特约播出","作者是脑残","以上企业均已倒闭","前200",
                "干死黄旭东","后排给作者续一秒","来同学们，Crty c加Crty v","前500","自古前排出傻逼","看海杰，玩坦克，一发入魂好霸道","开发者长得帅","后排压压惊~~"};
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(showDanmaku) {
                    int time = new Random().nextInt(500);
                    int number=new Random().nextInt(15)+1;//生成1~15的随机数
                    addDanmaku(damu[number], false);
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    

    /**
     * sp转px的方法。
     */
    public int sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (danmakuView != null && danmakuView.isPrepared()) {
            danmakuView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
            danmakuView.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showDanmaku = false;
        if (danmakuView != null) {
            danmakuView.release();
            danmakuView = null;
        }
    }
        
        
    

    private void chaxun() {
        Intent intent=new Intent(tikuguanli.this,chaxuntimu.class);
        startActivity(intent);
    }

    public void tianjia(){
        Intent intenr=new Intent(tikuguanli.this,tianjiatimu.class);
        startActivity(intenr);
    }
    public void shanchu(){
        Intent intenr=new Intent(tikuguanli.this,shanchu.class);
        startActivity(intenr);
    }
    private void xiugai() {
        Intent inter=new Intent(tikuguanli.this,xiugaitimu.class);
        startActivity(inter);
    }
    
   
}
