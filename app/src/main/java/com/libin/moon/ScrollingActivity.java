package com.libin.moon;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.libin.library.adapter.BaseAdapter;
import com.libin.library.adapter.BaseAdapterViewListener;
import com.libin.library.util.ClickProxy;
import com.libin.moon.databinding.ActivityScrollingBinding;
import com.libin.moon.databinding.ItemDiaryBinding;
import com.libin.moon.model.DiaryModel;
import com.libin.moon.util.OnQueryListener;
import com.libin.moon.util.RealmUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.cutline.RecycleViewDivider;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewListener;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;

public class ScrollingActivity extends BaseActivity<ActivityScrollingBinding> implements OnToolsItemClickListener<DiaryModel> {

    private Toolbar toolbar;

    private RecyclerView content;

    private List<DiaryModel> data = new ArrayList<>();

    private DefaultAdapter<DiaryModel> adapter;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewAndData() {
        toolbar = getBinding().toolbar;
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setElevation(25);
        }
        CollapsingToolbarLayout toolBarLayout = getBinding().toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = getBinding().fab;
        fab.setOnClickListener(view -> {
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            readyGoForResult(PostActivity.class, AppConstant.REQUEST_CODE_POST);
        });
        initContent();
        requestData();
    }

    private void requestData() {
        RealmUtil.query(DiaryModel.class, new OnQueryListener<DiaryModel>() {
            @Override
            public void onResult(List<DiaryModel> result) {
                data.clear();
                data.addAll(result);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initContent() {
        content = getBinding().diaryList;
        content.setLayoutManager(new LinearLayoutManager(this));
        content.setAdapter(adapter = new BaseAdapter<>(this, data, R.layout.item_diary, new BaseAdapterViewListener<DiaryModel, ItemDiaryBinding>(this)));
        content.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL).setDividerPadding(getResources().getDimensionPixelSize(R.dimen.qb_px_30)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.REQUEST_CODE_POST && resultCode == RESULT_OK) {
            requestData();
        }
    }

    @Override
    public void onItemClick(int position, DiaryModel diaryModel) {

    }

    @Override
    protected View getTitleView() {
        return toolbar = getBinding().toolbar;
    }

    @Override
    protected void setTitlePadding(View titleView, int statusBarHeight) {
        super.setTitlePadding(titleView, statusBarHeight);
        titleView.getLayoutParams().height += statusBarHeight / 2;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            onBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    private void onBack() {
        showToastCenter("宝贝白白~再点一下返回就退出咯");
    }

    @Override
    protected int getStatusBarColorRes() {
        return android.R.color.transparent;
    }
}