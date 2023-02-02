package com.tx.catcat.CustomView;

import android.content.AttributionSource;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.tx.catcat.Activity.BaseActivity;
import com.tx.catcat.Activity.DoingQuestionnaire;
import com.tx.catcat.util.QuestionnaireInterface;

public class LocateTurret extends View {

    Paint paint=new Paint();
    Bitmap turret;
    BitmapFactory.Options options=new BitmapFactory.Options();
    Canvas canvas;
    public String XY="";
    int high=0;
    int width=0;
    int Bithigh=0;
    int Bitwidth=0;
    public LocateTurret(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 给炮台设置图片和设置canvas
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
            this.canvas = canvas;
            canvas.drawBitmap(turret, x, y, paint);
        }catch (Exception e){
            BaseActivity.exception=BaseActivity.exception+"\r\n"+e.getMessage();
            StackTraceElement[] stackTrace = e.getStackTrace();
            for(StackTraceElement s:stackTrace){
                BaseActivity.exception=BaseActivity.exception+"\r\n"+s;
            }
            DoingQuestionnaire.isbug=true;
            BaseActivity.DestoryActivity(DoingQuestionnaire.class.getSimpleName());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setbitmap();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=w;
        high=h;
    }

    Integer x=0;
    Integer y=0;
    /**
     * 当用户摸这个view时就将定位的坐标保存
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int act = event.getAction();
         x=(int)event.getX();
         y=(int)event.getY();
        switch (act) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                draw(canvas);
                XY="x:"+(x-width/2+Bitwidth/2)+"\r\n"+"y:"+((y-high/2+Bithigh/2)*-1);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 获取炮台的图片路径
     */
    public void setbitmap(){
        turret=BitmapFactory.decodeFile(QuestionnaireInterface.getTurretPath(),options);
        Bitwidth=options.outWidth;
        Bithigh=options.outHeight;
    }
}
