package com.libin.moon;

import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Trace;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.ActionBar;
import androidx.core.content.ContextCompat;
import androidx.viewbinding.ViewBinding;

import com.libin.library.activity.BaseAppCompatActivity;
import com.libin.library.util.SystemUtil;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/12
 */
public abstract class BaseActivity<VB extends ViewBinding> extends BaseAppCompatActivity<VB> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (shouldPortrait() && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private boolean shouldPortrait() {
        return true;
    }

    @Override
    protected int getStatusBarColorRes() {
        return SystemUtil.isDarkMode(this) ? R.color.black_overlay : R.color.colorPrimary;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initTitlePadding();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initTitlePadding();

        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, getStatusBarColorRes())));
        }
    }

    private void initTitlePadding() {
        final View titleView = getTitleView();
        if (null == titleView) {
            return;
        }
        titleView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                titleView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (setTitlePaddingTop()) {
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setTitlePadding(titleView, getStatusBarHeight());
                        }
                    }, 100);
                }
            }
        });
    }

    protected void setTitlePadding(View titleView, int statusBarHeight) {
        titleView.setPadding(0,statusBarHeight, 0, 0);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initTitlePadding();
    }

    protected View getTitleView() {
        return null;
    }

    public boolean setTitlePaddingTop(){
        return true;
    }

    protected int getStatusBarHeight(){
        Rect rectangle= new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        return rectangle.top;
    }
}
