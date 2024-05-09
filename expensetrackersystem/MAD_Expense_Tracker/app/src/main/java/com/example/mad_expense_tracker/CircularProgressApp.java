package com.example.mad_expense_tracker; // Replace with your actual package name
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CircularProgressApp extends View {

    private int progress = 0;
    private int maxProgress = 100;
    private int strokeWidth = 20;
    private int startAngle = -90;
    private int sweepAngle = 0;
    private int backgroundColor = Color.GRAY;
    private int progressColor = Color.GREEN;

    private RectF rectF;
    private Paint backgroundPaint;
    private Paint progressPaint;

    public CircularProgressApp(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews();
    }

    private void initializeViews() {
        rectF = new RectF();
        backgroundPaint = new Paint();
        progressPaint = new Paint();

        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(strokeWidth);
        backgroundPaint.setColor(backgroundColor);

        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        progressPaint.setColor(progressColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(centerX, centerY) - strokeWidth;

        rectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        canvas.drawCircle(centerX, centerY, radius, backgroundPaint);

        sweepAngle = (int) ((float) progress / maxProgress * 360);
        canvas.drawArc(rectF, startAngle, sweepAngle, false, progressPaint);
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
        initializeViews();
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        backgroundPaint.setColor(backgroundColor);
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        progressPaint.setColor(progressColor);
        invalidate();
    }
}