package com.libin.library.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.libin.library.util.EventCenter;
import com.libin.library.util.LoadingViewController;
import com.libin.library.util.ToastUtils;
import com.libin.library.util.ViewBindingUtil;
import com.libin.library.util.viewswitcher.VaryViewHelperController;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者：王鹏飞
 * 创建时间：2016/1/4 16:06
 * 邮箱：15291967179@163.com
 * 描述：
 */
public abstract class BaseFragment<VB extends ViewBinding> extends Fragment implements LoadingViewController {

    protected String TAG_LOG = null;

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInVisible = true;

    private boolean isPrepared;

    private VaryViewHelperController mVaryViewHelperController;

    private VB binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater(), container, false);
        if (null != binding) {
            return binding.getRoot();
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }
        initViewAndData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public VB getBinding() {
        return binding;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus())
        {
        EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }

        if (getUserVisibleHint()) {
            onUserVisible();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInVisible) {
                isFirstInVisible = false;
                onFirstUserInVisible();
            } else {
                onUserInvisible();
            }
        }
    }

//    /**
//     * 设置 LayoutID
//     *
//     * @return
//     */
//    protected abstract int getContentViewLayoutID();

    /**
     * ViewController genView
     *
     * @return 提示信息展示View
     */
    protected abstract View getLoadingTargetView();

    /**
     * 界面初始化
     */
    protected abstract void initViewAndData();

    /**
     * like onResume()
     */
    protected abstract void onUserVisible();

    /**
     * like onPause();
     */
    protected abstract void onUserInvisible();

    /**
     * 第一次 当fragment是不可见的时候
     */
    protected abstract void onFirstUserInVisible();

    /**
     * fragment 第一次 可见的时候， 做初始化 或 数据的刷新（只调用一次）
     */
    protected abstract void onFirstUserVisible();

    protected abstract void onEventComing(EventCenter eventCenter);

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }

    }


    /**
     * 是否在当前实例中注册 EventBus
     *
     * @return 是否注册EventBus
     */
    protected boolean isRegisterEventBus() {
        return false;
    }


    /**
     * startActivity
     *
     * @param clazz 跳转页面Activity的Class
     */
    public void readyGo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz 跳转页面Activity的Class
     * @param bundle 参数Bundle
     */
    public void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz 跳转页面Activity的Class
     * @param requestCode requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz 跳转页面Activity的Class
     * @param requestCode requestCode
     * @param bundle 参数Bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }


    protected void readyGoThenKill(Class<?> clazz) {
        readyGoThenKill(clazz, null);
    }

    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        readyGo(clazz, bundle);
        if (null != getActivity()) {
            getActivity().finish();
        }
    }

    /**
     * Toast 提示
     *
     * @param text 提示文言
     */
    protected void showToastShort(String text) {
        ToastUtils.showToastShort(text);
    }

    protected void showToastShort(int resourceId) {
        ToastUtils.showToastShort(resourceId);
    }

    protected void showToastLong(String text) {
        ToastUtils.showToastLong(text);
    }

    protected void showToastLong(int resourceId) {
        ToastUtils.showToastLong(resourceId);
    }

    public void showToastCenter(String text) {
        ToastUtils.showToastGravityCenter(text);
    }


    /**
     * toggle show loading
     *
     * @param toggle 是否展示
     */
    @Override
    public void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("You must return a right target view for loading");
            return;
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg, this);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     *
     * @param toggle 是否展示
     */
    @Override
    public void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("You must return a right target view for loading");
            return;
        }

        if (toggle) {
            mVaryViewHelperController.showEmpty(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     *
     * @param toggle 是否展示
     */
    @Override
    public void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("You must return a right target view for loading");
            return;
        }

        if (toggle) {
            mVaryViewHelperController.showError(msg, onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show network error
     *
     * @param toggle 是否展示
     */
    @Override
    public void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
//            throw new IllegalArgumentException("You must return a right target view for loading");
            return;
        }

        if (toggle) {
            mVaryViewHelperController.showNetworkError(onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    @Override
    public void restoreView() {
        if (null != mVaryViewHelperController) {
            mVaryViewHelperController.restore();
        }
    }

    @Subscribe
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter)
            onEventComing(eventCenter);
    }


}
