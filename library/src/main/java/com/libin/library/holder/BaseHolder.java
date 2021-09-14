package com.libin.library.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import com.libin.library.util.ViewBindingUtil;

import java.util.List;

import em.sang.com.allrecycleview.holder.CustomHolder;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/14
 */
public class BaseHolder<T, VB extends ViewBinding> extends CustomHolder<T> {

    VB binding;

    public BaseHolder(Context context, List<T> data, VB mBinding) {
        super(data, mBinding.getRoot());
        binding = mBinding;
    }

    public BaseHolder(Context context, List<T> lists, int itemID) {
        super(context, lists, itemID);
    }
}
