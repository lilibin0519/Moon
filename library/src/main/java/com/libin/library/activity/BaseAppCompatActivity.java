package com.libin.library.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.gyf.immersionbar.ImmersionBar;
import com.libin.library.R;
import com.libin.library.util.BaseAppManager;
import com.libin.library.util.EventCenter;
import com.libin.library.util.LoadingViewController;
import com.libin.library.util.ToastUtils;
import com.libin.library.util.ViewBindingUtil;
import com.libin.library.util.net.NetChangeObserver;
import com.libin.library.util.net.NetStateReceiver;
import com.libin.library.util.net.NetType;
import com.libin.library.util.viewswitcher.VaryViewHelperController;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;


/**
 * 作者：王鹏飞
 * 创建时间：2015/12/29 14:59
 * 邮箱：15291967179@163.com
 * 描述：
 */
public abstract class BaseAppCompatActivity<VB extends ViewBinding> extends AppCompatActivity implements LoadingViewController
{

    /**
     * 网络状态
     */
    protected NetChangeObserver mNetChangeObserver = null;

    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;

    private VB viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (null != extras)
        {
            getBundleExtras(extras);
        }

        if (isRegisterEventBus())
        {
            EventBus.getDefault().register(this);
        }


        NetStateReceiver.registerObserver(mNetChangeObserver);
        BaseAppManager.getInstance().addActivity(this);

//        if (getContentViewLayoutID() != 0)
//        {
//            setContentView(getContentViewLayoutID());
//        }
        viewBinding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater());
        if (null != viewBinding) {
            setContentView(viewBinding.getRoot());
        }

        mNetChangeObserver = new NetChangeObserver()
        {

            @Override
            public void onNetConnected(NetType type)
            {
                super.onNetConnected(type);
                onNetworkConnected(type);
            }

            @Override
            public void onNetDisConnect()
            {
                super.onNetDisConnect();
                onNetworkDisConnected();
            }
        };

        if (isImmersionBarEnabled()) {
            ImmersionBar.with(this).statusBarColor(getStatusBarColorRes()).statusBarDarkFont(getStatusBarDarkAble(), 0.5f).navigationBarColor(getNavigationColor()).navigationBarDarkIcon(getNavigationDark(), 0.5f).init();
        }

        initViewAndData();
    }

    protected int getStatusBarColorRes() {
        return R.color.colorPrimary;
    }

    protected boolean getStatusBarDarkAble() {
        return false;
    }

    protected int getNavigationColor() {
        return android.R.color.transparent;
    }

    protected boolean getNavigationDark() {
        return true;
    }

    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * Bundle  传递数据
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * 界面初始化
     */
    protected abstract void initViewAndData();

    /**
     * 当前没有网络连接
     */
    protected void onNetworkDisConnected(){}

    /**
     * 当前网络连接类型
     *
     * @param type
     */
    protected void onNetworkConnected(NetType type){}

    /**
     * 切换根View   findViewById(R.id...);
     *
     * @return
     */
    protected View getLoadingTargetView() {
        return null;
    }

    /**
     * EventBus
     *
     * @param center
     */
    protected void onEventComing(EventCenter<?> center) {

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setNativeBarColor();
        initLoadingTargetView();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setNativeBarColor();
        initLoadingTargetView();
    }

    public void setNativeBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && (drawSystemNavigationBarBg() || drawSystemStatusBarBg())) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (drawSystemNavigationBarBg()) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);   //设置沉浸式虚拟键，在MIUI系统中，虚拟键背景透明。原生系统中，虚拟键背景半透明。
            }
            if (drawSystemStatusBarBg()) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);   //设置沉浸式虚拟键，在MIUI系统中，虚拟键背景透明。原生系统中，虚拟键背景半透明。
            }
        }
    }

    protected boolean drawSystemNavigationBarBg() {
        return false;
    }

    protected boolean drawSystemStatusBarBg() {
        return false;
    }

    private void initLoadingTargetView() {
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
    }

    @Override
    public void finish()
    {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        NetStateReceiver.removeRegisterObserver(mNetChangeObserver);
        if (isRegisterEventBus())
        {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 界面跳转
     *
     * @param clazz
     */
    public void readyGo(Class<?> clazz)
    {
        readyGo(clazz, null);
    }

    /**
     * 跳转界面，  传参
     *
     * @param clazz
     * @param bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle)
    {
        Intent intent = new Intent(this, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz
     */
    public void readyGoThenKill(Class<?> clazz)
    {
        readyGoThenKill(clazz, null);
    }

    protected void readyGoThenKill(Class<?> clazz, Bundle bundle)
    {
        readyGo(clazz, bundle);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    public void readyGoForResult(Class<?> clazz, int requestCode)
    {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle)
    {
        Intent intent = new Intent(this, clazz);
        if (null != bundle)
        {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Toast 提示
     *
     * @param text
     */
    public void showToastShort(String text)
    {
        ToastUtils.showToastShort(text);
    }

    public void showToastShort(int resourceId)
    {
        ToastUtils.showToastShort(resourceId);
    }

    public void showToastLong(String text)
    {
        ToastUtils.showToastLong(text);
    }

    protected void showToastLong(int resourceId)
    {
        ToastUtils.showToastLong(resourceId);
    }

    public void showToastCenter(String text)
    {
        ToastUtils.showToastGravityCenter(text);
    }

    /**
     * 是否在当前 Activity 实例中注册  EventBus
     * 默认在当前 Activity 不注册
     * 如果需要注册广播监听，  重写此方法    返回   true
     *
     * @return
     */
    protected boolean isRegisterEventBus()
    {
        return false;
    }

    @Override
    public void restoreView()
    {
        if (null != mVaryViewHelperController)
            mVaryViewHelperController.restore();
    }


    /**
     * 显示  正在加载界面
     *
     * @param toggle
     * @param msg
     */
    @Override
    public void toggleShowLoading(boolean toggle, String msg)
    {

        if (null == mVaryViewHelperController)
        {
//            throw new IllegalArgumentException("必须传入一个目标视图加载控制器");
            return;
        }

        if (toggle)
        {
            mVaryViewHelperController.showLoading(msg, this);
        } else
        {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * 显示返回内容为空界面
     *
     * @param toggle
     * @param msg
     * @param onClickListener
     */
    @Override
    public void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener)
    {
        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("必须传入一个目标视图加载控制器");
            return;
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * 显示加载失败页面
     *
     * @param toggle
     * @param msg
     * @param onClickListener
     */
    @Override
    public void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener)
    {
        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("必须传入一个目标视图加载控制器");
            return;
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * 无网络连接时   显示当前界面
     *
     * @param toggle
     * @param onClickListener
     */
    @Override
    public void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener)
    {
        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("必须传入一个目标视图加载控制器");
                return;
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }

    }

    @Subscribe
    public void onEventMainThread(EventCenter<?> center)
    {

        if (null != center)
        {
            onEventComing(center);
        }

    }


    /**
     * set status bar translucency
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on)
            {
                winParams.flags |= bits;
            } else
            {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    protected void enterFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

        flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE // 保持View Layout不变，隐藏状态栏或者导航栏后，View不会拉伸。
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 让View全屏显示，Layout会被拉伸到StatusBar下面，不包含NavigationBar。
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // 让View全屏显示，Layout会被拉伸到StatusBar和NavigationBar下面。
                | View.SYSTEM_UI_FLAG_FULLSCREEN // Activity全屏显示，且状态栏被隐藏覆盖掉。等同于（WindowManager.LayoutParams.FLAG_FULLSCREEN）。
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION; // 隐藏虚拟按键(导航栏)。有些手机会用虚拟按键来代替物理按键。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 这个flag只有当设置了SYSTEM_UI_FLAG_HIDE_NAVIGATION才起作用。如果没有设置这个flag，
            // 任意的View相互动作都退出SYSTEM_UI_FLAG_HIDE_NAVIGATION模式。如果设置就不会退出。
            flags |= View.SYSTEM_UI_FLAG_IMMERSIVE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        }

        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.setSystemUiVisibility(flags);
        }
    }

    protected void exitFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_VISIBLE;

        flags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE // 保持View Layout不变，隐藏状态栏或者导航栏后，View不会拉伸。
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // 让View全屏显示，Layout会被拉伸到StatusBar下面，不包含NavigationBar。
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;// 让View全屏显示，Layout会被拉伸到StatusBar和NavigationBar下面

        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.setSystemUiVisibility(flags);
        }
    }

    public VB getBinding() {
        return viewBinding;
    }
}
