package com.mao.myxiuxiu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 毛麒添 on 2016/8/8 0008.
 */
public class XiuYiXiu extends View {
    //定义屏幕长宽高和半径
    private int width;
    private int height;
    private int raudis;
    private Bitmap mBitmap;
    private Paint paint;

    private int bitmapWidth;
    private int bitmapHeight;

    private List<Integer>radiuss=new ArrayList<>();//放置绘制出来的波
    private long current=System.currentTimeMillis();//获得当前时间

    //开启handle进行重复绘制
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
             invalidate();//刷新的方法
            //隔一段时间添加一个波
            if(System.currentTimeMillis()-current>500){
                radiuss.add(bitmapWidth/2);
                current=System.currentTimeMillis();//当前时间重新赋值刷新
            }
            //改变半径的值
            for (int i = 0; i < radiuss.size(); i++) {
                radiuss.set(i,radiuss.get(i)+4);//不断的改变半径
            }
            //性能优化，控制波的数量(迭代器)
            Iterator<Integer> iterator= radiuss.iterator();
            while(iterator.hasNext()){
                Integer next=iterator.next();//获取这个值
                if(next>=width/2){//大于手机屏幕一半并且包含则移除
                    if(radiuss.contains(next)){
                        iterator.remove();//使用迭代器来移除（否则有异常）
                    }

                }
            }
            handler.postDelayed(runnable,16);
        }
    };
    public XiuYiXiu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.a);
        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#155c7c"));
        bitmapWidth=mBitmap.getWidth();
        bitmapHeight=mBitmap.getHeight();
        raudis=bitmapWidth/2;
        radiuss.add(raudis);
    }


    protected void onDraw(Canvas canvas) {
        width=canvas.getWidth();
        height=canvas.getHeight();
        //圈圈的坐标
        int left=width/2-bitmapWidth/2;
        int top=height/2-bitmapHeight/2;


        //先把圈圈画上去
        //绘制波纹，同时改变透明度
        for (int i = 0; i < radiuss.size(); i++) {
            int r=radiuss.get(i);//绘制的半径
            paint.setAlpha(177-177*(r-bitmapWidth/2)/((width-bitmapWidth)/2));//根据半径来让透明度变化
            canvas.drawCircle(width/2,height/2,r,paint);
        }

        canvas.drawBitmap(mBitmap,left,top,null);
        super.onDraw(canvas);
    }
    public void onStart(){
        handler.post(runnable);
    }
}
