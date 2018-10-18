package com.demo.dialogcontrol.dialog;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.lang.ref.WeakReference;

/**
 * 姓名：mengc
 * 日期：2018/8/9
 * 功能：获取弹窗类，在此处配置弹窗即可。。
 */

public class BaseDialogUtils {
    /**
     * 获取对应的弹窗
     *
     * @param dialogEnum
     * @param baseDialogBean
     * @return
     */
    public static BaseDialogFragment getDialog(int dialogEnum, BaseDialogBean baseDialogBean) {
        BaseDialogFragment baseDialogFragment = null;
        switch (dialogEnum) {
            case DialogEnum.Test1:
                baseDialogFragment = new TestDialog();
                break;
        }
        baseDialogFragment.initDate(baseDialogBean);
        return baseDialogFragment;
    }

    /**
     * 显示弹窗
     *
     * @param baseDialogFragment
     * @param context
     */
    public static void showDialogFragmentActivity(BaseDialogFragment baseDialogFragment, Context context) {
        WeakReference<BaseDialogFragment> weakReference = new WeakReference(baseDialogFragment);
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        weakReference.get().show(transaction, Long.toString(System.currentTimeMillis()));
    }


    public static void showDialogFragment(BaseDialogFragment baseDialogFragment, Fragment context) {
        WeakReference<BaseDialogFragment> weakReference = new WeakReference(baseDialogFragment);
        FragmentManager supportFragmentManager = context.getChildFragmentManager();
        weakReference.get().show(supportFragmentManager, Long.toString(System.currentTimeMillis()));
    }

    public static void showDialog(BaseDialogFragment baseDialogFragment, Context context) {
        if (context instanceof FragmentActivity) {
            showDialogFragmentActivity(baseDialogFragment, context);
        }
    }

}
