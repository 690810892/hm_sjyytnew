package com.zysapp.sjyyt.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zysapp.sjyyt.activity.R;

import java.io.File;

import xtom.frame.XtomObject;
import xtom.frame.util.XtomBaseUtil;
import xtom.frame.util.XtomFileUtil;


public class MyDrawable extends Drawable {

    private Paint mPaint;//画笔
    private int mWidth;//图片宽与长度的最小值
    private int mRadius;//半径
    private int mRound;//圆角
    private RectF mRectF;//矩形
    private Bitmap mBitmap;//图片
    private Type mType = Type.TYPE_ROUND;//默认是矩形

    //设置类型
    enum Type{
        TYPE_ROUND, TYPE_CICLE;
    }

    public MyDrawable(Bitmap bitmap){
        this.mBitmap = bitmap;

        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        mWidth = Math.min(mBitmap.getWidth(), mBitmap.getHeight());
        mRadius = mWidth / 2;
    }

    /**
     * 向外提供设置图片类型的方法
     * @param type
     */
    public void setType(Type type){
        this.mType = type;
    }

    /**
     * 暴露给外面设置圆角的大小
     *
     * @param round
     */
    public void setRound(int round) {
        this.mRound = round;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
        mRectF = new RectF(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        if (mType == Type.TYPE_ROUND) {
            canvas.drawRoundRect(mRectF, mRound, mRound, mPaint);
        }else {
            canvas.drawCircle(mWidth / 2, mWidth / 2, mRadius, mPaint);
        }
    }

    @Override
    public int getIntrinsicWidth() {
        if (mType == Type.TYPE_CICLE) {
            return mWidth;
        }else {
            return mBitmap.getWidth();
        }
    }

    @Override
    public int getIntrinsicHeight() {
        if (mType == Type.TYPE_CICLE) {
            return mWidth;
        }else {
            return mBitmap.getHeight();
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
