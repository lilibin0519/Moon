package com.libin.moon;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.libin.library.util.ClickProxy;
import com.libin.library.util.SystemUtil;
import com.libin.moon.databinding.ActivityPostBinding;
import com.libin.moon.model.DiaryModel;
import com.libin.moon.model.MyPhoto;
import com.libin.moon.util.RealmUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.CustomPeakHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewListener;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/13
 */
public class PostActivity extends BaseActivity<ActivityPostBinding> {

    private List<MyPhoto> data;

    private RecyclerView content;

    private DefaultAdapter<MyPhoto> adapter;

    private CustomPeakHolder headerHolder;

    private MenuItem menuDoneItem;

    private TextView editContent;

    private boolean menuIsInflated;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewAndData() {
//        Toolbar toolbar = getBinding().toolbar;
//        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("写日记");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setElevation(25);
        }
        content = getBinding().postContent;
        content.setLayoutManager(new GridLayoutManager(this, 3));
        content.setAdapter(adapter = new DefaultAdapter<>(this, data, R.layout.item_diary, new DefaultAdapterViewListener<MyPhoto>(){
            @Override
            public CustomHolder<MyPhoto> getBodyHolder(Context context, List<MyPhoto> lists, int itemID) {
                return new CustomHolder<MyPhoto>(context, lists, itemID){
                    @Override
                    public void initView(int position, List<MyPhoto> data, Context context) {
//                        super.initView(position, data, context);
//                        itemView.setOnClickListener(new ClickProxy(v -> {
//                            onItemClick(position, data.get(position));
//                        }));
//                        holderHelper.setText(R.id.item_diary_content, data.get(position).path);
//                        holderHelper.setText(R.id.item_diary_date, new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(new Date(data.get(position).stamp)));
                    }
                };
            }
        }));
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_post, content, false);
        adapter.addHead(headerHolder = new CustomPeakHolder(headerView));
        editContent = headerHolder.itemView.findViewById(R.id.header_post_content);
        editContent.setFocusable(true);
        editContent.setFocusableInTouchMode(true);
        editContent.requestFocus();
//        editContent.requestFocusFromTouch();
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                menuDoneItem.setEnabled(s.length() > 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!menuIsInflated) {
            getMenuInflater().inflate(R.menu.post_menu, menu);
            menuDoneItem = menu.findItem(R.id.save);
            menuDoneItem.setEnabled(false);
            menuIsInflated = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        if (item.getItemId() == R.id.save) {
            DiaryModel model = new DiaryModel();
            model.stamp = System.currentTimeMillis();
            model.content = editContent.getText().toString();
            RealmUtil.insert(model, new RealmUtil.OnOperationListener() {
                @Override
                public void onSuccess() {
                    showToastCenter("宝贝又写了一篇，真棒！~");
                    setResult(RESULT_OK);
                    finish();
                }

                @Override
                public void onError() {
                    showToastCenter("保存失败啦~快去找包包！");
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onItemClick(int position, MyPhoto myPhoto) {

    }
}
