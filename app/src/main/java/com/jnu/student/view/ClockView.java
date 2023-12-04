package com.jnu.student.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;

import com.jnu.student.R;

import java.util.Calendar;

public class ClockView extends View {
    private Bitmap hourHandBitmap;
    private Bitmap minuteHandBitmap;
    private Bitmap secondHandBitmap;

    private Matrix hourHandMatrix;
    private Matrix minuteHandMatrix;
    private Matrix secondHandMatrix;

    private int centerX;
    private int centerY;

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 加载时针、分针和秒针的图片资源
        hourHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hour_hand);
        minuteHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.minute_hand);
        secondHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second_hand);

        hourHandMatrix = new Matrix();
        minuteHandMatrix = new Matrix();
        secondHandMatrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 计算时钟中心点的坐标
        centerX = w / 2;
        centerY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        // 计算时针、分针和秒针的旋转角度
        float hourRotation = (hour + minute / 60f) * 30f;
        float minuteRotation = (minute + second / 60f) * 6f;
        float secondRotation = second * 6f;

        // 设置旋转角度
        hourHandMatrix.setRotate(hourRotation, hourHandBitmap.getWidth() / 2, hourHandBitmap.getHeight());
        minuteHandMatrix.setRotate(minuteRotation, minuteHandBitmap.getWidth() / 2, minuteHandBitmap.getHeight());
        secondHandMatrix.setRotate(secondRotation, secondHandBitmap.getWidth() / 2, secondHandBitmap.getHeight());

        // 绘制时针、分针和秒针
        canvas.drawBitmap(hourHandBitmap, hourHandMatrix, null);
        canvas.drawBitmap(minuteHandBitmap, minuteHandMatrix, null);
        canvas.drawBitmap(secondHandBitmap, secondHandMatrix, null);

        // 重绘视图
        invalidate();
    }
}