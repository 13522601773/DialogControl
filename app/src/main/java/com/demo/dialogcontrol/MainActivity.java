package com.demo.dialogcontrol;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.demo.dialogcontrol.dialog.*;

public class MainActivity extends AppCompatActivity implements ControlDissmisface {
    DialogControl pDialogControl;
    private View mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        //可以写到application中
        BlurHelper.init(this);
        mBt = findViewById(R.id.bt);
        pDialogControl =new DialogControl(this);
        mBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pDialogControl.getNewDialog(DialogEnum.Test1,new TestBean("test1"),MainActivity.this);
            }
        });
    }

    @Override
    public void setDissMiss(BaseDialogFragment baseDialogFragment, String dissMissType) {
        if(baseDialogFragment instanceof TestDialog){
            Toast.makeText(this,"TestDialog"+"关闭了"+dissMissType,Toast.LENGTH_LONG).show();
        }
    }
}
