package com.longcai.medical.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 *  Created by qly on 2016/6/22.
 *  将图片转化为圆角
 *  构造中第二个参数定义半径
 */
public class GlideRoundWithTopTransform extends BitmapTransformation {

    private float radius = 0f;
//    private float mBorderWidth;
//    private Paint mBorderPaint;

    public GlideRoundWithTopTransform(Context context) {
        this(context, 10);
    }

    public GlideRoundWithTopTransform(Context context, int radius) {
        super(context);
//        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
        this.radius = radius;
    }

//    public GlideRoundWithTopTransform(Context context, int borderWidth, int radius, int borderColor) {
//        super(context);
////        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
//        this.radius = radius;
////        mBorderWidth =  Resources.getSystem().getDisplayMetrics().density * borderWidth;
//        mBorderWidth = borderWidth;
//
//        mBorderPaint = new Paint();
//        mBorderPaint.setDither(true);
//        mBorderPaint.setAntiAlias(true);
//        mBorderPaint.setColor(borderColor);
//        mBorderPaint.setStyle(Paint.Style.STROKE);
//        mBorderPaint.setStrokeWidth(mBorderWidth);
//    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        canvas.drawRect(0, rectF.bottom - radius, radius, rectF.bottom, paint);
        canvas.drawRect(rectF.right - radius, rectF.bottom - radius, rectF.right, rectF.bottom, paint);
//        if (mBorderPaint != null) {
//            float borderRadius = radius - mBorderWidth / 2;
//            canvas.drawRoundRect(rectF, borderRadius, borderRadius, mBorderPaint);
//        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}