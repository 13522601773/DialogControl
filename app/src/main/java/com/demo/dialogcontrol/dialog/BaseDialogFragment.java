package com.demo.dialogcontrol.dialog;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.*;

import java.lang.ref.WeakReference;

public abstract class BaseDialogFragment extends DialogFragment implements  DialogInterface.OnKeyListener,View.OnClickListener{
    private ShowListner mShowListner;
    private ControlDissmisface mControlDissmisface;
    private BaseDialogBean mBaseDialogBean;
    public   String pDissMissType="";
    protected boolean pAllowTime =true; //判断是否允许开启强制退出的定时器
    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return !isKeyBackCanceled(false);
        }
        return false;
    }
    /**
     * 获取布局ID
     */
    abstract protected int getContentViewLayoutID();

    /**
     * 界面初始化
     */
    abstract protected void onInit(View view, @Nullable Bundle savedInstanceState);

    /**
     * 获取Dialog宽度
     *
     * @return
     */
    abstract protected int getDialogLayoutWidthSize();

    /**
     * 获取Dialog高度
     *
     * @return
     */
    abstract protected int getDialogLayoutHeightSize();

    /**
     * 设置Dialog是否允许点击外部消失
     *
     * @return if {@code true}, 允许消失.
     * if {@false}, 不允许消失.
     */
    protected abstract boolean isDialogCanceledOnTouchOutside();

    /**
     * 设置Dialog是否允许点击消失
     *
     * @return if {@code true}, 允许消失.
     * if {@false}, 不允许消失.
     */
    public abstract boolean isDialogCancelable();

    /**
     * 是否允许back键取消Dialog
     *
     * @return
     */
    protected boolean isKeyBackCanceled(boolean b) {
        return b;
    }
    /**
     * 设置Dialog动画
     *
     * @return
     */
    public abstract int getWindowAnimationsId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Dialog背景样式
     *
     * @return
     */
    protected Drawable getDialogBackgroundDrawable() {
        return new ColorDrawable(Color.TRANSPARENT);
    }

    protected int getSystemUiVisibility() {
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setOnKeyListener(this);
        getDialog().getWindow().setLayout(getDialogLayoutWidthSize(), getDialogLayoutHeightSize());
        Drawable bg = getDialogBackgroundDrawable();
        getDialog().getWindow().setBackgroundDrawable(bg != null ? bg : new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().getDecorView().setSystemUiVisibility(getSystemUiVisibility());
        WindowManager.LayoutParams windowParams = getDialog().getWindow().getAttributes();
        windowParams.dimAmount = 0f;
        if(getWindowAnimationsId()!=0){
            windowParams.windowAnimations = getWindowAnimationsId();
        }
        windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        getDialog().getWindow().setAttributes(windowParams);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().setCanceledOnTouchOutside(isDialogCanceledOnTouchOutside());
        getDialog().setCancelable(isDialogCancelable());
        onInit(view, savedInstanceState);
        if(mBaseDialogBean==null){
            setDate(new BaseDialogBean());
        }else {
           setDate(mBaseDialogBean);
        }
        if(mShowListner!=null){
            mShowListner.isShowing();
        }

    }

    protected abstract void setDate(BaseDialogBean baseDialogBean);


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mControlDissmisface!=null){
            mControlDissmisface.setDissMiss(this,pDissMissType);
            this.onDestroy();
        }
    }

    public void setShowingListner(ShowListner showingListner){
        this.mShowListner=showingListner;
    }

    @Override
    public void onClick(View view) {
            widgetClick(view);
    }
    public abstract void widgetClick(View v);

    public boolean isShowing(){
        if(this.getDialog()!=null){
            return  this.getDialog().isShowing();
        }
        return false;
    }

    public void setDissMissListner(ControlDissmisface controlDissmisface) {
        this.mControlDissmisface=controlDissmisface;
    }

    public void initDate(BaseDialogBean baseDialogBean){
        this.mBaseDialogBean=baseDialogBean;
    }

    /**
     * 触摸监听
     * @param view
     */
    public void setonTouch(View view){
    }

    /**
     * 设置背景模糊
     * @param view
     */
    public Bitmap setBlur(View view){
        View dView = getActivity().getWindow().getDecorView();
        dView.buildDrawingCache();
        dView.setDrawingCacheEnabled(true);
        Bitmap blur = BlurHelper.blur(Bitmap.createBitmap(dView.getDrawingCache()), 0.5f, 10f, "#33ffffff");
        BitmapDrawable bitMapDrawable = new BitmapDrawable(getActivity().getResources(), blur);
        view.setBackground(bitMapDrawable);
        dView.setDrawingCacheEnabled(false);
        WeakReference<Bitmap> bitmapWeakReference = new WeakReference<>(blur);
        return bitmapWeakReference.get();
    }
}
