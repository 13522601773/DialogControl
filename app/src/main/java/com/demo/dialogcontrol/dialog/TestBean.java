package com.demo.dialogcontrol.dialog;

/**
 * 姓名：mengc
 * 日期：2018/10/17
 * 功能：
 */

public class TestBean extends BaseDialogBean {
    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    private String test;

    public TestBean(String test) {
        this.test = test;
    }
}
