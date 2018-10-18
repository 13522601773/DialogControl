# dialogControl
===========
<h2>对dialogFragment进行封装，包装。并对弹窗进行统一管理。包含弹窗的c++语言的背景模糊调用，dialog消亡后，内存的快速回收，提高效率。统一处理dissmiss事件。方便复杂的dialog功能需求。</h2>


## <h3>图片模糊处理</h3></br>
<h4>模糊处理类粘贴</h4>
<img width="200px" height="200px" src="https://github.com/13522601773/dialogControl/blob/master/pic/1.png"/>

```java
     //可以写到application中，模糊初始化
        BlurHelper.init(this);
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

```
## <h3>弹窗处理</h3></br>


```java
1.自定义弹窗继承BaseDialogFragment方法

   public class TestDialog extends BaseDialogFragment {

       public TestDialog() {

       }

2.定义自己的弹窗类型

public class DialogEnum {
    public static final int Test1=1;
    public static final int LOADING=2;
    public static final int MARKET=3;
}

3.在工具类中应用

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

4、使用
        //放入base基类中
        pDialogControl =new DialogControl(this);
        //如需传参创建自己的bean继承BaseDialogBean即可
         pDialogControl.getNewDialog(DialogEnum.Test1,new TestBean("test1"),MainActivity.this);
         //实现消失接口，可传参

         @Override
             public void setDissMiss(BaseDialogFragment baseDialogFragment, String dissMissType) {
                if(baseDialogFragment instanceof TestDialog){
                    Toast.makeText(this,"TestDialog"+"关闭了"+dissMissType,Toast.LENGTH_LONG).show();
                }
            }



```

## 详细使用方式请参考demo
