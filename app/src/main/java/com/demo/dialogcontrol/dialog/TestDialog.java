package com.demo.dialogcontrol.dialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.demo.dialogcontrol.R;


public class TestDialog extends BaseDialogFragment {
    private Bitmap mBlurBitMap;
    public TextView textView;
    public RelativeLayout mOutslidLayout;
    private TestBean mTestBean;

    public TestDialog() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.dialog_test;
    }

    @Override
    protected void onInit(View rootView, @Nullable Bundle savedInstanceState) {
        this.textView = (TextView) rootView.findViewById(R.id.tv_1);
        this.mOutslidLayout = (RelativeLayout) rootView.findViewById(R.id.outslid_layout);
        mBlurBitMap = setBlur(mOutslidLayout);
        setLisner();

    }


    private void setLisner() {
        mOutslidLayout.setOnClickListener(this);
    }


    @Override
    protected int getDialogLayoutWidthSize() {
        return ScreenUtils.getScreenWidth(getContext());
    }

    @Override
    protected int getDialogLayoutHeightSize() {
        return ScreenUtils.getScreenHeight(getContext());
    }

    @Override
    protected boolean isDialogCanceledOnTouchOutside() {
        return false;
    }

    @Override
    public boolean isDialogCancelable() {
        return false;
    }

    @Override
    public int getWindowAnimationsId() {
        return 0;
    }

    @Override
    protected void setDate(BaseDialogBean baseDialogBean) {
        if (baseDialogBean instanceof TestBean) {
            mTestBean = (TestBean) baseDialogBean;
            textView.setText(mTestBean.getTest()+"");
            }

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.outslid_layout:
                pDissMissType="test==1";
                dismiss();
                mBlurBitMap.recycle();
                System.runFinalization();
                dismiss();
                break;
        }
    }

}
