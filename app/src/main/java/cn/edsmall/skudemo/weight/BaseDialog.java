package cn.edsmall.skudemo.weight;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;


/**
 *
 */
public class BaseDialog extends AlertDialog {

    private int layoutResID;
    private View parentView;
    private Context mContext;
    private Window mWindow;

    /**
     * @param context     Context
     * @param layoutResID dialog layout
     */
    public BaseDialog(@NonNull Context context, int layoutResID) {
        super(context);
        mContext = context;
        this.layoutResID = layoutResID;
    }

    /**
     * @param context     Context
     * @param layoutResID dialog layout
     * @param theme       theme id
     */
    public BaseDialog(@NonNull Context context, int layoutResID, int theme) {
        super(context, theme);
        mContext = context;
        this.layoutResID = layoutResID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentView = getLayoutInflater().inflate(layoutResID, null);
        setContentView(parentView);
        mWindow = getWindow();
        mWindow.setBackgroundDrawableResource(android.R.color.transparent);
    }

    public void setDialogWH(int width, int height) {
        if (mWindow == null) mWindow = getWindow();
        mWindow.setLayout(width , height);
    }

    public void setGravity(int gravity) {
        if (mWindow == null) mWindow = getWindow();
        mWindow.setGravity(gravity);
    }

    public <T extends View> T findViewById(int id) {
        return parentView.findViewById(id);
    }

    public void setViewOnClickListener(int id, View.OnClickListener listener) {
        parentView.findViewById(id).setOnClickListener(listener);
    }
}
