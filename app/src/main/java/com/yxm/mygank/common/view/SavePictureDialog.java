package com.yxm.mygank.common.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxm.mygank.R;
import com.yxm.mygank.common.util.FileHelper;

/**
 * Created by yxm on 2020/4/13
 *
 * @function 保存图片dialog
 */
public class SavePictureDialog extends Dialog {

    private String mUrl;
    private DisplayMetrics dm;

    public SavePictureDialog(Context context, String url) {
        this(context, R.style.SheetDialogStyle);
        mUrl = url;
    }

    public SavePictureDialog(Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_save_picture);
        dm = context.getResources().getDisplayMetrics();
        initView();
        initListener();
    }

    private void initView() {
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = dm.widthPixels; //设置宽度
        dialogWindow.setAttributes(lp);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void initListener() {
        TextView savePicture = findViewById(R.id.save_picture);
        TextView cancel = findViewById(R.id.cancel_save);

        savePicture.setOnClickListener(view -> {
            FileHelper.getInstance().saveImage(mUrl);
            if (isShowing()) {
                dismiss();
            }
        });
        cancel.setOnClickListener(view -> {
            if (isShowing()) {
                dismiss();
            }
        });
    }
}
