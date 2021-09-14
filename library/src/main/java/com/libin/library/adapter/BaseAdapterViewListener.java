package com.libin.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.libin.library.holder.BaseHolder;
import com.libin.library.util.ViewBindingUtil;

import java.util.List;

import em.sang.com.allrecycleview.inter.DefaultAdapterViewListener;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/14
 */
public class BaseAdapterViewListener<T, VB extends ViewBinding> extends DefaultAdapterViewListener<T> {

    private OnToolsItemClickListener<T> clickListener;

    public BaseAdapterViewListener(OnToolsItemClickListener<T> mClickListener) {
        clickListener = mClickListener;
    }

    public BaseAdapterViewListener() {
    }

    public BaseHolder<T, VB> getBodyHolder(Context context, List<T> lists, ViewGroup parent) {
        VB binding = ViewBindingUtil.inflateWithGeneric(this, LayoutInflater.from(context), parent, false);
        BaseHolder<T, VB> holder = new BaseHolder<>(context, lists, binding);
        holder.setOnTOnToolsItemClickListener(clickListener);
        return holder;
    }

    public BaseAdapterViewListener<T, VB> setClickListener(OnToolsItemClickListener<T> clickListener) {
        this.clickListener = clickListener;
        return this;
    }
}
