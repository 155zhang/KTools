package com.jiangkang.ktools.effect;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.jiangkang.ktools.R;

import java.util.Locale;

/**
 * Created by jiangkang on 2017/10/14.
 */

public class ShapeView extends View {

    private Paint mPaint;

    private Paint mDividerPaint;

    public ShapeView(Context context) {
        super(context);
        init();
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        if (mPaint == null) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        if (mDividerPaint == null) {
            mDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDividerPaint.setColor(Color.parseColor("#00796B"));
        }
        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //mask
        canvas.drawColor(Color.parseColor("#12FF9800"));

        final int height = getMeasuredHeight();

        //line
        for (int y = 200; y < height; y = y + 200) {
            canvas.drawLine(0, y, getMeasuredWidth(), y, mDividerPaint);
        }

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.parseColor("#f44336"));

        //circle:50
        canvas.drawCircle(100, 100, 50, mPaint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawArc(200, 50, 300, 150, 30, 60, true, mPaint);
            canvas.drawArc(350, 50, 450, 150, 30, 60, false, mPaint);
        }

        //rect:250
        canvas.drawRect(50, 250, 150, 300, mPaint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(200, 250, 300, 300, 10, 10, mPaint);
        }

        //text:450
        mPaint.setTextSize(64);
        mPaint.setTextLocale(Locale.CHINA);
        canvas.drawText("Android", 50, 500, mPaint);

        //bitmap:650
        canvas.drawBitmap(BitmapFactory.decodeResource(this.getContext().getResources(), R.drawable.ic_device), 50, 650, mPaint);





    }


}
