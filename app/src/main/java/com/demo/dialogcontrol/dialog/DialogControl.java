package com.demo.dialogcontrol.dialog;

import android.content.Context;

import java.util.HashMap;

/**
 * 姓名：mengc
 * 日期：2018/8/7
 * 功能：dialogFragment 工具类
 */

public class DialogControl {
    private Context mContext;
    public HashMap<String, BaseDialogFragment> mDialogHashMap = new HashMap<>();

    public DialogControl(Context context) {
        this.mContext = context;
    }

    /**
     *
     * @param dialogEnum  dialog 类型
     * @param baseDialogBean dialog 显示参数
     */
    public void getNewDialog(int dialogEnum, BaseDialogBean baseDialogBean) {
      final   BaseDialogFragment baseDialog = BaseDialogUtils.getDialog(dialogEnum, baseDialogBean);
        BaseDialogUtils.showDialog(baseDialog, mContext);
        baseDialog.setShowingListner(new ShowListner() {
            @Override
            public void isShowing() {
                judgeIsEject(mDialogHashMap, baseDialog);
            }
        });
    }

    public void getNewDialog(int dialogEnum, BaseDialogBean baseDialogBean,final ControlDissmisface controlDissmisface) {
        final   BaseDialogFragment baseDialog = BaseDialogUtils.getDialog(dialogEnum, baseDialogBean);
        BaseDialogUtils.showDialog(baseDialog, mContext);
        baseDialog.setShowingListner(new ShowListner() {
            @Override
            public void isShowing() {
                judgeIsEject(mDialogHashMap, baseDialog,controlDissmisface);
            }
        });
    }


    private void judgeIsEject(HashMap<String, BaseDialogFragment> dialogHashMap, BaseDialogFragment baseDialog) {
        for (String key : dialogHashMap.keySet()) {
            BaseDialogFragment baseDialog1 = dialogHashMap.get(key);
            if (baseDialog1.isShowing()) {
                baseDialog1.dismiss();
            }
        }
        dialogHashMap.clear();
        long l = System.currentTimeMillis();
        String s = Long.toString(l);
        dialogHashMap.put(s, baseDialog);

    }

    private void judgeIsEject(HashMap<String, BaseDialogFragment> dialogHashMap, BaseDialogFragment baseDialog,ControlDissmisface controlDissmisface) {
        for (String key : dialogHashMap.keySet()) {
            BaseDialogFragment baseDialog1 = dialogHashMap.get(key);
            if (baseDialog1.isShowing()) {
                baseDialog1.dismiss();
            }
        }
        dialogHashMap.clear();
        long l = System.currentTimeMillis();
        String s = Long.toString(l);
        dialogHashMap.put(s, baseDialog);
        baseDialog.setDissMissListner(controlDissmisface);

    }

    public  void dissMiss(){
        for (String key : mDialogHashMap.keySet()) {
            BaseDialogFragment baseDialog1 = mDialogHashMap.get(key);
            if (baseDialog1.isShowing()) {
                baseDialog1.dismiss();
                baseDialog1.onDestroy();
                baseDialog1=null;
            }
        }
    }

}
