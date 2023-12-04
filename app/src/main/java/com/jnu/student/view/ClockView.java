package com.jnu.student.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class ClockView extends View {

    private Paint paint = new Paint();
    private RectF oval = new RectF();
    private float centerX;
    private float centerY;
    private float radius;
    private int hour;
    private int minute;
    private int second;

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
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int diameter = Math.min(width, height);

        centerX = width / 2;
        centerY = height / 2;
        radius = diameter / 2;

        setMeasuredDimension(diameter, diameter);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
        drawHands(canvas);
    }

    private void drawCircle(Canvas canvas) {
        oval.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(oval, 0, 360, true, paint);
    }

    private void drawHands(Canvas canvas) {
        float hourAngle = (hour * 30) + (minute / 60f) * 30;
        float minuteAngle = (minute * 6) + (second / 60f) * 6;
        float secondAngle = second * 6;

        float hourX = (float) (centerX + radius * Math.cos(Math.toRadians(hourAngle)));
        float hourY = (float) (centerY + radius * Math.sin(Math.toRadians(hourAngle)));
        canvas.drawLine(centerX, centerY, hourX, hourY, paint);

        float minuteX = (float) (centerX + radius * Math.cos(Math.toRadians(minuteAngle)));
        float minuteY = (float) (centerY + radius * Math.sin(Math.toRadians(minuteAngle)));
        canvas.drawLine(centerX, centerY, minuteX, minuteY, paint);

        float secondX = (float) (centerX + radius * Math.cos(Math.toRadians(secondAngle)));
        float secondY = (float) (centerY + radius * Math.sin(Math.toRadians(secondAngle)));
        canvas.drawLine(centerX, centerY, secondX, secondY, paint);
    }

    public void setTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        invalidate();
    }
}
